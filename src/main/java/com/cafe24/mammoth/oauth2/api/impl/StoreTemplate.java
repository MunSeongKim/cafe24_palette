package com.cafe24.mammoth.oauth2.api.impl;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cafe24.mammoth.oauth2.api.Store;
import com.cafe24.mammoth.oauth2.api.StoreOperations;
import com.cafe24.mammoth.oauth2.api.support.HttpRequestDecorator;
import com.cafe24.mammoth.oauth2.api.support.URIBuilder;

public class StoreTemplate implements StoreOperations {

	@Autowired
	OAuth2ClientContext context;

	private String accessToken;
	private RestTemplate usingApiRestTemplate;

	public StoreTemplate() {
		this.accessToken = context.getAccessToken().getValue();
	}

	public StoreTemplate(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public Store getStoreInfo() {

		usingApiRestTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		usingApiRestTemplate.setInterceptors(Arrays.asList(
				new ClientHttpRequestInterceptor[] { new Cafe24ApiHeaderBearerOAuth2RequestInterceptor(accessToken) }));

		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("shop_no", "1");

		URI apiUrl = buildApiUri("/api/v2/admin/store", parameters);

		System.out.println(" >>> apiUrl.getPath() : " + apiUrl.getPath());

		return usingApiRestTemplate.getForObject(apiUrl, Store.class);

	}

	protected URI buildApiUri(String path, MultiValueMap<String, String> parameters) {
		return URIBuilder.fromUri("https://qyuee.cafe24.com" + path).queryParams(parameters).build();
	}

	/**
	 * 
	 * Cafe24 api를 사용할 때 Authorization header를 추가하는 인터셉터 class
	 * 
	 * @author bit
	 *
	 */
	class Cafe24ApiHeaderBearerOAuth2RequestInterceptor implements ClientHttpRequestInterceptor {

		private String accessToken;

		public Cafe24ApiHeaderBearerOAuth2RequestInterceptor(String accessToken) {
			this.accessToken = accessToken;
		}

		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
				throws IOException {

			HttpRequest protectedResourceRequest = new HttpRequestDecorator(request);
			protectedResourceRequest.getHeaders().set("Authorization", "Bearer " + accessToken);
			return execution.execute(protectedResourceRequest, body);
		}

	}
}
