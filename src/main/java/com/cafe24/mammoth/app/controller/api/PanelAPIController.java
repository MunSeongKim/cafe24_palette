package com.cafe24.mammoth.app.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.mammoth.app.domain.Script;
import com.cafe24.mammoth.app.service.PanelService;
import com.cafe24.mammoth.app.service.ScriptService;
import com.cafe24.mammoth.app.support.JSONResult;

@RestController
@RequestMapping("/api/cafe24")
public class PanelAPIController {
	@Autowired
	ScriptService scriptService;
	@Autowired
	PanelService panelService;
	
	@PostMapping(value = "/apply/{panelId}")
	public JSONResult pageselect(@RequestBody Map<String, Object> map, @PathVariable("panelId") Long panelId) {
		Map<String, Script> result = scriptService.applyPanel(map, panelId);
		System.out.println(panelId);
		return JSONResult.success(result != null ? result : "null");
	}
	
	@DeleteMapping(value="/panel/{panelId}")
	public JSONResult delete(@PathVariable("panelId") long panelId) {
		panelService.removePanel(panelId);
		
		return JSONResult.success("removed");
	}
	
}
