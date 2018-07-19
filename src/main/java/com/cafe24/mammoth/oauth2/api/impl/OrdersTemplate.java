package com.cafe24.mammoth.oauth2.api.impl;

import java.net.URI;
import java.util.List;

import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cafe24.mammoth.oauth2.api.Orders;
import com.cafe24.mammoth.oauth2.api.impl.json.Cafe24ApiJsonParser;
import com.cafe24.mammoth.oauth2.api.operation.OrdersOperations;
import com.cafe24.mammoth.oauth2.api.support.URIBuilder;

/**
 * Orders API 사용 템플릿
 * 
 * @version 1.0
 * @since 2018-07-11
 * @author MoonStar
 *
 */
public class OrdersTemplate implements OrdersOperations {
	private final String ORDERS_URL = "/api/v2/admin/orders";
	private URI apiUrl;
	private String baseUrl;
	private RestTemplate usingApiRestTemplate;
	
	// POST request 요청 데이터 가공에 사용
	// private ObjectMapper objectMapper;
	
	public OrdersTemplate() { }
	
	public OrdersTemplate(RestTemplate usingApiRestTemplate, String baseUrl) {
		this.baseUrl = baseUrl + ORDERS_URL;
		this.usingApiRestTemplate = usingApiRestTemplate;
		// this.objectMapper = new ObjectMapper();
	}
	
	@Override
	public List<Orders> getList(MultiValueMap<String, String> params){
		apiUrl = URIBuilder.buildApiUri(baseUrl, params);
		String jsonResult = usingApiRestTemplate.getForObject(apiUrl, String.class);
		Orders orders = Cafe24ApiJsonParser.parser(jsonResult, Orders.class);
		return orders.getList();
	}

	@Override
	public Integer count(MultiValueMap<String, String> params) {
		apiUrl = URIBuilder.buildApiUri(baseUrl, params, "count");
		String jsonResult = usingApiRestTemplate.getForObject(apiUrl, String.class);
		Orders orders = Cafe24ApiJsonParser.parser(jsonResult, Orders.class);
		return orders.getCount();
	}

}
