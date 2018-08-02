package com.cafe24.mammoth.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mammoth.app.domain.Panel;
import com.cafe24.mammoth.app.domain.Script;
import com.cafe24.mammoth.app.domain.SelectFunc;
import com.cafe24.mammoth.app.domain.enumerate.Position;
import com.cafe24.mammoth.app.repository.FunctionRepository;
import com.cafe24.mammoth.app.repository.PanelRepository;
import com.cafe24.mammoth.app.repository.ScriptRepository;
import com.cafe24.mammoth.app.repository.SelectFuncRepository;
import com.cafe24.mammoth.app.repository.ThemeRepository;

@Service
@Transactional
public class PanelService {
	
	@Autowired
	PanelRepository repo;
	
	@Autowired
	SelectFuncRepository selectFuncRepository;
	
	@Autowired
	ThemeRepository themeRepository;
	
	@Autowired
	FunctionRepository funcRepository;
	
	@Autowired
	ScriptRepository scriptRepository;
	
	public List<Panel> getPanelList() {
		return repo.findAll();
	}
	
	public Panel getPanelById(Long id) {
		return repo.findOne(id);
	}
	
	public boolean savePanel(Panel panel) {
		return repo.save(panel) != null ? true : false;
		
	}
	public void removePanel(Long id) {
		repo.deleteById(id);
	}
	
	public boolean createPanel(
			String panelName,
			List<Long> funcId,
			List<Long> funcOrder,
			Long themeId,
			String position
			) {
		Panel panel = new Panel();
		panel.setName(panelName);
		panel.setPosition(Position.valueOf(position));
		panel.setTheme(themeRepository.getOne(themeId));
		panel = repo.save(panel);
		
		// select_func tbl row create
		for(int i=0; i<funcId.size(); i++) {
			SelectFunc selectFunc = new SelectFunc();
			selectFunc.setPanel(panel);
			selectFunc.setFuncOrder(funcOrder.get(i));
			selectFunc.setFunc(funcRepository.getOne(funcId.get(i)));
			selectFuncRepository.save(selectFunc);
		}
		
		// script tbl row create
		Script script = new Script();
		script.setPanel(panel);
		script.setIsApply(false);
		script.setFilepath("/template/skel.js");
		scriptRepository.save(script); 
		
		return true;
	}
	
	public boolean isExistByName(String name) {
		System.out.println(repo.findByName(name));
		System.out.println(repo.findOne(7L));
		System.out.println("name : "+name);
//		System.out.println("canUsePanelName : "+result);
		 
//		return result == null ? true : false;
		return false;
	}
	
}
