package com.cafe24.mammoth.app.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/panel")
public class OrderlistController {

	@RequestMapping(value={"/", ""})
	public String panel() {
		return "template/skel.html";
	}
	
	@RequestMapping(value="/loadjsp")
	public String load() {
		System.out.println("xkxkxkkxkxkkxkxkxkkxkxk");
		return "function/jsptest/loadtest.jsp";
	}
	
	@GetMapping(value="/movePage")
	public String load2() {
		return "template/skel2.html";
	}
}
