package com.cafe24.mammoth.oauth2.api.impl;

import java.net.URI;
import java.util.List;

import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cafe24.mammoth.oauth2.api.Products;
import com.cafe24.mammoth.oauth2.api.impl.json.Cafe24ApiJsonParser;
import com.cafe24.mammoth.oauth2.api.operation.ProductsOperations;
import com.cafe24.mammoth.oauth2.api.support.URIBuilder;

/**
 * Products API 사용 템플릿
 * 
 * @version 1.0
 * @since 2018-07-13
 * @author MoonStar
 *
 */
public class ProductsTemplate implements ProductsOperations {

	private final String PRODUCTS_URL = "/api/v2/admin/products";
	private URI apiUrl;
	private String baseUrl;
	private RestTemplate usingApiRestTemplate;
	
	public ProductsTemplate() {	}
	
	public ProductsTemplate(RestTemplate usingApiRestTemplate, String baseUrl) {
		this.baseUrl = baseUrl + PRODUCTS_URL;
		this.usingApiRestTemplate = usingApiRestTemplate;
	}
	
	
	@Override
	public Products get(String productNo) {
		apiUrl = URIBuilder.buildApiUri(baseUrl, productNo);
		String jsonResult = usingApiRestTemplate.getForObject(apiUrl, String.class);
		Products products = Cafe24ApiJsonParser.parser(jsonResult, Products.class);
		return products;
	}
	
	@Override
	public Products get(String productNo, MultiValueMap<String, String> params) {
		apiUrl = URIBuilder.buildApiUri(baseUrl, params, productNo);
		String jsonResult = usingApiRestTemplate.getForObject(apiUrl, String.class);
		Products products = Cafe24ApiJsonParser.parser(jsonResult, Products.class);
		return products;
	}

	@Override
	public List<Products> getList(MultiValueMap<String, String> params) {
		apiUrl = URIBuilder.buildApiUri(baseUrl, params);
		String jsonResult = usingApiRestTemplate.getForObject(apiUrl, String.class);
		Products products = Cafe24ApiJsonParser.parser(jsonResult, Products.class);
		return products.getList();
	}

	@Override
	public List<Products> getList() {
		apiUrl = URIBuilder.buildApiUri(baseUrl);
		String jsonResult = usingApiRestTemplate.getForObject(apiUrl, String.class);
		Products products = Cafe24ApiJsonParser.parser(jsonResult, Products.class);
		return products.getList();
	}

}
