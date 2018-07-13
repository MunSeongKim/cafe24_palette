package com.cafe24.mammoth.app.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.mammoth.app.domain.dto.Order;
import com.cafe24.mammoth.app.service.cafe24api.OrderAPIService;
import com.cafe24.mammoth.oauth2.api.Orders;

@RestController
@RequestMapping("/test")
public class TestAPIController {
	@Autowired
	private OrderAPIService orderAPIService;
	
	
	@RequestMapping({"", "/"})
	private List<Order> test() {
		List<Order> orders = orderAPIService.getOrderList();
		return orders;
	}
	
//	@RequestMapping({"", "/"})
//	private Integer test() {
//		return orderAPIService.getCount();
//	}
}
