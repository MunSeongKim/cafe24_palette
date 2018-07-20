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

/**
 * {@link MappingJackson2HttpMessageConverter}를 참조해서 구현한 Cafe24용 MessageConverter<br>
 * MessageConverter 생성 시 미디어 타입을 JSON으로 지정해야 AccessToken 발급 Response body를 MessageConverter가 해석할 수 있음<br>
 * read() 메소드에 전달되는 타입이 OAuth2 프레임워크에서 {@link interface OAuth2AccessToken}으로 전달<br>
 * 때문에 해당 타입을 Cafe24OAuth2AccessToken 클래스로 변환 후 super.read()를 실행해야 Cafe24OAuth2AccessTokenJackson2Deserializer가 동작 가능<br>
 * 
 * @since 18-07-19
 * @author MoonStar
 *
 */
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
		Type convertedType = type.equals(OAuth2AccessToken.class) ? Cafe24OAuth2AccessToken.class : OAuth2AccessToken.class;
		return super.read(convertedType, contextClass, inputMessage);
	}
	

}
