package com.cafe24.mammoth.oauth2.support;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;

import com.cafe24.mammoth.oauth2.Cafe24OAuth2AccessToken;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@SuppressWarnings("serial")
public class Cafe24OAuth2AccessTokenJackson2Deserializer extends StdDeserializer<OAuth2AccessToken> {

	public Cafe24OAuth2AccessTokenJackson2Deserializer() {
		super(OAuth2AccessToken.class);
	}

	@Override
	public OAuth2AccessToken deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		String tokenValue = null;
		String tokenType = null;
		String refreshToken = null;
		Date expiresAt = null;
		Date issuedAt = null;
		String mallId = null;
		String clientId = null;
		Set<String> scope = null;
		Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();
		
		while (p.nextToken() != JsonToken.END_OBJECT) {
			String name = p.getCurrentName();
			p.nextToken();
			if (OAuth2AccessToken.ACCESS_TOKEN.equals(name)) {
				tokenValue = p.getText();
			} else if (OAuth2AccessToken.TOKEN_TYPE.equals(name)) {
				tokenType = p.getText();
			} else if (OAuth2AccessToken.REFRESH_TOKEN.equals(name)) {
				refreshToken = p.getText();
			}
			/*
			 * else if (OAuth2AccessToken.EXPIRES_IN.equals(name)) { try { expiresIn =
			 * p.getLongValue(); } catch (JsonParseException e) { expiresIn =
			 * Long.valueOf(p.getText()); } }
			 */
			else if (Cafe24OAuth2AccessToken.SCOPES.equals(name)) {
				scope = parseScope(p);
			} else if (Cafe24OAuth2AccessToken.EXPIRES_AT.equals(name)) {
				expiresAt = convertStringToDate(p.getText());
			} else if (Cafe24OAuth2AccessToken.ISSUED_AT.equals(name)) {
				issuedAt = convertStringToDate(p.getText());
			} else if (Cafe24OAuth2AccessToken.MALL_ID.equals(name)) {
				mallId = p.getText();
			} else if (Cafe24OAuth2AccessToken.CLIENT_ID.equals(name)) {
				clientId = p.getText();
			} else {
				additionalInformation.put(name, p.readValueAs(Object.class));
			}
		}
		
		Cafe24OAuth2AccessToken accessToken = new Cafe24OAuth2AccessToken(tokenValue);
		
		accessToken.setExpiresAt(expiresAt);
		accessToken.setIssuedAt(issuedAt);
		accessToken.setScope(scope);
		accessToken.setMallId(mallId);
		accessToken.setClientId(clientId);
		accessToken.setAdditionalInformation(additionalInformation);
		
		if( tokenType != null ) {
			accessToken.setTokenType(tokenType);
		}
		if( refreshToken != null) {
			accessToken.setRefreshToken(new DefaultOAuth2RefreshToken(refreshToken));
		}

		return accessToken;
	}

	private Set<String> parseScope(JsonParser jp) throws JsonParseException, IOException {
		Set<String> scope;
		if (jp.getCurrentToken() == JsonToken.START_ARRAY) {
			scope = new TreeSet<String>();
			while (jp.nextToken() != JsonToken.END_ARRAY) {
				scope.add(jp.getValueAsString());
			}
		} else {
			String text = jp.getText();
			scope = OAuth2Utils.parseParameterList(text);
		}
		return scope;
	}

	/**
	 * Cafe24로 부터 받은 "2018-07-02T10:29:43.348"과 같은 형태를 MYSQL에 저장하기 위해<br>
	 * DATE타입으로 변환한다.<br>
	 * T는 ISO8601 형식을 사용한다는 것을 의미. datetime 값의 시간 시작 부분을 명시함.<br>
	 * 
	 * @param timeOfString
	 * @return Date
	 * @throws ParseException
	 * @author qyuee
	 * @since 2018-07-02
	 */
	private static Date convertStringToDate(String timeOfString) {

		// timeOfString = timeOfString.replace('T', ' ');
		Date result = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
			result = format.parse(timeOfString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return result;
	}
}
