package com.cafe24.mammoth.configure;

import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.cafe24.mammoth.app.support.APITokenInterceptor;

/**
 * Swagger 설정, Interceptor 설정을 위한 클래스<br>
 * web.xml에 기술하는 내용을 작성<br>
 * 
 * @since 2018-07-16
 * @author MoonStar
 *
 */
@Configuration
@ComponentScan(basePackageClasses = { WebConfigurer.class })
public class WebConfigurer extends WebMvcConfigurationSupport {

	@Value("${spring.mvc.view.prefix}")
	private String prefix;
	@Value("${spring.mvc.view.suffix}")
	private String suffix;

	// Interceptor Bean 등록
	@Bean
	public APITokenInterceptor apiTokenInterceptor() {
		return new APITokenInterceptor();
	}

	// ViewResolver 설정
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		super.configureViewResolvers(registry);
		registry.jsp(prefix, suffix);
	}

	// Interceptor 등록
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(apiTokenInterceptor()).addPathPatterns("/api/test/**");
		registry.addInterceptor(apiTokenInterceptor()).addPathPatterns("/api/cafe24/**");
	}

	// Servlet Context 설정
	@Override
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
		servletContext.addListener(RequestContextListener.class);
	}
	
	// ResourceHandler 등록
	// 정적 자원에 대한 처리 등록
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
		// API Docs 페이지 리소스
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/")
				.resourceChain(true);
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/")
				.resourceChain(true);

		// JQuery, Bootstrap 등 프론트 라이브러이 리소스
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/META-INF/resources/webjars/")
				.setCacheControl(CacheControl.maxAge(3L, TimeUnit.HOURS).cachePublic()).resourceChain(true);

		// 기능, 테마 리소스
		registry.addResourceHandler("/function/**").addResourceLocations("file:/cafe24/tmp/function/")
				.resourceChain(true);
		registry.addResourceHandler("/theme/**").addResourceLocations("file:/cafe24/tmp/theme/").resourceChain(true);

		// 각 기능용 리소스 API
		registry.addResourceHandler("/**/*.js").addResourceLocations("/").resourceChain(true);
		registry.addResourceHandler("/**/*.css").addResourceLocations("/").resourceChain(true);
		registry.addResourceHandler("/**/*.jpg").addResourceLocations("/").resourceChain(true);
		registry.addResourceHandler("/**/*.png").addResourceLocations("/").resourceChain(true);
		registry.addResourceHandler("/**/*.html").addResourceLocations("/").resourceChain(true);
	}
	
	/**
	 * LazyInitialization 문제 해결 방법 -> Open Session In View 필터 등록.
	 * 정상 작동하는지 확인해 봐야함.
	 */
	@Bean
	public FilterRegistrationBean<OpenEntityManagerInViewFilter> registerOpenEntityManagerInViewFilterBean() {
		FilterRegistrationBean<OpenEntityManagerInViewFilter> registrationBean = new FilterRegistrationBean<OpenEntityManagerInViewFilter>();
		OpenEntityManagerInViewFilter filter = new OpenEntityManagerInViewFilter();
		registrationBean.setFilter(filter);
		registrationBean.setOrder(5);
		return registrationBean;
	}


}
