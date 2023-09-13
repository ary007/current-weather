package com.currentweather.currentweather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CurrentWeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrentWeatherApplication.class, args);
	}
	@Bean
	public RestTemplate template(RestTemplateBuilder builder) {
		// Do any additional configuration here
		return builder.build();
	}
}
