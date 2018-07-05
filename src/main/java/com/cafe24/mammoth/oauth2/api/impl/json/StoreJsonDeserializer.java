package com.cafe24.mammoth.oauth2.api.impl.json;

import java.io.IOException;

import com.cafe24.mammoth.oauth2.api.Store;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class StoreJsonDeserializer extends JsonDeserializer<Store>{

	@Override
	public Store deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String shopNo = null;
		String shopName = null;
		String mallId = null;
		
		while(p.nextToken() != JsonToken.END_OBJECT) {
			String name = p.getCurrentName();
			
			p.nextToken();
			
			if(name.equals("shop_no")) {
				shopNo = p.getText();
			}else if(name.equals("shop_name")) {
				shopName = p.getText();
			}else if(name.equals("mall_id")) {
				mallId = p.getText();
			}
		}
		
		Store store = new Store();
		
		store.setShopName(shopName);
		store.setShopNo(shopNo);
		store.setMallId(mallId);
		
		return store;
	}
	
}
