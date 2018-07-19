package com.cafe24.mammoth.configure;

import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
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
	protected void configureViewResolvers(ViewResolverRegistry registry) {
		super.configureViewResolvers(registry);
		registry.jsp(prefix, suffix);
	}

	// Servlet Context 설정
	@Override
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
		servletContext.addListener(RequestContextListener.class);
	}

	// Interceptor 등록
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(apiTokenInterceptor()).addPathPatterns("/api/test/**");
	}

	// ResourceHandler 등록
	// 정적 자원에 대한 처리 등록
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/").resourceChain(true);
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/").resourceChain(true);
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/META-INF/resources/webjars/")
				.setCacheControl(CacheControl.maxAge(3L, TimeUnit.HOURS).cachePublic()).resourceChain(true);
	}
}
