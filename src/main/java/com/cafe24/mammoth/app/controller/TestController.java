package com.cafe24.mammoth.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.mammoth.app.service.OrderAPIService;
import com.cafe24.mammoth.oauth2.api.Orders;

@RestController
@RequestMapping("/test")
public class TestController {
	@Autowired
	private OrderAPIService orderAPIService;
	
	
	@RequestMapping({"", "/"})
	private List<Orders> test() {
		List<Orders> orders = orderAPIService.getOrderList();
		return orders;
	}
}
