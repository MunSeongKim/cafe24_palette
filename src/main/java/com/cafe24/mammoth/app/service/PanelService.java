package com.cafe24.mammoth.app.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mammoth.app.domain.Member;
import com.cafe24.mammoth.app.domain.Panel;
import com.cafe24.mammoth.app.domain.Script;
import com.cafe24.mammoth.app.domain.SelectFunc;
import com.cafe24.mammoth.app.domain.enumerate.Position;
import com.cafe24.mammoth.app.repository.FunctionRepository;
import com.cafe24.mammoth.app.repository.MemberRepository;
import com.cafe24.mammoth.app.repository.PanelRepository;
import com.cafe24.mammoth.app.repository.ScriptRepository;
import com.cafe24.mammoth.app.repository.SelectFuncRepository;
import com.cafe24.mammoth.app.repository.ThemeRepository;

@Service
@Transactional
public class PanelService {
	
	@Autowired
	private PanelRepository panelRepository;
	
	@Autowired
	private SelectFuncRepository selectFuncRepository;
	
	@Autowired
	private ThemeRepository themeRepository;
	
	@Autowired
	private FunctionRepository funcRepository;
	
	@Autowired
	private ScriptRepository scriptRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	public List<Panel> getPanelList(String mallId) {
		return panelRepository.findAllByMemberId(mallId);
	}
	
	public Panel getPanelById(Long id) {
		return panelRepository.findOne(id);
	}
	
	public boolean savePanel(Panel panel) {
		return panelRepository.save(panel) != null ? true : false;
		
	}
	public void removePanel(Long id) {
		panelRepository.deleteById(id);
	}
	
	public boolean createPanel(
			String mallId,
			String panelName,
			List<Long> funcId,
			List<Long> funcOrder,
			Long themeId,
			String position
			) {
		
		System.out.println("mallId: " + mallId);
		
		Optional<Member> savedMember = memberRepository.findById(mallId);
		Member member = savedMember.isPresent() ? savedMember.get() : null;
		Panel panel = new Panel();
		panel.setName(panelName);
		panel.setPosition(position.toUpperCase().equals("RIGHT") ? Position.RIGHT : Position.LEFT);
		panel.setTheme(themeRepository.getOne(themeId));
		panel.setMember(member);
		panel = panelRepository.save(panel);
		
		// select_func tbl row create
		for(int i=0; i<funcId.size(); i++) {
			SelectFunc selectFunc = new SelectFunc();
			selectFunc.setPanel(panel);
			selectFunc.setFuncOrder(funcOrder.get(i));
			selectFunc.setFunction(funcRepository.getOne(funcId.get(i)));
			selectFuncRepository.save(selectFunc);
		}
		
		// script tbl row create
		Script script = new Script();
		script.setPanel(panel);
		script.setIsApply(false);
		script.setFilepath("/template/paletteLoader.js");
		scriptRepository.save(script); 
		
		return true;
	}

	public Panel getApplyPanel(String mallId) {
		return panelRepository.findByMemberIdAndScriptIsIsApplyTrue(mallId);
	}
	
	public boolean isExistByName(String name) {
		int result = panelRepository.confirmPanelName(name);
		System.out.println("결과 : "+result);
		 
		return result == 1 ? true : false;
	}
	
}
