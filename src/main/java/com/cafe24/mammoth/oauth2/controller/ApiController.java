package com.cafe24.mammoth.oauth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.mammoth.oauth2.api.Store;
import com.cafe24.mammoth.oauth2.api.impl.StoreTemplate;

@Controller
public class ApiController {
	
	@Autowired
	OAuth2ClientContext context;
	
	@ResponseBody
	@RequestMapping("/apiTest")
	public String apiTest() {
		StoreTemplate storeTemplate = new StoreTemplate(context);
		
		Store store = storeTemplate.getStoreInfo();
		
		System.out.println(store);
		
		return store.toString();
	}
	
}
