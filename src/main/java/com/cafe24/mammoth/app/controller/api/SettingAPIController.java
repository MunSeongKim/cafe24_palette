package com.cafe24.mammoth.app.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.mammoth.app.service.PanelService;
import com.cafe24.mammoth.app.support.APITokenInterceptor;
import com.cafe24.mammoth.app.support.JSONResult;

/**
 * Cafe24 API를 사용하지 않는 RESTController
 * {@link APITokenInterceptor}의 걸리지 않는다.
 * 
 * @author qyuee
 */
@RestController
@RequestMapping("/api/app")
public class SettingAPIController {
	@Autowired
	PanelService panelService;
	
	@PostMapping(value = "/panel/confirmPName")
	public JSONResult canUsePanelName(@RequestBody String name){
		System.out.println("name : "+name);
		
		boolean result = panelService.isExistByName(name);
		return JSONResult.success(result == true ? "non-exist" : "exist");
	}
	
	@DeleteMapping(value="/panel/{panelId}")
	public JSONResult delete(@PathVariable("panelId") long panelId) {
		panelService.removePanel(panelId);
		
		return JSONResult.success("removed");
	}
}
