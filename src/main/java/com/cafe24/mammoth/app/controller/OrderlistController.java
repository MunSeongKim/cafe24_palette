package com.cafe24.mammoth.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/panel")
public class OrderlistController {

	@RequestMapping(value={"/", ""})
	public String panel() {
		return "template/skel.html";
	}
}
