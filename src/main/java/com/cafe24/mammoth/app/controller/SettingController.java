package com.cafe24.mammoth.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cafe24.mammoth.app.support.SettingTab;

@Controller
@RequestMapping(value = "/setting")
@SessionAttributes({"mallId", "mallUrl"})
public class SettingController {

	@GetMapping(value = "/create")
	public String create(Model model) {
		model.addAttribute("tabs", new SettingTab());
		return "setting";
	}
	
	@GetMapping(value ="/update/{panelId}")
	public String update(Model model, @ModelAttribute("mallId") String mallId,
			@PathVariable("panelId") Long panelId) {
		model.addAttribute("tabs", new SettingTab());
		// Get data of panel
		return "setting";
	}
	
	@GetMapping(value="/preview")
	public String preview(Model model, @ModelAttribute("mallUrl") String mallUrl) {
		
		return "preview";
	}

	@PostMapping(value = "/save")
	public String save(Model model, @ModelAttribute("mallId") String mallId) {
		/*
		 * temp_panel table -> panel table 데이터 이동
		 */
		return "redirect:/"; // /WEB-INF/views/index.jsp
	}
}
