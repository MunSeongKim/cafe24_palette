package com.cafe24.mammoth.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.mammoth.oauth2.api.Store;
import com.cafe24.mammoth.oauth2.api.impl.StoreTemplate;

@Controller
public class ApiController {
	
	/*@Autowired
	StoreTemplate storeTemplate;*/
	
	@ResponseBody
	@RequestMapping("/apiTest")
	public String apiTest() {
		StoreTemplate storeTemplate = new StoreTemplate();
		
		Store store = storeTemplate.getStoreInfo();
		
		System.out.println(store);
		
		return store.toString();
	}
	
}
