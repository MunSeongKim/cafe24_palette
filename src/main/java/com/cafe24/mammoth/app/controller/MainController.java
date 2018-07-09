package com.cafe24.mammoth.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafe24.mammoth.app.domain.Panel;
import com.cafe24.mammoth.app.service.PanelService;
import com.cafe24.mammoth.app.service.ScriptService;

// @RestController : data return
// -> 객체를 반환하면 객체 데이터는 JSON/XML 형식의 HTTP 응답 작성
// -> @Controller + @ResponseBody
@Controller		// : view(화면) return
@RequestMapping(value="/{mid}")
public class MainController {
	
	@Autowired
	PanelService pservice;
	@Autowired
	ScriptService sservice;
	
	@RequestMapping(value= {"", "/"})
	public String main(Model model, @PathVariable("mid") String mid) {
		
		List<Panel> list = pservice.getPanelList();
		
		model.addAttribute("mid", mid);
		model.addAttribute("center", "main");
		model.addAttribute("list", list);
		return "index";	// /WEB-INF/views/index.jsp
	}
	
	@RequestMapping(value="/save")
	public String save(Model model, @PathVariable("mid") String mid) {
		/*
		 * temp_panel table -> panel table 데이터 이동
		 */
		return "redirect:/"+mid;	// /WEB-INF/views/index.jsp
	}
	
	@RequestMapping(value="/{panelId}/update")
	public String update(@PathVariable("panelId") Long panelId) {
		
		return "update";
		
	}
	


}
