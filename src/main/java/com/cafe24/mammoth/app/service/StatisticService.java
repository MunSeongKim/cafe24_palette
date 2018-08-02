package com.cafe24.mammoth.app.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mammoth.app.repository.PanelRepository;
import com.cafe24.mammoth.app.repository.SelectFuncRepository;

@Service
@Transactional
public class StatisticService {
	@Autowired
	PanelRepository PanelRepository;
	
	@Autowired
	SelectFuncRepository selectFuncRepository;
	
	public Long getPanelCount() {
		System.out.println("PanelRepository.count() ==> " + PanelRepository.count());
		return PanelRepository.count();
	}
	
	public Long getPerPersonPanelCount() {
		/*// 사람마다 수정해야됨.
		System.out.println("PanelRepository.count() ==> " + PanelRepository.perPersonPanelCount());
		
		return PanelRepository.perPersonPanelCount();*/
		return 1L;
	}
	
	
}
