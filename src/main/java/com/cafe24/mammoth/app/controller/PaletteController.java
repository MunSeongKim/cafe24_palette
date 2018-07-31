package com.cafe24.mammoth.app.controller;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafe24.mammoth.app.domain.Member;
import com.cafe24.mammoth.app.domain.Panel;
import com.cafe24.mammoth.app.domain.SelectFunc;
import com.cafe24.mammoth.app.service.MemberService;
import com.cafe24.mammoth.app.service.PanelService;

/**
 * 패널 적용 후 쇼핑몰에서 요청하는 jsp를 응답하기 위한 컨트롤러
 * 
 * @since 18-07-31
 * @author MoonStar
 *
 */
@Controller
@RequestMapping("/palette")
public class PaletteController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PanelService panelService;
	
	@RequestMapping("/{mallUrl}")
	public String palette(@PathVariable("mallUrl") String mallUrl, Model model) {
		Member member = memberService.getOneByMallUrl(mallUrl);
		Panel panel = panelService.getApplyPanel(member.getMallId());
		List<SelectFunc> selectFuncs = panel.getSelectFuncs();
		
		// 기능 순서 내림차순 정렬
		selectFuncs.sort(new Comparator<SelectFunc>() {
			@Override
			public int compare(SelectFunc source, SelectFunc target) {
				if(source.getFuncOrder() < target.getFuncOrder()) {
					return -1;
				} else if(source.getFuncOrder() == target.getFuncOrder()) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		
		model.addAttribute("panel", panel);
		model.addAttribute("theme", panel.getTheme());
		model.addAttribute("selectFuncs", selectFuncs);
		
		return "template/panel";
	}
}