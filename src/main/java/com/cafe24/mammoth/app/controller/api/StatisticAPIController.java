package com.cafe24.mammoth.app.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cafe24.mammoth.app.domain.Function;
import com.cafe24.mammoth.app.domain.dto.StatisticDto;
import com.cafe24.mammoth.app.service.StatisticService;
import com.cafe24.mammoth.app.support.JSONResult;

/*
 * 18.08.02 ?듦퀎??controller 異붽?
 */

@RestController
@RequestMapping("/api/app")
@SessionAttributes({"mallId", "mallUrl"})
public class StatisticAPIController {
	@Autowired
	private StatisticService statisticService;
	
	@Autowired
	private StatisticDto statisticDto;
	
	@GetMapping(value="/pCount")
	public JSONResult getPanelCount(/*@ModelAttribute("mallId") String mallId*/) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// ?쇰떒 mallId ?꾩떆濡?諛뺤븘 ?볤쿋?? ?꾩떆?곗씠??1,2,3
		Long panelCount = statisticService.getPanelCount(); // ?⑤꼸 珥?媛쒖닔
		double perPersonPanelCount = statisticService.getPerPersonPanelCount();
		System.out.println("statisticService.getPanelCount() ==> " + panelCount);
		
		map.put("totalPanelCount", panelCount);
		map.put("perPersonPanelCount", perPersonPanelCount);
		
		return JSONResult.success(map);
	}
	
	@GetMapping(value="/fCount")
	public JSONResult getFunctionCount(/*@ModelAttribute("mallId") String mallId*/) {
		List<Object[]> list = statisticService.getFunctionCount();
		List<StatisticDto> list3 = new ArrayList<StatisticDto>();
		
		for(Object[] obj : list) {
			StatisticDto tempObj = new StatisticDto();
			tempObj.setFunctionId((Long)obj[0]);
			tempObj.setFunctionName((String)obj[1]);
			tempObj.setFunctionCount((Long)obj[2]);
			list3.add(tempObj);
		}
			
		//System.out.println("map ==> " + map);
		
		return JSONResult.success(list3);
	}
}
