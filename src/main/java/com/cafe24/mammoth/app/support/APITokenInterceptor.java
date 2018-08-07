package com.cafe24.mammoth.app.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.cafe24.mammoth.app.domain.Member;
import com.cafe24.mammoth.app.service.MemberService;
import com.cafe24.mammoth.oauth2.Cafe24ClientTokenServices;
import com.cafe24.mammoth.oauth2.api.impl.Cafe24Template;

/**
 * Cafe24 API 사용 전 AccessToken 확인을 위한 인터셉터<br>
 * 
 * @since 18-07-16
 * @author MoonStar
 */
@Component
public class APITokenInterceptor implements HandlerInterceptor {

	@Autowired
	private MemberService memberService;
	@Autowired
	private Cafe24Template cafe24Template;
	@Autowired
	private Cafe24ClientTokenServices clientTokenServices;
	@Autowired
	private OAuth2RestTemplate oauth2RestTemplate;

	/**
	 * Cafe24 API를 사용하는 컨트롤러 요청이 들어왔을 때 동작<br>
	 * DB에 저장된 Authentication을 가져와서 accessToken을 조회하도록 구현<br>
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 요청 URL로부터 User의 MallId를 가져오기 위한 작업

		HttpSession session = request.getSession();
		String mallId = null;
		if (session != null) {
			mallId = (String) request.getSession().getAttribute("mallId");
			if (mallId == null) {
				String mallUrl = request.getHeader("origin").replaceAll("^(https?)://", "");
				if (mallUrl.contains("localhost")) {
					mallUrl = request.getParameter("mall_url");
				}
				mallUrl = mallUrl.replaceFirst("m.", "");
				Member storedMember = memberService.getOneByMallUrl(mallUrl);
				mallId = storedMember.getMallId();
				request.getSession().setAttribute("mallId", mallId);
			}
		}

		// SecurityContextHolder에 Authentication 객체를 교체
		clientTokenServices.setAuthenticationToSecurityContext(mallId);

		// RestTemplate에서 AccessToken을 받음
		// 위에서 설정한 authentication 객체와 일치한 토큰정보가 없으면 토근을 새로 발급
		// 있으면 DB에 저장된 토근을 반환
		// Token refreshing은 RestTemplate 안에서 자동으로 이루어짐.
		OAuth2AccessToken accessToken = oauth2RestTemplate.getAccessToken();
		// API를 사용하기 위해 Cafe24Template에 mallId와 accessToken을 설정
		cafe24Template.setAccessToken(accessToken.getValue());
		cafe24Template.setMallId(mallId);

		// 컨트롤러로 응답 전달
		return true;
	}
}
