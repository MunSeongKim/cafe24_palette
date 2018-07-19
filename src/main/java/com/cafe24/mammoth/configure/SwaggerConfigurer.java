package com.cafe24.mammoth.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * REST API 문서화 도구인 Swagger 설정<br>
 * 추후 apiinfo() 설정하여 문서에 추가정보 삽입 필요<br>
 * 
 * @since 18-07-16
 * @author MoonStar
 *
 */
@EnableSwagger2
@Configuration
public class SwaggerConfigurer {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.cafe24.mammoth.app.controller.api"))
				.paths(PathSelectors.any()).build();
	}

}
