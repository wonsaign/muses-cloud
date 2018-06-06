package com.nec.muses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

import com.nec.muses.service.SendService;

@SpringBootApplication
@EnableEurekaClient
@EnableBinding(SendService.class)
public class EurekaServiceProvideApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServiceProvideApplication.class, args);
	}
}
