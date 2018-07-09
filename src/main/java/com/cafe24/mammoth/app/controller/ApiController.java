package com.cafe24.mammoth.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.mammoth.oauth2.api.Scripttags;
import com.cafe24.mammoth.oauth2.api.Themes;
import com.cafe24.mammoth.oauth2.api.impl.Cafe24Template;
import com.cafe24.mammoth.oauth2.api.impl.ScriptTagsTemplate;
import com.cafe24.mammoth.oauth2.api.impl.ThemesTemplate;

@Controller
public class ApiController {

	@Autowired
	private Cafe24Template cafe24Template;

	@ResponseBody
	@RequestMapping("/scripttags/apiTest")
	public List<Scripttags> scriptTagsApiTest() {
		ScriptTagsTemplate scriptTagsTemplate = cafe24Template.getOperation(ScriptTagsTemplate.class);
		List<Scripttags> list = scriptTagsTemplate.getList();
		for (Scripttags scripttags : list) {
			System.out.println("API TEST!!! : "+scripttags);
		}
		return list;
	}
	
	@ResponseBody
	@RequestMapping("/themes/apiTest")
	public List<Themes> themesApiTest() {
		ThemesTemplate themesTemplate = cafe24Template.getOperation(ThemesTemplate.class);
		List<Themes> list = themesTemplate.getList();
		for (Themes themes : list) {
			System.out.println("API TEST!!! : "+themes);
		}
		return list;
	}

}
