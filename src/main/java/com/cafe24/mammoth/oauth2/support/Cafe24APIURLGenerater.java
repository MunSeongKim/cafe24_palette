package com.cafe24.mammoth.oauth2.support;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

public class Cafe24APIURLGenerater {
	// URL을 생성하기 위한 URL prefix, suffix 리터럴
	private static final String PREFIX_URL = "https://";
	private static final String CLIENT_CODE_URL = "cafe24api.com/api/v2/oauth/authorize";
	private static final String ACCESS_TOKEN_URL = "cafe24api.com/api/v2/oauth/token";
	private static final String STORE_API_URL = "cafe24api.com/api/v2/admin/store";
	private static final String STORE_API_FIELDS_PARAMETER = "fields=shop_no,shop_name,mall_id,base_domain,primary_domain,mall_url";
	
	public static void generateForAccessTokenUrl(AuthorizationCodeResourceDetails details, String mallId) {
		String clientCodeUrlByUser = PREFIX_URL + mallId + "." + CLIENT_CODE_URL;
		String accessTokenUrlByUser = PREFIX_URL + mallId + "." + ACCESS_TOKEN_URL;
		details.setUserAuthorizationUri(clientCodeUrlByUser);
		details.setAccessTokenUri(accessTokenUrlByUser);
	}
	
	public static void generateForResourceUrl(ResourceServerProperties resource, String mallId) {
		String storeAPIByUser = PREFIX_URL + mallId + "." + STORE_API_URL + "?" + STORE_API_FIELDS_PARAMETER;
		resource.setUserInfoUri(storeAPIByUser);	
	}
	
}
