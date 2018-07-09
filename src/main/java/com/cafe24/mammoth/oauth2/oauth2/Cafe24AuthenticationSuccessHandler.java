package com.cafe24.mammoth.oauth2.oauth2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.cafe24.mammoth.oauth2.api.impl.Cafe24Template;
import com.cafe24.mammoth.oauth2.service.AuthService;

/**
 * 
 * {@link AuthenticationSuccessHandler} 를 구현한  핸들러.<br>
 * 
 * Cafe24Filter 동작 성공 후 동작하는 Handler<br>
 * authService.saveAccessToken(resource, authentication, accessToken); 을 통해 DB에 영속화한다.<br>
 * <hr>
 * 수정내용<br>
 * - 클래스명 변경 : Cafe24FilterSuccessHandler -> Cafe24AuthenticationSuccessHandler<br>
 * <br>
 * @since <i>2018. 07. 02.<i>
 * @since 2018-07-02
 * @author <i>MS Kim</i>
 * @author qyuee, allery
 * 
 */

public class Cafe24AuthenticationSuccessHandler implements AuthenticationSuccessHandler{
	
	@Autowired
	private Cafe24Template cafe24Template;
	
	private OAuth2ClientContext context;
	
	private AuthService authService;
	
	public Cafe24AuthenticationSuccessHandler(OAuth2ClientContext context, AuthService authService) {
		this.context=context;
		this.authService=authService;
	}
	
	/**
	 * 동작 성공 후 수행되는 code
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		// OAuth2ClientContext storeinfo = (OAuth2ClientContext) authentication;
		
		// OAuth2ClientContext에서 AccessToken 가져오기
		OAuth2AccessToken accessToken = context.getAccessToken();
		
		// Auth 영속화.
		// Auth 저장하고, member에도 저장.
		authService.saveAuth(accessToken);
		
		// cafe24Template 초기화.
		cafe24Template.init(accessToken);
		
		// memserService 구현 필요
		// memberService.saveMember(mallId, mallUrl);
		
		String mallId = (String) accessToken.getAdditionalInformation().get("mall_id");
		// 왜 있을까...?
		request.getSession().setAttribute("mall_id", mallId);
		// accessToken 발금 후 영속화 후 /{mall_id}로 리다이렉션
		response.sendRedirect("/" + mallId);
	}
}
