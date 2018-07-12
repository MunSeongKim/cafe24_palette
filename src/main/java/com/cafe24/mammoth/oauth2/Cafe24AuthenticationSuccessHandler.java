package com.cafe24.mammoth.oauth2;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.cafe24.mammoth.app.service.AuthService;
import com.cafe24.mammoth.app.service.MemberService;
import com.cafe24.mammoth.oauth2.api.impl.Cafe24Template;


/**
 * 
 * {@link AuthenticationSuccessHandler} 를 구현한  핸들러.<br>
 * 
 * Cafe24Filter 동작 성공 후 동작하는 Handler<br>
 * authService.saveAccessToken(resource, authentication, accessToken); 을 통해 DB에 영속화한다.<br>
 * <hr>
 * 수정내용<br>
 * - 클래스명 변경 : Cafe24FilterSuccessHandler -> Cafe24AuthenticationSuccessHandler 18-07-02, MoonStar<br>
 * - 생성자 변경: AuthService 매개변수 삭제하고 Authwired로 의존성 주입 18-07-09, MoonStar<br>
 * - Entity 영속화: MemberService 추가, Auth, Member 같이 저장 18-07-10, MoonStar<br>
 * - Redirect URL 변경: /mammoth로 Redirect 경로 변경 18-07-10, MoonStar<br>
 * <br>
 * 
 * @since 2018-07-02
 * @author qyuee, allery
 * 
 */

public class Cafe24AuthenticationSuccessHandler implements AuthenticationSuccessHandler{
	
	@Autowired
	private Cafe24Template cafe24Template;
	@Autowired
	private AuthService authService;
	@Autowired
	private MemberService memberService;
	
	private OAuth2ClientContext context;
	
	public Cafe24AuthenticationSuccessHandler(OAuth2ClientContext context) {
		this.context=context;
	}
	
	/**
	 * 동작 성공 후 수행되는 code
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		// OAuth2ClientContext storeinfo = (OAuth2ClientContext) authentication;
		
		// OAuth2ClientContext에서 AccessToken 가져오기
		OAuth2AccessToken accessToken = context.getAccessToken();
		Authentication userAuthentication = ((OAuth2Authentication) authentication).getUserAuthentication();
		Map<String, Object> details = (Map<String, Object>) userAuthentication.getDetails();
		Map<String, Object> storeDetails = (Map<String, Object>) details.get("store");
		String mallUrl = (String) storeDetails.get("base_domain");
		String mallId = (String) accessToken.getAdditionalInformation().get("mall_id");

		// cafe24Template 초기화.
		cafe24Template.init(accessToken);
		
		// Auth, Member 영속화.
		authService.save(accessToken);
		memberService.save(mallUrl, mallId);
		
		request.getSession().setAttribute("mallId", mallId);
		request.getSession().setAttribute("mallUrl", mallUrl);
		
		// accessToken 발금 후 영속화 후 /로 리다이렉션
		response.sendRedirect("/mammoth");
	}
}
