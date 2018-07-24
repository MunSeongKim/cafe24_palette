package com.cafe24.mammoth.oauth2.support;

import java.util.Map;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.cafe24.mammoth.oauth2.Cafe24AuthorizationCodeResourceDetails;

/**
 * mall_id 기반 AccessToken 발급 URL, Store API 사용 URL 생성하는 클래스
 * 
 * @since 18-07-20
 * @author MoonStar
 *
 */
public class Cafe24APIGenerater {
	// URL을 생성하기 위한 URL prefix, suffix 리터럴
	private static final String PREFIX_URL = "https://";
	private static final String CLIENT_CODE_URL = "cafe24api.com/api/v2/oauth/authorize";
	private static final String ACCESS_TOKEN_URL = "cafe24api.com/api/v2/oauth/token";
	private static final String STORE_API_URL = "cafe24api.com/api/v2/admin/store";
	private static final String STORE_API_FIELDS_PARAMETER = "fields=shop_no,shop_name,mall_id,base_domain,primary_domain,mall_url";
	
	/**
	 * Client Code 발급 URL, AccessToken 발급 URL을 만들어 properties에 저장
	 * @param details - {@link Cafe24AuthrizationCodeResourceDetils}
	 * @param mallId - 사용자 mall_id
	 */
	public static void generateForAccessTokenUrl(AuthorizationCodeResourceDetails details, String mallId) {
		String clientCodeUrlByUser = PREFIX_URL + mallId + "." + CLIENT_CODE_URL;
		String accessTokenUrlByUser = PREFIX_URL + mallId + "." + ACCESS_TOKEN_URL;
		details.setUserAuthorizationUri(clientCodeUrlByUser);
		details.setAccessTokenUri(accessTokenUrlByUser);
	}
	
	/**
	 * Authentication 인증 객체를 만들기 위해 Store API를 사용할 URL 생성하여 properties에 저장
	 * @param resource - {@link ResourceServerProperties}
	 * @param mallId - 사용자 mall_Id
	 */
	public static void generateForResourceUrl(ResourceServerProperties resource, String mallId) {
		String storeAPIByUser = PREFIX_URL + mallId + "." + STORE_API_URL + "?" + STORE_API_FIELDS_PARAMETER;
		resource.setUserInfoUri(storeAPIByUser);	
	}
	
	@SuppressWarnings("unchecked")
	public static String getMallIdByAuthentication(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
		if (authentication == null) {
			return ((Cafe24AuthorizationCodeResourceDetails) resource).getMallId();
		} else {
			Authentication userAuthentication = ((OAuth2Authentication) authentication).getUserAuthentication();
			Map<String, Object> storeDetails = (Map<String, Object>) ((Map<String, Object>) userAuthentication
					.getDetails()).get("store");
			return (String) storeDetails.get("mall_id");
		}
	}
	
	public static void setMallIdInResourceDetails(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
		if(authentication != null) {
			String mallId = Cafe24APIGenerater.getMallIdByAuthentication(resource, authentication);
			Cafe24APIGenerater.generateForAccessTokenUrl((AuthorizationCodeResourceDetails) resource, mallId);
		}
	}
	
}
