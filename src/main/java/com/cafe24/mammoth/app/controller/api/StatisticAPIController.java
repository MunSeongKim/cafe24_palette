package com.cafe24.mammoth.app.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cafe24.mammoth.app.domain.dto.Statistic;
import com.cafe24.mammoth.app.service.StatisticService;
import com.cafe24.mammoth.app.support.JSONResult;

/**
 * 18.08.02 통계를 처리하는 API 컨트롤러
 * @author Allan
 */
@RestController
@RequestMapping("/api/app")
@SessionAttributes({ "mallId", "mallUrl" })
public class StatisticAPIController {
	
	@Autowired
	private StatisticService statisticService;

	// panel 통계 관련 메소드
	@GetMapping(value = "/pCount")
	public JSONResult getPanelCount() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 패널 총 개수
		Long panelCount = statisticService.getPanelCount();
		// 한 사람당 평균 패널 사용 개수
		double perPersonPanelCount = statisticService.getPerPersonPanelCount();

		map.put("totalPanelCount", panelCount);
		map.put("perPersonPanelCount", perPersonPanelCount);

		return JSONResult.success(map);
	}

	// function 통계 관련 메소드
	@GetMapping(value = "/fCount")
	public JSONResult getFunctionCount() {
		// 각 function의 사용 개수 - 여러개의 row가 나옴
		List<Object[]> list = statisticService.getFunctionCount();
		// 여러 row를 받기 위한 list
		List<Statistic> list3 = new ArrayList<Statistic>();

		// 여러개의 row를 하나씩 돌리면서 list3에 넣어줌.
		for (Object[] obj : list) {
			Statistic tempObj = new Statistic();
			tempObj.setFunctionId((Long) obj[0]); // functionId
			tempObj.setFunctionName((String) obj[1]); // function Korean Name
			tempObj.setFunctionCount((Long) obj[2]);  // function total count
			list3.add(tempObj);
		}

		return JSONResult.success(list3);
	}
	
}
