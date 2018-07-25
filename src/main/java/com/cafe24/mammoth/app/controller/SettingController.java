package com.cafe24.mammoth.app.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cafe24.mammoth.app.domain.Func;
import com.cafe24.mammoth.app.domain.Theme;
import com.cafe24.mammoth.app.domain.enumerate.PanelType;
import com.cafe24.mammoth.app.domain.enumerate.Position;
import com.cafe24.mammoth.app.service.FuncService;
import com.cafe24.mammoth.app.service.PanelService;
import com.cafe24.mammoth.app.service.ThemeService;
import com.cafe24.mammoth.app.support.SettingTab;

@Controller
@RequestMapping(value = "/setting")
@SessionAttributes({"mallId", "mallUrl"})
public class SettingController {
	
	/*@PostConstruct
	public void init() {
		Func func1 = new Func();
		func1.setName("recent"); 
		func1.setNameEng("recent"); 
		func1.setDesciption("recent");
		func1.setImgPath("/admin/image/test.gif");
		func1.setFilePath("/template/function/recent/recent.html");
		func1.setIsButton(false);
		func1.setPreviewPath("/tmp");
		
		Func func2 = new Func();
		func2.setName("scoll");
		func2.setNameEng("scoll");
		func2.setDesciption("scoll");
		func2.setImgPath("/admin/image/test2.gif");
		func2.setFilePath("/template/function/scroll/scroll.html");
		func2.setIsButton(false);
		func2.setPreviewPath("/tmp");
		
		Func func3 = new Func();
		func3.setName("orderlist");
		func3.setNameEng("orderlist");
		func3.setDesciption("orderlist");
		func3.setImgPath("/admin/image/test3.gif");
		func3.setFilePath("/template/function/orderlist/orderlist_popuplayer.html");
		func3.setIsButton(false);
		func3.setPreviewPath("/tmp");
		
		funcService.save(func1);
		funcService.save(func2);
		funcService.save(func3);
		
		Theme theme1 = new Theme();
		theme1.setTitle("theme1");
		theme1.setDescription("theme1!!");
		theme1.setCssFilePath("/template/testTheme1.css");
		theme1.setTitleImgPath("/admin/image/beige.PNG");
		theme1.setPreviewImgPath("/admin/image/theme_white_orange.png");
		 
		Theme theme2 = new Theme();
		theme2.setTitle("theme2");
		theme2.setDescription("theme2!!!"); 
		theme2.setCssFilePath("/template/testTheme2.css");
		theme2.setTitleImgPath("/admin/image/strawberry.PNG"); 
		theme2.setPreviewImgPath("/admin/image/theme_black_red.png");
		
		themeService.saveTheme(theme1);
		themeService.saveTheme(theme2);
	}*/
	
	@Autowired
	private FuncService funcService;
	
	@Autowired
	private ThemeService themeService;
	
	@Autowired
	private PanelService panelService;

	@GetMapping(value = "/create")
	public String create(Model model) {
		List<Func> funcs = funcService.getFuncList();
		List<Theme> themeList = themeService.getThemeList();
		List<PanelType> panelTypes = Arrays.asList(PanelType.values());
		List<Position> positions = Arrays.asList(Position.values());
		
		model.addAttribute("positions", positions);
		model.addAttribute("panelTypes", panelTypes);
		model.addAttribute("tabs", new SettingTab());
		model.addAttribute("funcs", funcs);
		model.addAttribute("themes", themeList);
		return "setting";
	}
	
	@PostMapping(value = "/create")
	public String createPersist( 
			@RequestParam("funcid") List<Long> funcId,
			@RequestParam("funcorder") List<Long> funcOrder,
			@RequestParam("themeid") Long themeId,
			@RequestParam("position") String position) {
		System.out.println("createPersist is called!!");
		
		panelService.createPanel(funcId, funcOrder, themeId, position);
		
		return "redirect:/"; 
	}
	
	// iframne올 띄우기 
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
	
	// 새 패널 만들기 - 패널 미리보기
	@GetMapping(value="/preview")
	public String preview(Model model) {
		// funclist
		List<Func> funcs = funcService.getFuncList();
		model.addAttribute("funcs", funcs);
		return "preview_panel";
	}

	// 대쉬 보드 테스트
	@GetMapping(value="/dashboard")
	public String dashboard() {
		return "dashboard"; 
	}
}
