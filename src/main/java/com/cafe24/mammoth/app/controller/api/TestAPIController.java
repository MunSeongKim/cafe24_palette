package com.cafe24.mammoth.app.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.mammoth.app.domain.dto.Order;
import com.cafe24.mammoth.app.service.cafe24api.OrderAPIService;
import com.cafe24.mammoth.app.support.JSONResult;

@RestController
@RequestMapping("/api/test")
@CrossOrigin
public class TestAPIController {
	@Autowired
	private OrderAPIService orderAPIService;
	
	
	@GetMapping(value="/orders")
	public JSONResult orderList(@RequestParam("start_date") String startDate,
			@RequestParam("end_date") String endDate,
			@RequestParam("member_id") String memberId) {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("start_date", startDate);
		params.add("end_date", endDate);
		params.add("member_id", memberId);
		System.out.println(params);
		List<Order> orderList = orderAPIService.getOrderList(params);
		System.out.println(orderList);
		return JSONResult.success(orderList != null ? orderList : "null");
	}
	
//	@RequestMapping({"", "/"})
//	private Integer test() {
//		return orderAPIService.getCount();
//	}
}
