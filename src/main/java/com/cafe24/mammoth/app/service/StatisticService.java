package com.cafe24.mammoth.app.service;

import java.util.HashMap;
import java.util.List;

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
	
	// 패널관련 통계
	public Double getPerPersonPanelCount() {
		List<Long> list = PanelRepository.perPersonPanelCount();
		
		double perPersonPanelCount = (double)PanelRepository.count() / (double)list.size();
				
		return perPersonPanelCount;
	}
	
	// 기능관련 통계
	public List<Object[]> getFunctionCount(){
		List<Object[]> list = selectFuncRepository.getFunctionCount();
		
		return list;
	}
	
}
