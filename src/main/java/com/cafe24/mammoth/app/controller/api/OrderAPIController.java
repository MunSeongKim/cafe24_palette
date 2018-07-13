package com.cafe24.mammoth.app.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.mammoth.app.domain.dto.Order;
import com.cafe24.mammoth.app.service.cafe24api.OrderAPIService;
import com.cafe24.mammoth.app.support.JSONResult;


/**
 * 구매목록 조회 API 컨트롤러<br>
 * 요청 parameter 양식
 * <pre>
 * {
 *   "start_date": "",
 *   "end_date": "",
 *   "member_id": ""
 * }
 * </pre>
 * @since 18-07-13
 * @author MoonStar
 *
 */
@RestController
@RequestMapping("/api")
public class OrderAPIController {
	@Autowired
	private OrderAPIService orderAPIService;
	
	
	@GetMapping(value="/orderlist")
	public JSONResult orderList(@RequestBody MultiValueMap<String, String> data) {
		List<Order> orderList = orderAPIService.getOrderList(data);
		
		return JSONResult.success(orderList != null ? orderList : "null");
	}
}
