package com.cafe24.mammoth.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cafe24.mammoth.app.domain.Panel;
import com.cafe24.mammoth.app.service.PanelService;

// @RestController : data return
// -> 객체를 반환하면 객체 데이터는 JSON/XML 형식의 HTTP 응답 작성
// -> @Controller + @ResponseBody
@Controller // : view(화면) return
@RequestMapping(value = "/")
@SessionAttributes({"mallId", "mallUrl"})
public class MainController {
	
	@Autowired
	private PanelService panelService;
	
	@GetMapping(value = { "", "/" })
	public String main(@ModelAttribute("mallId") String mallId, Model model) {
		
		List<Panel> list = panelService.getPanelList(mallId);
		
		model.addAttribute("list", list);
		return "main";
	}
}
