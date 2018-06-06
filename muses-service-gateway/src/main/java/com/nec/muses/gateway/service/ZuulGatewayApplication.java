package com.nec.muses.gateway.service;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ZuulGatewayApplication {
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(ZuulGatewayApplication.class).properties("server.port=8080").run(args);
	}
}
