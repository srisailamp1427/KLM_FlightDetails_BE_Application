package com.afkl.cases.df;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * This is SpringBootApplication class
 * 
 */

@SpringBootApplication
@EnableCaching
public class Bootstrap {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(Bootstrap.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
