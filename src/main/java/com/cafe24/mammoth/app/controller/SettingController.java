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
		func1.setImgPath("/assets/admin/image/test.gif");
		func1.setFilePath("/function/recent/recent.html");
		func1.setIsButton(false);
		func1.setPreviewPath("/tmp");
		
		Func func2 = new Func();
		func2.setName("scoll");
		func2.setNameEng("scoll");
		func2.setDesciption("scoll");
		func2.setImgPath("/assets/admin/image/test2.gif");
		func2.setFilePath("/assets/admin/function/scroll/scroll.html");
		func2.setIsButton(false);
		func2.setPreviewPath("/tmp");
		
		Func func3 = new Func();
		func3.setName("orderlist");
		func3.setNameEng("orderlist");
		func3.setDesciption("orderlist");
		func3.setImgPath("/assets/admin/image/test3.gif");
		func3.setFilePath("/assets/admin/function/orderlist/orderlist_popuplayer.html");
		func3.setIsButton(false);
		func3.setPreviewPath("/tmp");
		
		funcService.save(func1);
		funcService.save(func2);
		funcService.save(func3);
		
		Theme theme1 = new Theme();
		theme1.setTitle("theme1");
		theme1.setDescription("theme1!!");
		theme1.setCssFilePath("/assets/admin/template/testTheme1.css");
		theme1.setTitleImgPath("/assets/admin/image/beige.PNG");
		theme1.setPreviewImgPath("/assets/admin/image/theme_white_orange.png");
		 
		Theme theme2 = new Theme();
		theme2.setTitle("theme2");
		theme2.setDescription("theme2!!!"); 
		theme2.setCssFilePath("/assets/admin/template/testTheme2.css");
		theme2.setTitleImgPath("/assets/admin/image/strawberry.PNG");
		theme2.setPreviewImgPath("/assets/admin/image/theme_black_red.png");
		
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
			@RequestParam("themeid") Long themeId) {
		System.out.println("createPersist is called!!");
		// @RequestParam - primitive type의 값만 받을 수 있음. map이나 다른 형태 불가.-> 되는데?
		
		panelService.createPanel(funcId, funcOrder, themeId);
		
		/*for(int i=0; i<funcid.size(); i++) {
			System.out.println("funcid : "+funcid.get(i)+", funcorder : "+funcorder.get(i));
		}*/
		
		/*for(Entry<?, ?> entry : selectedFuncInfo.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}*/
		
		return "redirect:/"; 
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
	
	// 새 패널 만들기 - 패널 미리보기
	@GetMapping(value="/preview")
	public String preview(Model model) {
		
		System.out.println("[HELLO!!]");
		
		// funclist
		List<Func> funcs = funcService.getFuncList();
		model.addAttribute("funcs", funcs);
		return "preview_panel";
	}

}
