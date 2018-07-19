package com.cafe24.mammoth.oauth2.api.impl;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cafe24.mammoth.oauth2.api.support.Cafe24APIHeaderBearerOAuth2RequestInterceptor;

@Component
public class Cafe24Template {
	
	private static final Logger logger = LoggerFactory.getLogger(Cafe24Template.class);
	
	private String mallId;
	private String accessToken;
	private RestTemplate usingApiRestTemplate;
	
	public Cafe24Template() {
		logger.info("Cafe24Template is registed as Bean of Root Context.");
	}
	
	// For test.
//	public Cafe24Template(String accessToken) {
//		this.accessToken = accessToken;
//	}
	
	/**
	 * 최초에 AccessToken 발급이 성공하고, SuccessHandler를 통해서 이 메소드가 호출 된다.<br>
	 * accessToken과 mallId를 셋팅한다.<br>
	 * @param {@link OAuth2AccessToken} accessToken
	 * @since 2018-07-09
	 */
	public void init(OAuth2AccessToken accessToken) {
		logger.info("Cafe24Template's init() method is called.");
		this.accessToken = accessToken.getValue();
		this.mallId = (String) accessToken.getAdditionalInformation().get("mall_id");
	}
	
	/**
	 * 원하는 Api의 객체를 얻기 위한 메소드<br>
	 * 파라미터로 입력한 class 타입으로 Api객체를 생성하여 반환.<br>
	 * @param operation
	 * @return T
	 * @since 2018-07-09
	 */
	public <T> T getOperation(Class<T> operation) {
		initallize();
		String apiURL = "https://" + mallId + ".cafe24.com";
		Constructor<T> constructor;
		T returnObject = null;
		try {
			constructor = operation.getConstructor(RestTemplate.class, String.class);
			returnObject = constructor.newInstance(usingApiRestTemplate, apiURL);
		} catch (Exception e) {
			logger.info("[Cafe24Template Exception] "+e.getMessage());
		}
		return returnObject;
	}

	/**
	 * 공통으로 사용될 restTemplate 생성.
	 * @since 2018-07-09
	 */
	private void initallize() {
		//logger.info("Cafe24Template's initallize() method is called.");
		usingApiRestTemplate = new RestTemplate(new SimpleClientHttpRequestFactory());
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
		interceptors.add(new Cafe24APIHeaderBearerOAuth2RequestInterceptor(accessToken));
		usingApiRestTemplate.setInterceptors(interceptors);
	}

	public String getMallId() {
		return mallId;
	}

	public void setMallId(String mallId) {
		this.mallId = mallId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
