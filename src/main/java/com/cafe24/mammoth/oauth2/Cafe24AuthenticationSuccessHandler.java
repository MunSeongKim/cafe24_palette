package com.cafe24.mammoth.oauth2;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.cafe24.mammoth.app.service.MemberService;
import com.cafe24.mammoth.oauth2.api.impl.Cafe24Template;

/**
 * 
 * {@link AuthenticationSuccessHandler} 를 구현한 핸들러.<br>
 * 
 * Cafe24Filter 동작 성공 후 동작하는 Handler<br>
 * authService.saveAccessToken(resource, authentication, accessToken); 을 통해 DB에
 * 영속화한다.<br>
 * <hr>
 * 수정내용<br>
 * - 클래스명 변경 : Cafe24FilterSuccessHandler -> Cafe24AuthenticationSuccessHandler
 * 18-07-02, MoonStar<br>
 * - 생성자 변경: AuthService 매개변수 삭제하고 Authwired로 의존성 주입 18-07-09, MoonStar<br>
 * - Entity 영속화: MemberService 추가, Auth, Member 같이 저장 18-07-10, MoonStar<br>
 * - Redirect URL 변경: /mammoth로 Redirect 경로 변경 18-07-10, MoonStar<br>
 * - Entity 영속화 변경: 토큰 발급전 생성한 Member Entity에 토큰 발급 후 정보 추가하여 업데이트, Auth는 삭제, 18-07-19, MoonStar<br>
 * - Authentication 영속화: 토큰발급이 완료된 인증 객체 저장, 18-07-23, MoonStar<br>
 * 
 * @since 2018-07-02
 * @author qyuee, allery
 * 
 */

public class Cafe24AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private Cafe24Template cafe24Template;
	@Autowired
	private MemberService memberService;
	@Autowired
	private Cafe24ClientTokenServices clientTokenServices;

	private OAuth2ClientContext context;

	public Cafe24AuthenticationSuccessHandler(OAuth2ClientContext context) {
		this.context = context;
	}

	/**
	 * 동작 성공 후 수행되는 code
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// OAuth2ClientContext에서 AccessToken 가져오기
		Cafe24OAuth2AccessToken accessToken = (Cafe24OAuth2AccessToken) context.getAccessToken();
		String mallId = accessToken.getMallId();

		Authentication userAuthentication = ((OAuth2Authentication) authentication).getUserAuthentication();
		Map<String, Object> details = (Map<String, Object>) userAuthentication.getDetails();
		Map<String, Object> storeDetails = (Map<String, Object>) details.get("store");
		String baseDomain = (String) storeDetails.get("base_domain");
		String primaryDomain = (String) storeDetails.get("primary_domain");
		String mallUrl = (String) storeDetails.get("mall_url");

		// 사용자 인증 객체 저장
		clientTokenServices.saveAuthentication(authentication);

		// cafe24Template 초기화
		cafe24Template.init(accessToken);

		// 사용자 정보 업데이트
		memberService.save(baseDomain, primaryDomain, mallUrl, mallId);

		request.getSession().setAttribute("mallId", mallId);
		request.getSession().setAttribute("mallUrl", mallUrl);

		// accessToken 발금 후 영속화 후 /로 리다이렉션
		response.sendRedirect("/mammoth");
	}
}
