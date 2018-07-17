package com.cafe24.mammoth.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cafe24.mammoth.app.domain.Func;
import com.cafe24.mammoth.app.support.SettingTab;

@Controller
@RequestMapping(value = "/setting")
@SessionAttributes({"mallId", "mallUrl"})
public class SettingController {

	@GetMapping(value = "/create")
	public String create(Model model) {
		List<Func> list = new ArrayList<>();
		
		Func func1 = new Func();
		func1.setName("최근 본 상품"); 
		func1.setNameEng("recent");
		func1.setDesciption("이 기능을 선택하여 사용자들이 더욱 최근 본 상품에 대한 구매율을 높일 수 있도록\r\n" + 
				"			하세요. 많은 사용자들은 여러 상품을 조회하고 구매까지 이어지는 경우는 생각보다 많지\r\n" + 
				"			않습니다.");
		func1.setImgpath("../admin/assets/image/test.gif");
		
		Func func2 = new Func();
		func2.setName("스크롤");
		func2.setNameEng("scoll");
		func2.setDesciption("이 기능은 고객이 홈페이지 방문 시 더욱 빠르고 효과적으로 쇼핑을 할 수 있도록 지원합니다."
				+ "이는 사용자들에게 상품에 대한 접근성을 높여주고 사용자UX를 향상시킵니다.");
		func2.setImgpath("../admin/assets/image/test2.gif");
		
		Func func3 = new Func();
		func3.setName("구매 목록");
		func3.setNameEng("buylist");
		func3.setDesciption("이 기능은 사용자들이 구매목록을 페이지의 이동 없이 할 수 있도록 지원하는 기능입니다.");
		func3.setImgpath("../admin/assets/image/test3.gif");
		
		list.add(func1);
		list.add(func2);
		list.add(func3);
		
		model.addAttribute("tabs", new SettingTab());
		model.addAttribute("funcs", list);
		return "setting";
	}
	
	@GetMapping(value="/testPage")
	public String testPage() {
		
		return "test"; 
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
