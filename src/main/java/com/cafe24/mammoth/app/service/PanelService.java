package com.cafe24.mammoth.app.service;

import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mammoth.app.domain.Panel;
import com.cafe24.mammoth.app.domain.SelectFunc;
import com.cafe24.mammoth.app.domain.enumerate.Position;
import com.cafe24.mammoth.app.repository.FuncRepository;
import com.cafe24.mammoth.app.repository.PanelRepository;
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
	FuncRepository funcRepository;
	
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
	
	public boolean createPanel(List<Long> funcId, List<Long> funcOrder, Long themeId) {
		Panel panel = new Panel();
		panel.setName("Test Panel["+Calendar.getInstance().getTimeInMillis()+"]");
		panel.setPosition(Position.RIGHT);
		panel.setTheme(themeRepository.getOne(themeId));
		panel = repo.save(panel);
		
		for(int i=0; i<funcId.size(); i++) {
			SelectFunc selectFunc = new SelectFunc();
			selectFunc.setPanel(panel);
			selectFunc.setFuncOrder(funcOrder.get(i));
			selectFunc.setFunc(funcRepository.getOne(funcId.get(i)));
			selectFuncRepository.save(selectFunc);
		}
		
		return true;
	}
	
}
