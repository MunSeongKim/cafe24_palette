package com.cafe24.mammoth.app.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cafe24.mammoth.app.domain.dto.Order;
import com.cafe24.mammoth.app.service.OrderService;
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
@RequestMapping("/api/cafe24")
@CrossOrigin
@SessionAttributes("mallId")
public class OrderAPIController {
	@Autowired
	private OrderService orderService;
	
	
	@GetMapping(value="/orders")
	public JSONResult orderList(@RequestParam("start_date") String startDate,
			@RequestParam("end_date") String endDate,
			@RequestParam(value="member_id", required=true) String member_id, @ModelAttribute String mallId) {
			//,@RequestParam(value="buyer_name", required=false, defaultValue="") String buyer_name) {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("start_date", startDate);
		params.add("end_date", endDate);
		params.add("member_id", member_id);
		
		
		//params.add("buyer_name", buyer_name);
		List<Order> orderList = orderService.getOrderList(params, mallId);
		return JSONResult.success(orderList != null ? orderList : "null");
	}
}
