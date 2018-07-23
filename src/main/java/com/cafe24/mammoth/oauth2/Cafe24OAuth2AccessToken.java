package com.cafe24.mammoth.oauth2;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import com.cafe24.mammoth.oauth2.support.Cafe24OAuth2AccessTokenJackson2Deserializer;

//@org.codehaus.jackson.map.annotate.JsonSerialize(using = OAuth2AccessTokenJackson1Serializer.class)
//@org.codehaus.jackson.map.annotate.JsonDeserialize(using = OAuth2AccessTokenJackson1Deserializer.class)
//@com.fasterxml.jackson.databind.annotation.JsonSerialize(using = OAuth2AccessTokenJackson2Serializer.class)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = Cafe24OAuth2AccessTokenJackson2Deserializer.class)

/**
 * Cafe24에서 발급하는 AccessToken 양식에 맞춰 정보를 담는 객체 <br>
 * OAuth2AccessToken을 참조하여 구현<br>
 * 
 * @since 18-07-19
 * @author MoonStar
 *
 */
public class Cafe24OAuth2AccessToken implements Serializable, OAuth2AccessToken {

	/**
	 * For Serialization
	 */
	private static final long serialVersionUID = 5389077752016962344L;

	/**
	 * Customize constant value for Response from Cafe24 API Server
	 */
	public static String SCOPES = "scopes";
	public static String EXPIRES_AT = "expires_at";
	public static String ISSUED_AT = "issued_at";
	public static String MALL_ID = "mall_id";
	public static String CLIENT_ID = "client_id";
	
	/**
	 * Original field in {@link DefaultOAuth2AccessToken}
	 */
	// Access token value
	private String value;
	// Access token type name
	private String tokenType = BEARER_TYPE.toLowerCase();
	// RefreshToken object
	private OAuth2RefreshToken refreshToken;
	// Application authorization scope
	private Set<String> scope;
	// Client Id = App ID
	private String clientId;
	// Not converted data
	private Map<String, Object> additionalInformation = Collections.emptyMap();
	
	/**
	 * Customize fields for Response from Cafe24 API server
	 */
	private Date expiresAt;
	private Date issuedAt;
	private String mallId;

	public Cafe24OAuth2AccessToken(String value) {
		this.value = value;
	}

	@SuppressWarnings("unused")
	private Cafe24OAuth2AccessToken() {
		this((String) null);
	}

	public Cafe24OAuth2AccessToken(OAuth2AccessToken accessToken) {
		this(accessToken.getValue());
		
		setRefreshToken(accessToken.getRefreshToken());
		setExpiresAt(accessToken.getExpiration());
		setScope(accessToken.getScope());
		setTokenType(accessToken.getTokenType());
		
		setValues(accessToken.getAdditionalInformation());
	}

	public Date getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Date expiresAt) {
		this.expiresAt = expiresAt;
	}

	public Date getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(Date issuedAt) {
		this.issuedAt = issuedAt;
	}

	public String getMallId() {
		return mallId;
	}

	public void setMallId(String mallId) {
		this.mallId = mallId;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public void setRefreshToken(OAuth2RefreshToken refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void setScope(Set<String> scope) {
		this.scope = scope;
	}

	public void setAdditionalInformation(Map<String, Object> additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return this.additionalInformation;
	}

	@Override
	public Set<String> getScope() {
		return this.scope;
	}

	@Override
	public OAuth2RefreshToken getRefreshToken() {
		return this.refreshToken;
	}

	@Override
	public String getTokenType() {
		return this.tokenType;
	}

	@Override
	public boolean isExpired() {
		return expiresAt != null && expiresAt.before(new Date());
	}

	@Override
	public Date getExpiration() {
		return this.expiresAt;
	}

	// expiresAt을 기반으로 토근 만료까지 남은 시간을 초단위로 변환
	@Override
	public int getExpiresIn() {
		return expiresAt != null ? Long.valueOf((expiresAt.getTime() - System.currentTimeMillis()) / 1000L).intValue()
				: 0;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	private void setValues(Map<String, Object> map) {
		System.out.println("=================== Cafe24OAuth2AccessToken.setValues() =====================");

		if (map.containsKey(SCOPES)) {
			Set<String> scope = new TreeSet<String>();
			for (StringTokenizer tokenizer = new StringTokenizer((String) map.get(SCOPE), " ,"); tokenizer
					.hasMoreTokens();) {
				scope.add(tokenizer.nextToken());
			}
			this.setScope(scope);
		}

		if (map.containsKey(ISSUED_AT)) {
			String issuedAtString = (String) map.get(ISSUED_AT);
			Date issuedAt = convertStringToDate(issuedAtString);
			this.setIssuedAt(issuedAt);
		}

		if (map.containsKey(MALL_ID)) {
			this.setMallId((String) map.get(MALL_ID));
		}
		
		if (map.containsKey(CLIENT_ID)) {
			this.setClientId((String) map.get(CLIENT_ID));
		}
		
		this.setAdditionalInformation(map);
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

	
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && toString().equals(obj.toString());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	public String print() {
		return "Cafe24OAuth2AccessToken [mallId=" + mallId + ", value=" + value + ", tokenType=" + tokenType
				+ ", refreshToken=" + refreshToken + ", expiresAt=" + expiresAt + ", issuedAt=" + issuedAt
				+ ", scope=" + scope + ", additionalInformation=" + additionalInformation + "]";
	}

	@Override
	public String toString() {
		return String.valueOf(getValue());
	}
}
