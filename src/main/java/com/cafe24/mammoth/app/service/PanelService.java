package com.cafe24.mammoth.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mammoth.app.domain.Panel;
import com.cafe24.mammoth.app.repository.PanelRepository;

@Service
@Transactional
public class PanelService {
	
	@Autowired
	PanelRepository repo;
	
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
	
}
