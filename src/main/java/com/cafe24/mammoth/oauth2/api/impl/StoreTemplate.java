package com.cafe24.mammoth.oauth2.api.impl;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cafe24.mammoth.oauth2.api.Store;
import com.cafe24.mammoth.oauth2.api.operation.StoreOperations;
import com.cafe24.mammoth.oauth2.api.support.Cafe24ApiHeaderBearerOAuth2RequestInterceptor;
import com.cafe24.mammoth.oauth2.api.support.URIBuilder;

/**
 * stop
 * @author qyuee
 * @since 2018-07-06
 */
public class StoreTemplate implements StoreOperations{
	
	OAuth2ClientContext context;
	private OAuth2AccessToken oAuth2AccessToken;
	private String accessToken;
	private RestTemplate usingApiRestTemplate;
	
	public StoreTemplate(OAuth2ClientContext context) {
		this.context = context;
		this.oAuth2AccessToken = context.getAccessToken();
		this.accessToken = oAuth2AccessToken.getValue();
	}
	
	public StoreTemplate(String accessToken) {
		this.accessToken = accessToken;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Store getStoreInfo() {
		
		usingApiRestTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());
		usingApiRestTemplate.setInterceptors(Arrays.asList(new ClientHttpRequestInterceptor[]{new Cafe24ApiHeaderBearerOAuth2RequestInterceptor(accessToken)}));
		
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("shop_no", "1");
		
		URI apiUrl = buildApiUri("/api/v2/admin/store", parameters);
		
		System.out.println(" >>> apiUrl.getPath() : "+apiUrl.getPath());
		
		String str = usingApiRestTemplate.getForEntity(apiUrl, String.class).getBody();
		
		System.out.println("str : "+str);
		
		Map<String, Object> result = usingApiRestTemplate.getForEntity(apiUrl, Map.class).getBody();
		
		Map<String, Object> store = (Map<String, Object>) result.get("store");
		
		System.out.println("store :"+store);
		
		return usingApiRestTemplate.getForObject(apiUrl, Store.class);
		
	}
	
	protected URI buildApiUri(String path, MultiValueMap<String, String> parameters) {
		return URIBuilder.fromUri("https://qyuee.cafe24.com" + path).queryParams(parameters).build();
	}
	
}
