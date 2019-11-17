package com.ai.st.microservice.providers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StMicroserviceProvidersApplication {

	public static void main(String[] args) {
		SpringApplication.run(StMicroserviceProvidersApplication.class, args);
	}

}
