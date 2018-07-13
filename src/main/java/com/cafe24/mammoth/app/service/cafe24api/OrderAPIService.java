package com.cafe24.mammoth.app.service.cafe24api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
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


@Service
public class OrderAPIService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderAPIService.class);
	
	@Autowired
	private Cafe24Template cafe24Template;
	@Autowired
	private EhCacheCacheManager cacheManager;
	
	private final String orderStatus = "N00,N10,N20,N21,N22,N30,N40";
	private final String fields = "shop_no,order_id,order_date,member_id,items";
	private final String embed = "items";
	
	public List<Order> getOrderList(MultiValueMap<String, String> params) {
		Cache cache = cacheManager.getCache("products");
		
		params.add("order_status", orderStatus);
		params.add("fields", fields);
		params.add("embed", embed);
		
		// Processing API
		OrdersTemplate ordersTemplate = cafe24Template.getOperation(OrdersTemplate.class);
		List<Orders> ordersResult = ordersTemplate.getList(params);
		List<Order> orderList = new ArrayList<Order>();
		for(Orders o: ordersResult) {
			Order order = new Order();
			order.setOrderId(o.getOrderId());
			order.setOrderDate(o.getOrderDate());
			for(Map<String, String> i: o.getItems()) {
				System.out.println(cache.get("9"));
				Product product = getProduct(i.get("product_no"));
				
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
	
	@Cacheable(value="products")
	Product getProduct(String productNo) {
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
		
		System.out.println("----------------- products API called ----------------");
		System.out.println(products);
		System.out.println(product + "\n");
		return product;
	}

}
