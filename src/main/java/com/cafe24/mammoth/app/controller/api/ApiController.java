package com.cafe24.mammoth.app.controller.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.mammoth.app.domain.Script;
import com.cafe24.mammoth.app.service.PanelService;
import com.cafe24.mammoth.app.service.ScriptService;
import com.cafe24.mammoth.app.support.JSONResult;

@RestController
@RequestMapping("/{mid}/api")
public class ApiController {
	@Autowired
	ScriptService sservice;
	@Autowired
	PanelService pservice;
	
	@RequestMapping(value = "/{panelId}/isapply", method = RequestMethod.PUT)
	public JSONResult pageselect(@RequestBody Map<String, Object> map, @PathVariable("panelId") Long panelId) {
		Map<String, Script> result = sservice.updateIsApplyByPanelId(map, panelId);

		return JSONResult.success(result != null ? result : "null");
	}
	
	@RequestMapping(value="/delete")
	public JSONResult delete(@RequestParam(value="id") long panelId) {
		pservice.removePanel(panelId);
		
		return JSONResult.success("removed");
	}
	
	
	
	
	
	
}
