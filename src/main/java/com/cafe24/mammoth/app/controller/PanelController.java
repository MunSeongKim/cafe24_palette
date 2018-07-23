package com.cafe24.mammoth.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/panel")
public class PanelController {
	
	public String panel() {
		return "/template/panel";
	}
}
