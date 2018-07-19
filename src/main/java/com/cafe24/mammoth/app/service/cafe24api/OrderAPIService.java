package com.cafe24.mammoth.app.service.cafe24api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
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
public class OrderAPIService {
	
	@Autowired
	private Cafe24Template cafe24Template;
	
	@Autowired
	private CacheManager cacheManager;
	
	private final String orderStatus = "N00,N10,N20,N21,N22,N30,N40";
	private final String fields = "shop_no,order_id,order_date,member_id,items";
	private final String embed = "items";
	
	public List<Order> getOrderList(MultiValueMap<String, String> params) {
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
				String productNo = i.get("product_no");
				// 수동 캐시작업
				Product product = cache.get(productNo, Product.class);
				// 캐시에 없으면 product 정보 요청
				if( product == null) {
					product = getProduct(i.get("product_no"));
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
	@Cacheable(value="products",key="#productNo")
	private Product getProduct(String productNo) {
		//Request Parameters
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("fields", "product_no,small_image,category");
		// Communicate to API Server
		ProductsTemplate productsTemplate = cafe24Template.getOperation(ProductsTemplate.class);
		// API Response
		Products products = productsTemplate.get(productNo, params);
		// Convert response to DTO
		Product product = new Product();
		product.setProductNo(products.getProductNo());
		product.setSmallImage(product.getSmallImage());
		product.setCategories(products.getCategories());
		System.out.println("--------------product API called ------------------");
		System.out.println(product);
		cacheManager.getCache("products").put(productNo, product);
		return product;
	}

}
