package com.cafe24.mammoth.oauth2.oauth2;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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
	
	private ResourceServerProperties cafe24Resource;
	
	private OAuth2ClientContext context;
	
	private AuthorizationCodeResourceDetails resource;
	
	private AuthService authService;
	
	public Cafe24AuthenticationSuccessHandler(AuthorizationCodeResourceDetails resource, OAuth2ClientContext context, AuthService authService, ResourceServerProperties resourceServerProperties) {
		this.resource=resource;
		this.context=context;
		this.authService=authService;
		this.cafe24Resource = resourceServerProperties;
	}
	
	/**
	 * 동작 성공 후 수행되는 code
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		// from UserInfoTokenServices
		// Request + AuthenticationDetails = Authentication
		// Access Token 발급하고 Store API로 그 사용자를 인증하는 형태이다.
		OAuth2Authentication result = (OAuth2Authentication) authentication;  // Oauth2 인증 객체 -> 아마도 store 관련된 듯.
		//OAuth2Request req = result.getOAuth2Request();						  // 인증 객체의 Request?
		
		//Object details = authentication.getDetails();
		//Object credential = (String)authentication.getCredentials();
		//UserDetails principal = (UserDetails) authentication.getPrincipal();
		//List<GrantedAuthority> list = (List<GrantedAuthority>) authentication.getAuthorities();
		//Set<GrantedAuthority> list = (HashSet<GrantedAuthority>) req.getAuthorities();
		
		
		// Security Context에 저장된 최상단 Authentication 객체 정보
		System.out.println("\n\n==============[Authentication]======================");
		System.out.println("authentication.getName() : "+authentication.getName());
		System.out.println(authentication.getCredentials());
		System.out.println(authentication.getPrincipal());
		System.out.println(authentication.getDetails());
		// System.out.println(result.getCredentials()); -> empty string
		
		// OAuth2 토큰을 통해서 사용자의 정보를 인증 객체로 만들어 놓음.
		System.out.println("\n\n==============[OAuth2Authentication info]============================");
		System.out.println("result.isAuthenticated() : "+result.isAuthenticated());
		System.out.println("getUserAuthentication : "+result.getUserAuthentication());
		//result.getUserAuthentication().getdetails(); --> store 정보 있음.
		
		System.out.println("result.getDetails() : "+result.getDetails());
		System.out.println("result.getPrincipal() : "+result.getPrincipal());
		System.out.println("result.getName() : "+result.getName());
		System.out.println("result.getCredentials() : "+result.getCredentials());
		System.out.println("result.getAuthorities() : "+result.getAuthorities());
		
		
		//Authentication userAuthentication = result.getUserAuthentication();
		
		OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) result.getDetails();
		System.out.println("\n\n================== [detailsinfo] =======================");
		System.out.println("details.getTokenType() : "+details.getTokenType());
		System.out.println("details.getTokenValue() : "+details.getTokenValue());
		System.out.println("details.getSessionId() : "+details.getSessionId());
		System.out.println("details.getRemoteAddress() : "+details.getRemoteAddress());
		System.out.println("details.getDecodedDetails() : "+details.getDecodedDetails());
		
		
		OAuth2AccessToken accessToken = context.getAccessToken();
		Map<String, Object> data = accessToken.getAdditionalInformation();
		System.out.println("\n\n================== OAuth2AccessToken ==================");
		System.out.println("accessToken.getValue() : "+accessToken.getValue());
		System.out.println("accessToken.isExpired() : "+accessToken.isExpired());
		System.out.println("accessToken.getExpiresIn() : "+accessToken.getExpiresIn());
		System.out.println("accessToken.getExpiration() : "+accessToken.getExpiration());
		System.out.println("accessToken.getTokenType() : "+accessToken.getTokenType());
		System.out.println("accessToken.getScope() : "+accessToken.getScope());
		System.out.println("accessToken.getAdditionalInformation() : "+accessToken.getAdditionalInformation());
		
		OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
		System.out.println("\n================== [Refresh Token] ==================");
		System.out.println("refreshToken.getValue() : "+refreshToken.getValue());
		
		Set<String> keySet = data.keySet();
		Iterator<String> i = keySet.iterator();
		while(i.hasNext()) {
			String key = i.next();
			System.out.println(key + ": " + data.get(key));
		}
		
		//System.out.println("authentication.getDetails() : "+details);
		//System.out.println("authentication.getCredentials() : "+credential);
		//System.out.println("authentication.getPrincipal() : "+principal);
		//System.out.println("principal.getUsername() : "+principal.getUsername());
		
		System.out.println("========================================================\n");
		
		// Auth 영속화.
		authService.saveAccessToken(resource, authentication, accessToken);
		
		/*authService.getAccessToken(resource, authentication);
		*/
	}
}
