package com.ai.st.microservice.providers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.ai.st.microservice.common.clients"})
@ComponentScan(value = {"com.ai.st.microservice.common.business", "com.ai.st.microservice.providers"})
public class StMicroserviceProvidersApplication {

    public static void main(String[] args) {
        SpringApplication.run(StMicroserviceProvidersApplication.class, args);
    }

}
