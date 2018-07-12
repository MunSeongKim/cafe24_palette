package com.cafe24.mammoth.oauth2.api.operation;

import java.util.List;

import org.springframework.util.MultiValueMap;

import com.cafe24.mammoth.oauth2.api.Orders;

public interface OrdersOperations {
	List<Orders> getList(MultiValueMap<String, String> params);
	int count(MultiValueMap<String, String> params);
	
	
}
