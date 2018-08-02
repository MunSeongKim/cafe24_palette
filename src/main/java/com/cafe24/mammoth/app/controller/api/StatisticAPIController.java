package com.cafe24.mammoth.app.controller.api;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cafe24.mammoth.app.domain.dto.StatisticDto;
import com.cafe24.mammoth.app.service.StatisticService;
import com.cafe24.mammoth.app.support.JSONResult;

/*
 * 18.08.02 통계용 controller 추가
 */

@RestController
@RequestMapping("/api/app")
@SessionAttributes({"mallId", "mallUrl"})
public class StatisticAPIController {
	@Autowired
	private StatisticService statisticService;
	
	@Autowired
	private StatisticDto statisticDto;
	
	HashMap<String, Long> map = new HashMap<String, Long>();
	
	@GetMapping(value="/pCount")
	public JSONResult getPanelCount(/*@ModelAttribute("mallId") String mallId*/) {
		// 일단 mallId 임시로 박아 놓겠음. 임시데이터 1,2,3
		Long panelCount = statisticService.getPanelCount(); // 패널 총 개수
		Long perPersonPanelCount = statisticService.getPerPersonPanelCount();
		System.out.println("statisticService.getPanelCount() ==> " + panelCount);
		
		map.put("totalPanelCount", panelCount);
		map.put("perPersonPanelCount", perPersonPanelCount);
		
		return JSONResult.success(map);
	}
}
