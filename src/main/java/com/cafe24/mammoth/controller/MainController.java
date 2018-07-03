package com.cafe24.mammoth.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Cafe24 App Main Controller
 * 
 * @author MS Kim
 * @since 18.06.28
 */
@Controller
public class MainController {
	// OAuth2 AccessToken, AccessTokenRequest를 관리하는 Context 객체
	@Autowired
	OAuth2ClientContext clientContext;
	
	/**
	 * AccessToken 발급 성공 시 실행, 발급 받은 토큰 값과 추가 정보 출력
	 * @param model - data: AccessToken과 함께 추가로 응답된 데이터, token: AccessToken 값
	 * @return jsp view name
	 */
	@GetMapping("/")
	public String main(Model model) {
		Map<String, Object> responseData = clientContext.getAccessToken().getAdditionalInformation();
		String token = clientContext.getAccessToken().getValue();
		model.addAttribute("data", responseData);
		model.addAttribute("token", token);
		return "main";
	}
}
