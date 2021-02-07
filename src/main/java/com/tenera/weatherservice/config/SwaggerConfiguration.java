package com.tenera.weatherservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Configuration class for Swagger API
 * @author Satya Kolipaka
 *
 */
@Configuration
public class SwaggerConfiguration {

	 private static final String DEFAULT_REST_PACKAGE = "com.tenera.weatherservice.controller";

	    @Value("${spring.application.name}")
	    private String applicationName;

	    @Bean
	    public Docket exchangeApi() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .groupName(this.applicationName)
	                .apiInfo(apiInfo())
	                .useDefaultResponseMessages(false)
	                .select()
	                .apis(RequestHandlerSelectors.basePackage(DEFAULT_REST_PACKAGE))
	                .paths(PathSelectors.any())
	                .build();
	    }

	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                .title("Weather Service")
	                .description("REST API Spec for Weather Service app")
	                .contact(new Contact("Satya Kolipaka", "https://www.linkedin.com/in/satya-sudheer-kolipkaka-9288b89a/", "satyasudheer625@gmail.com"))
	                .version("1.0") 
	                .build();
	    }
}
