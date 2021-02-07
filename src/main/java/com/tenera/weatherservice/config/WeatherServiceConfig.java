package com.tenera.weatherservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for weather service application
 * 
 * @author Satya Kolipaka
 *
 */
@Configuration
public class WeatherServiceConfig {
	
	@Bean
	public RestTemplate prepareRestTemplateForWeatherService() {
		return new RestTemplate();
	}
}
