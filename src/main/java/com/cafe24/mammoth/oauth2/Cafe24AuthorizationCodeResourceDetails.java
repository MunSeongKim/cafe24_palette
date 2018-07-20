package com.cafe24.mammoth.oauth2;

import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

/**
 * mallId 저장, 
 * @author bit
 *
 */
public class Cafe24AuthorizationCodeResourceDetails extends AuthorizationCodeResourceDetails {
	//
	public String mallId;

	public String getMallId() {
		return mallId;
	}

	public void setMallId(String mallId) {
		this.mallId = mallId;
	}

	// OAuth2 필터의 AccessTokenProviderChain이 클라이언트로 동작할 수 있도록 설정
	@Override
	public boolean isClientOnly() {
		return true;
	}

}
