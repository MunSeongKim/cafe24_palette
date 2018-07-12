package com.cafe24.mammoth.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.cafe24.mammoth.oauth2.api.Orders;
import com.cafe24.mammoth.oauth2.api.impl.Cafe24Template;
import com.cafe24.mammoth.oauth2.api.impl.OrdersTemplate;

@Service
public class OrderAPIService {
	@Autowired
	private Cafe24Template cafe24Template;
	
	private final String orderStatus = "N00,N10,N20,N21,N22,N30,N40";
	
	public List<Orders> getOrderList() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("start_date", "2018-07-01");
		params.add("end_date", "2018-07-12");
		params.add("order_status", orderStatus);
		params.add("member_id", "chyin370");
		params.add("fields", "shop_no,order_id,order_date,member_id,items");
		params.add("embed", "items");
		//params.add("embed", "items");
		OrdersTemplate ordersTemplate = cafe24Template.getOperation(OrdersTemplate.class);
		
		return ordersTemplate.getList(params);
	}

}
