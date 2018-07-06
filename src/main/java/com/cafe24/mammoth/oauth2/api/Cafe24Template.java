package com.cafe24.mammoth.oauth2.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.client.RestTemplate;

import com.cafe24.mammoth.oauth2.api.impl.ScriptTagsTemplate;
import com.cafe24.mammoth.oauth2.api.impl.ThemesTemplate;
import com.cafe24.mammoth.oauth2.api.support.Cafe24ApiHeaderBearerOAuth2RequestInterceptor;

public class Cafe24Template implements Cafe24 {

	private OAuth2AccessToken oAuth2AccessToken;
	private String accessToken;
	private RestTemplate usingApiRestTemplate;
	
	private ScriptTagsOperations scriptTagsOperations;
	private ThemesOperations themesOperations;

	public Cafe24Template(String accessToken) {
		this.accessToken = accessToken;
		initallize();
	}
	
	public Cafe24Template(OAuth2ClientContext context) {
		this.oAuth2AccessToken = context.getAccessToken();
		this.accessToken = oAuth2AccessToken.getValue();
		initallize();
	}

	private void initallize() {
		usingApiRestTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new Cafe24ApiHeaderBearerOAuth2RequestInterceptor(accessToken));
		usingApiRestTemplate.setInterceptors(interceptors);

		/*List<HttpMessageConverter<?>> list = usingApiRestTemplate.getMessageConverters();

		// 등록된 messageConverter log
		for (HttpMessageConverter<?> messageConverter : list) {
			System.out.println(messageConverter.getClass().getName());
		}*/
		
		initApi();
	}
	
	private void initApi() {
		scriptTagsOperations = new ScriptTagsTemplate(usingApiRestTemplate);
		themesOperations = new ThemesTemplate(usingApiRestTemplate);
	}

	@Override
	public ScriptTagsOperations scriptTagsOperations() {
		return scriptTagsOperations;
	}

	@Override
	public ThemesOperations themesOperations() {
		return themesOperations;
	}

}
