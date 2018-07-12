package com.cafe24.mammoth.oauth2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.cafe24.mammoth.app.domain.Auth;

/**
 * Spring security Oauth2를 통해서 생성된 {@link OAuth2AccessToken}의 필드 값과 Cafe24가 돌려준<br>
 * Access Token의 필드 값이 일치하지 않음. 그렇기에 맞지 않는 Cafe24의 Access Token 응답을<br>
 * {@link OAuth2AccessToken}에 맞도록 변환해야 함.<br>
 * <br>
 * 변환하여 Auth를 셋팅한다.<br>
 * <br>
 * [Cafe24]<br>
 * {<br>
 * 	Access_Code=blabla,<br>
 * 	expires_at=2018-07-02T12:29:43.345,<br>
 * 	client_id=PLJ3LsIxIdkkcvwiEjejoH,<br>
 * 	mall_id=qyuee,<br>
 * 	user_id=qyuee,<br>
 * 	scopes=[<br>
 * 		mall.read_category,<br>
 * 		mall.write_category,<br>
 * 		mall.read_store,<br>
 * 		mall.write_store<br>
 * 	],<br>
 * 	refresh_token=blahblah,<br>
 * 	issued_at=2018-07-02T10:29:43.348<br>
 * }<br>
 * <br>
 * [일치하는 필드] Access_Code, refresh_Code
 * <br>
 * [일치하지 않는 필드] <br>
 * expires_in -> expires_at <br>
 * scope -> scopes
 * <br>
 * <b>Update:</b><br>
 * oauth2.domain.Auth -> app.domain.Auth로 도메인 객체 변경
 * 기존 필드 issuedAt, expireAt 에서 issuedDate, expireDate로 필드명 다수 변경
 * 
 * @since 2018-07-02, <i>2018-07-09</i>
 * @author qyuee, <i>MoonStar</i>
 *
 */
public class AccessTokenConverter {

	@SuppressWarnings("unchecked")
	public Auth cafe24TokenConverter(OAuth2AccessToken oAuth2AccessToken, Auth auth) throws ParseException {

		// access_code 값 등록.
		auth.setAccessToken(oAuth2AccessToken.getValue());

		// refresh_code 값 등록.
		auth.setRefreshToken(oAuth2AccessToken.getRefreshToken().getValue());

		// OAuth2AccessToken에 맞지 않는 필드는 모두 getAdditionalInformation()에 있음.
		Map<String, Object> valueMap = oAuth2AccessToken.getAdditionalInformation();

		// oAuth2AccessToken에 변환하여 저장.
		// setter가 없어서 일단 생략. 급하지 않으니 생략.

		// Auth에 변환하여 저장.
		if (valueMap.containsKey("expires_at")) {
			String expiresAt = (String) valueMap.get("expires_at");
			auth.setExpireDate(changeStringToDate(expiresAt));
		}

		if (valueMap.containsKey("issued_at")) {
			String issuedAt = (String) valueMap.get("issued_at");
			auth.setIssuedDate(changeStringToDate(issuedAt));
		}

		if (valueMap.containsKey("scopes")) {
			auth.setScopes(String.join(",", (ArrayList<String>) valueMap.get("scopes")));
		}

		if (valueMap.containsKey("mall_id")) {
			auth.setMallId((String) valueMap.get("mall_id"));
		}

		return auth;
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
	private Date changeStringToDate(String timeOfString) throws ParseException {
		
		//timeOfString = timeOfString.replace('T', ' ');
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		
		System.out.println("format.toString : "+format.toString());
		
		return format.parse(timeOfString);
	}
}
