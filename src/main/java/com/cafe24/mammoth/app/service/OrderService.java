package com.cafe24.mammoth.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.cafe24.mammoth.app.domain.dto.Order;
import com.cafe24.mammoth.app.domain.dto.Product;
import com.cafe24.mammoth.oauth2.api.Orders;
import com.cafe24.mammoth.oauth2.api.Products;
import com.cafe24.mammoth.oauth2.api.impl.Cafe24Template;
import com.cafe24.mammoth.oauth2.api.impl.OrdersTemplate;
import com.cafe24.mammoth.oauth2.api.impl.ProductsTemplate;


/**
 * Cafe24 orders, products API에서 필요 정보를 추출하는 API<br>
 * 로그인 한 사용자의 선택 기간별 구매 목록을 구성<br>
 * 
 * @since 2018-07-13
 * @author MoonStar
 *
 */
@Service
public class OrderService {
	
	@Autowired
	private Cafe24Template cafe24Template;
	
	@Autowired
	private CacheManager cacheManager;
	
	private final String orderStatus = "N00,N10,N20,N21,N22,N30,N40";
	private final String fields = "shop_no,order_id,order_date,member_id,buyer_name,items";
	private final String embed = "items";
	
	public List<Order> getOrderList(MultiValueMap<String, String> params, String mallId) {
		Cache cache = (Cache) cacheManager.getCache("products");
		
		// 요청 파라미터 구성
		params.add("order_status", orderStatus);
		params.add("fields", fields);
		params.add("embed", embed);
		
		// API processing
		OrdersTemplate ordersTemplate = cafe24Template.getOperation(OrdersTemplate.class);
		List<Orders> ordersResult = ordersTemplate.getList(params);
		List<Order> orderList = new ArrayList<Order>();
		// order 리스트에서 orderId, orderDate, order item 추출
		for(Orders o: ordersResult) {
			Order order = new Order();
			order.setOrderId(o.getOrderId());
			order.setOrderDate(o.getOrderDate());
			for(Map<String, String> i: o.getItems()) {
				// 수동 캐시작업 - Cache 동작은 AOP 기반으로 동작하기 때문에 내부에서 호출하는 메소드에는 캐시가 적용될 수 없음
				Product product = cache.get((mallId+i.get("product_no")), Product.class);
				// 캐시에 없으면 product 정보 요청
				if( product == null) {
					product = getProduct(mallId, i.get("product_no"));
				}
				
				// orderItem 구성
				order.addItem(order.newItemInstance(i.get("item_no"), 
						i.get("product_no"), i.get("product_name"), i.get("product_price"),
						i.get("option_value"), i.get("quantity"), i.get("additional_discount_price"), product));
			}
			orderList.add(order);
		}
		
		// Return to browser
		return orderList;
	}

	public Integer getCount() {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("start_date", "2018-07-01");
		params.add("end_date", "2018-07-12");
		params.add("order_status", orderStatus);
		params.add("member_id", "chyin370");
		OrdersTemplate ordersTemplate = cafe24Template.getOperation(OrdersTemplate.class);
		return ordersTemplate.count(params);
	}
	
	// product 정보 요청
	private Product getProduct(String mallId, String productNo) {
		//Request Parameters
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("fields", "product_no,small_image,category,product_tag");
		// Communicate to API Server
		ProductsTemplate productsTemplate = cafe24Template.getOperation(ProductsTemplate.class);
		// API Response
		Products products = productsTemplate.get(productNo, params);
		// Convert response to DTO
		Product product = new Product();
		product.setProductNo(products.getProductNo());
		product.setSmallImage(products.getSmallImage());
		product.setCategories(products.getCategories());
		product.setTags(products.getProductTag());
		
		// 캐시에 데이터 삽입
		cacheManager.getCache("products").put((mallId + productNo), product);
		return product;
	}

}
