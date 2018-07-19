package com.cafe24.mammoth.oauth2.support;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.cafe24.mammoth.oauth2.Cafe24OAuth2AccessToken;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Cafe24OAuth2AccessTokenMessageConverter extends AbstractJackson2HttpMessageConverter  {
	public Cafe24OAuth2AccessTokenMessageConverter() {
		this(Jackson2ObjectMapperBuilder.json().build());
	}

	public Cafe24OAuth2AccessTokenMessageConverter(ObjectMapper objectMapper) {
		super(objectMapper, MediaType.APPLICATION_JSON, new MediaType("application", "*+json"));
	}

	@Override
	public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		System.out.println("=============== Cafe24OAuth2AccessTokenMessageConverter ==============");
		System.out.print("type instanceof OAuth2AccessToken: ");
		System.out.println(type instanceof OAuth2AccessToken);
		System.out.println("type.equals(OAuth2AccessToken.class): " + type.equals(OAuth2AccessToken.class));
		
		
		Type convertedType = type.equals(OAuth2AccessToken.class) ? Cafe24OAuth2AccessToken.class : OAuth2AccessToken.class;
		System.out.println(type + " -> " + convertedType);
		Object result = super.read(convertedType, contextClass, inputMessage);
		
		System.out.println(result.getClass());
		System.out.println(result instanceof Cafe24OAuth2AccessToken);
		System.out.println(((Cafe24OAuth2AccessToken) result).print());
		return result;
	}
	

}
