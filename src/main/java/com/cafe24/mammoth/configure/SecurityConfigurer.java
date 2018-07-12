package com.cafe24.mammoth.configure;

import javax.servlet.Filter;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.cafe24.mammoth.oauth2.Cafe24AuthenticationSuccessHandler;
import com.cafe24.mammoth.oauth2.Cafe24OAuth2ClientAuthenticationProcessingFilter;

/**
 * Spring Security 설정 테스트<br>
 * <br>
 * 1차 설정 완료.<br>
 * <br>
 * <b>Update:</b><br>
 * - AuthService 의존성 주입 부분을 SuccessHandler로 이전, SuccessHandler 생성자 변경 18-07-09, MoonStar<br> 
 * - CORS 적용: 자바스크립트 요청에 대해 Cross domain 문제 발생 해결 적용 18-07-11, MoonStar</br>
 * @since 2018. 06. 26
 * @author MS Kim
 *
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	/**
	 * YAML에 설정한 datasource 주입<br>
	 */
	@Autowired
	DataSource dataSource;

	/**
	 * OAuth2ClientContext 주입, 시큐리티 설정에 추가할 커스텀 인증 필터를 구성할 때 사용,
	 * OAuth2AccessToken을<br>
	 * 관리하는 메소드를 지원하는 인터페이스 객체<br>
	 */
	@Autowired
	OAuth2ClientContext oauth2ClientContext;

	/**
	 * jdbcAuthentication - jdbc 지원 사용자 저장소를 이용한 사용자 인증 설정<br>
	 * <br>
	 * 
	 * @param AuthenticationManagerBuilder
	 *            - 사용자 저장소 등 사용자에 대한 세부 서비스 설정을 지원하는 객체
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource);
	}

	/**
	 * 현재 상황: AccessToken 받는거 성공<br>
	 * 설정: CSRF 설정, X-Frame-Option 해제, OAuth2ContextFilter 등록<br>
	 * <br>
	 * 
	 * @param HttpSecurity
	 *            - 인터셉터로 요청을 안전하게 보호하는 방법 설정
	 * @since 18.06.28
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// iframe에서 요청이 발생할 때 X-Frame-Option에 대한 문제 발생 해당 값을 해제하여 문제 해결. 원인: 아직 모르겠음
		http.headers().frameOptions().disable();
		// BasicAuthenticationFilter 이전에 cafe24Filter를 수행하도록 지정
		// /oauth2 경로는 필터를 통해 인증받도록 설정, 그 외 경로는 접속 허용
		http.authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest)
		.permitAll().antMatchers("/oauth2").permitAll()
		.and().addFilterBefore(cafe24Filter(), BasicAuthenticationFilter.class);
	}

	/**
	 * 자원 경로에 대한 인증 해제 필요.<br>
	 * 모든 경로에 대한 보호, 인증 해제 설정<br>
	 * <br>
	 * 
	 * @param WebSecurity
	 *            - 시큐리티의 필터 연결을 설정
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/assets/**");
	}

	/**
	 * OAuth2 Access Token 발급 및 인증 절차를 수행 할 필터 생성<br>
	 * {@code OAuth2ClientAuthenticationProcessingFilter}: FilterChain에 의해 동작 할 필터,
	 * OAuth2 Client 역할 및 사용자 인증 수행<br>
	 * {@code OAuth2RestTemplate}: API 리소스 서버와 통신을 수행하는 역할<br>
	 * <br>
	 * 
	 * @return {@link OAuth2ClientAuthenticationProcessingFilter}
	 */
	private Filter cafe24Filter() {
		OAuth2ClientAuthenticationProcessingFilter cafe24Filter = new Cafe24OAuth2ClientAuthenticationProcessingFilter(
				"/oauth2", cafe24(), cafe24Resource());
		OAuth2RestTemplate cafe24Template = new OAuth2RestTemplate(cafe24(), oauth2ClientContext);
		cafe24Filter.setRestTemplate(cafe24Template);
		// 성공 후 동작 할 handler 등록.
		cafe24Filter.setAuthenticationSuccessHandler(Cafe24FilterSuccessHandler());

		return cafe24Filter;
	}

	/**
	 * OAuth2 인증 성공 후 실행 될 핸들러 생성, 사용자 정보 저장 수행<br>
	 * <br>
	 * 수정<br>
	 * - 매개변수 타입: Cafe24ResourceDetails -> ResourceServerProperties<br>
	 * - AuthService 인수 삭제
	 * <br>
	 * 
	 * @return {@link AuthenticationSuccessHandler}
	 */
	@Bean
	public AuthenticationSuccessHandler Cafe24FilterSuccessHandler() {
		return new Cafe24AuthenticationSuccessHandler(oauth2ClientContext);
	}

	/**
	 * AccessToken 발급에 필요한 정보를 읽어들여서 객체로 생성<br>
	 * cafe24.client 하위의 속성을 읽어서 AuthorizationCodeResourceDetails 객체 생성<br>
	 * <br>
	 * 
	 * @return {@link AuthrizationCodeResourceDetails}
	 */
	@Bean
	@ConfigurationProperties("cafe24.client")
	AuthorizationCodeResourceDetails cafe24() {
		return new AuthorizationCodeResourceDetails();
	}

	/**
	 * 사용자 정보 인증에 필요한 정보를 읽어들여서 객체로 생성<br>
	 * cafe24.resource 하위의 속성을 읽어들여 ResourceServerProperties 객체 생성<br>
	 * <br>
	 * 수정<br>
	 * - Return type: Cafe24ResourceDetails -> ResourceServerProperties<br>
	 * 
	 * @return {@link ResourceServerProperties}
	 */
	@Bean
	@ConfigurationProperties("cafe24.resource")
	ResourceServerProperties cafe24Resource() {
		return new ResourceServerProperties();
	}

	/**
	 * Cafe24OAuth2ClientContextFilter를 filter 등록 빈에 추가<br>
	 * Root Context Loading때 filter를 빈으로 등록<br>
	 */
	@Bean
	public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(
			OAuth2ClientContextFilter filter) {
		FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<OAuth2ClientContextFilter>();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}
	
	/**
	 * CORS 정책 설정<br>
	 * 모든 도메인들의 GET 요청에 대해 CORS 허용, 최대 캐시 보관 시간 3600초 설정<br>
	 * @return {@link CorsConfigurationSource}
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		
		configuration.addAllowedOrigin("*");
		configuration.addAllowedMethod(HttpMethod.GET);
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(false);
		configuration.setMaxAge(3600L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
