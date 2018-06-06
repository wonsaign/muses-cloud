package com.nec.muses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.nec.muses.service.ReceiveService;
//启动注解
@SpringBootApplication
/**
 * 可以发现eureka客户端
 */
@EnableDiscoveryClient
// Feign
@EnableFeignClients
// Hystrix ，开启断路器
@EnableCircuitBreaker
// 开启Hystrix仪表盘
@EnableHystrixDashboard
// RabbitMQ
@EnableBinding(ReceiveService.class)
public class EurekaServiceInvokeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServiceInvokeApplication.class, args);
	}

//	@StreamListener("MyInput")
//	public String getHello(byte[] msg) {
//		return "收到的消息：" + new String(msg);
//	}
}
