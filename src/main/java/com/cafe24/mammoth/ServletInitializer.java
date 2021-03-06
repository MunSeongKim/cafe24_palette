package com.cafe24.mammoth;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * 외부 Tomcat에서 동작하기 위해 ServletInitializer를 상속한 main() 메소드를 갖는 클래스를 설정
 * 외부 Tomcat은 war 파일을 해제 후 앱을 실행시키면서 main()이 실행되고, 순서대로 DispatcherServlet을 위한 설정이 진행
 *  
 * @author MS Kim
 * @since 18.06.28
 */
@SpringBootConfiguration
@EnableAutoConfiguration(exclude = {
        SecurityAutoConfiguration.class})
@ComponentScan
@Configuration
@EnableCaching
public class ServletInitializer extends SpringBootServletInitializer {
	private static final String PROPERTIES = "spring.config.location=classpath:/application.yml";
	
	public static void main(String[] args) {
		// 특정 properties 파일을 지정하여 WebApp 실행을 위한 명령
		new SpringApplicationBuilder().properties(PROPERTIES).run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ServletInitializer.class);
	}
	
	/**
	 * CacheManager 빈 설정
	 * @return {@link CacheManager}
	 */
	@Bean
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheCacheManager().getObject());
	}
	
	/**
	 * EhcacheManager 빈 설정
	 * @return {@link EhCacheManagerFactoryBean}
	 */
	@Bean
	public EhCacheManagerFactoryBean ehCacheCacheManager() {
		EhCacheManagerFactoryBean factory = new EhCacheManagerFactoryBean();
		// EhCache의 설정을 클래스패스의 ehcache.xml에서 읽어옴
		factory.setConfigLocation(new ClassPathResource("ehcache.xml"));
		factory.setShared(true);
		
		return factory;
	}
	
}
