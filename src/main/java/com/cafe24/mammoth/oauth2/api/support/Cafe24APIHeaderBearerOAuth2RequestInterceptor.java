package com.cafe24.mammoth.oauth2.api.support;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * 
 * Cafe24 api를 사용할 때 Authorization header를 추가하는 인터셉터 class
 * @author qyuee
 *
 */
public class Cafe24APIHeaderBearerOAuth2RequestInterceptor implements ClientHttpRequestInterceptor{
		
		private String accessToken;
		
		public Cafe24APIHeaderBearerOAuth2RequestInterceptor(String accessToken) {
			this.accessToken = accessToken;
		}
		
		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
				throws IOException {
			
			//System.out.println(" >>> restTemplate 수행 전 동작하는 인터셉터!!");
			
			HttpRequest protectedResourceRequest = new HttpRequestDecorator(request);
			protectedResourceRequest.getHeaders().set("Authorization", "Bearer " + accessToken);
			protectedResourceRequest.getHeaders().set("Content-Type", "application/json");
			
			return execution.execute(protectedResourceRequest, body);
		}

	}