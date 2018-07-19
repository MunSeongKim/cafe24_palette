package com.cafe24.mammoth.oauth2.api.operation;

import java.util.List;

import org.springframework.util.MultiValueMap;

import com.cafe24.mammoth.oauth2.api.Products;

public interface ProductsOperations {

	Products get(String productNo);

	Products get(String productNo, MultiValueMap<String, String> params);
	
	List<Products> getList();

	List<Products> getList(MultiValueMap<String, String> params);

	
	
}
