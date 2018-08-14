package com.cafe24.mammoth.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mammoth.app.repository.PanelRepository;
import com.cafe24.mammoth.app.repository.SelectFuncRepository;

/**
 * 18.08.02 통계를 처리하는 API 컨트롤러
 * @author Allan
 */

@Service
@Transactional
public class StatisticService {
	@Autowired
	PanelRepository PanelRepository;
	
	@Autowired
	SelectFuncRepository selectFuncRepository;
	
	// 패널 총 개수 구하는 서비스
	public Long getPanelCount() {
		System.out.println("PanelRepository.count() ==> " + PanelRepository.count());
		return PanelRepository.count();
	}
	
	// 패널관련 통계
	// 한 사람 패널 만들 개수의 평균 구하는 서비스
	public Double getPerPersonPanelCount() {
		List<Long> list = PanelRepository.perPersonPanelCount();
		
		double perPersonPanelCount = (double)PanelRepository.count() / (double)list.size();
				
		return perPersonPanelCount;
	}
	
	// 기능관련 통계
	// 기능에 대한 개수 구하는 서비스
	public List<Object[]> getFunctionCount(){
		List<Object[]> list = selectFuncRepository.getFunctionCount();
		
		return list;
	}
	
}
