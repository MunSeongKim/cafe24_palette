package com.cafe24.mammoth.oauth2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * OAuth2 토근 발급 및 사용자 인증 전 파라마티 처리를 위한 클래스<br>
 * OAuth2ClientAuthenticationProcessingFilter를 상속받아 구현<br>
 * <br>
 * mall_id를 파라미터에서 추출 후 Client Code 발급, Access Token 발급, User 정보 요청 URL 생성 후
 * Filter 실행<br>
 * <br>
 * 
 * @since <i>2018. 07. 02.</i>
 * @author <i>MS Kim</i>
 *
 */
public class Cafe24OAuth2ClientAuthenticationProcessingFilter extends OAuth2ClientAuthenticationProcessingFilter {
	// Access Token 발급을 위한 속성 정보, User 정보 요청을 위한 리소스 속성 정보를 저장하는 객체
	private AuthorizationCodeResourceDetails details;
	private ResourceServerProperties resource;

	// URL을 생성하기 위한 URL prefix, suffix 리터럴
	private static final String PREFIX_URL = "https://";
	private static final String CLIENT_CODE_URL = "cafe24api.com/api/v2/oauth/authorize";
	private static final String ACCESS_TOKEN_URL = "cafe24api.com/api/v2/oauth/token";
	private static final String STORE_API_URL = "cafe24api.com/api/v2/admin/store";

	/**
	 * Filter 실행 후 details와 resource를 Bean으로 유지하기 위한 생성자<br>
	 * <br>
	 * 
	 * @param {@code defaultFilterProcessesUrl} 필터가 실행 될 URL
	 * @param {@code details} AccessToken 발급을 위한 정보를 가진 객체
	 * @param {@code resource} User 인증을 위한 정보를 가진 객체
	 */
	public Cafe24OAuth2ClientAuthenticationProcessingFilter(String defaultFilterProcessesUrl,
			AuthorizationCodeResourceDetails details, ResourceServerProperties resource) {
		super(defaultFilterProcessesUrl);
		this.details = details;
		this.resource = resource;
	}

	/**
	 * Filter 실행 시 실제 동작하는 메소드<br>
	 * 요청 파라미터에서 mall_id를 추출 후 URL 생성<br>
	 * 생성한 URL 기반으로 Access Token 발급 및 User 인증 실행<br>
	 * <br>
	 * 접속한 사용자의 mall_id와 토큰을 발급했던 mall_id가 다를 경우 세션 초기화를 통해 토큰 재발급<br>
	 * <br>
	 * @since <i>2018. 07. 02.</i>
	 * @author <i>MS Kim</i>
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		// mall_id 추출 후 URL 생성
		String mallId = request.getParameter("mall_id");
		if (mallId != null) {
			String clientCodeUrlByUser = PREFIX_URL + mallId + "." + CLIENT_CODE_URL;
			String accessTokenUrlByUser = PREFIX_URL + mallId + "." + ACCESS_TOKEN_URL;
			String storeAPIByUser = PREFIX_URL + mallId + "." + STORE_API_URL;

			details.setUserAuthorizationUri(clientCodeUrlByUser);
			details.setAccessTokenUri(accessTokenUrlByUser);
			resource.setUserInfoUri(storeAPIByUser);
			setTokenServices(new UserInfoTokenServices(resource.getUserInfoUri(), details.getClientId()));
		}

		// 접속한 사용자의 mall_id와 토큰을 발급한 mall_id가 다르면 세션 초기화 후 토큰 재발급
		HttpSession session = request.getSession();
		OAuth2ClientContext clientContext = (OAuth2ClientContext) session.getAttribute("scopedTarget.oauth2ClientContext");
		if (clientContext != null) {
			OAuth2AccessToken token = clientContext.getAccessToken();
			if (token != null) {
				String tokenMallId = (String) token.getAdditionalInformation().get("mall_id");
				if(mallId != tokenMallId) {
					session.invalidate();
				}
			}
		}
		
		return super.attemptAuthentication(request, response);
	}

}
