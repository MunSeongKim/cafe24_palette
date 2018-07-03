package com.cafe24.mammoth.oauth2.service;

import java.text.ParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import com.cafe24.mammoth.oauth2.domain.Auth;
import com.cafe24.mammoth.oauth2.oauth2.AccessTokenConverter;
import com.cafe24.mammoth.oauth2.repository.AuthRepository;

/**
 * Token 발급 성공 후 Token 정보를 DB에 저장하기 위한 서비스<br>
 * Class 작성자: qyuee<br>
 * 
 * @since <i>2018. 07. 02</i>
 * @author <i>MS Kim</i>
 *
 */
@Service
public class AuthService implements ClientTokenServices{
	
	@Autowired
	AuthRepository authRepository;
	
	@Override
	public OAuth2AccessToken getAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
		// OAuth2 토큰을 통해서 사용자의 정보를 인증 객체로 만들어 놓음.
		@SuppressWarnings("unused")
		OAuth2Authentication oauth2Authentication = (OAuth2Authentication) authentication;
		
		Optional<Auth> test = authRepository.findById("qyuee");
		test.ifPresent(auth -> System.out.println("DB에 있는 값:"+auth.getExpiresAt()));
		
		return null;
	}

	@Override
	public void saveAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication, OAuth2AccessToken accessToken) {
		Auth auth = new Auth();
		try {
			auth = new AccessTokenConverter().cafe24TokenConverter(accessToken, auth);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		authRepository.save(auth);
	}

	@Override
	public void removeAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
		// TODO Auto-generated method stub
	}
}
