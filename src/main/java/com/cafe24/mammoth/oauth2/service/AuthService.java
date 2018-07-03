package com.cafe24.mammoth.oauth2.service;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
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
public class AuthService{
	
	@Autowired
	AuthRepository authRepository;
	
	public Auth getAuth() {
		return authRepository.findByMallId("qyuee");
	}
	
	public void saveAuth(OAuth2AccessToken accessToken) {
		Auth auth = new Auth();
		try {
			auth = new AccessTokenConverter().cafe24TokenConverter(accessToken, auth);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		authRepository.save(auth);
	}

	public void removeAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
		
	}
}
