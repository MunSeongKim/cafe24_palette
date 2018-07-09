package com.cafe24.mammoth.app.service;

import java.text.ParseException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import com.cafe24.mammoth.app.domain.Auth;
import com.cafe24.mammoth.app.repository.AuthRepository;
import com.cafe24.mammoth.oauth2.oauth2.AccessTokenConverter;

/**
 * Token 발급 성공 후 Token 정보를 DB에 저장하기 위한 서비스<br>
 * Class 작성자: qyuee<br>
 * 
 * @since <i>2018. 07. 02</i>
 * @author <i>MS Kim</i>
 *
 */
@Service
@Transactional
public class AuthService{
	
	@Autowired
	AuthRepository authRepository;
	
	public Auth getOne(String mallId) {
		return authRepository.findByMallId(mallId);
	}
	
	public Auth save(OAuth2AccessToken accessToken) {
		Auth auth = new Auth();
		try {
			auth = new AccessTokenConverter().cafe24TokenConverter(accessToken, auth);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return authRepository.save(auth);
	}

	public void removeAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
		
	}
}
