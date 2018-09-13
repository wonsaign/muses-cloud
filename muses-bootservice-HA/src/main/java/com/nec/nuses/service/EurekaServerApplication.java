package com.nec.nuses.service;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
/**
 * 所有交互全部使用REST请求
 * 定义：定义了Region和Zone（它们之间的关系是oneTomany），Zone定义了服务器地址，默认为defaultZone。
 * 发现：discoveryClient 在方法内提供定时任务（另外还有服务续约和无效的任务）
 * 注册：调用publishEvent，将注册事件传播出去，之后调用父类实现，
 * 		将元数据{appName,instanceId,instanceIP,port...}封装注册信息InstanceInfo{AppName,instanceId...}
 *      保存在CurrentHashMap<AppName,CurrentHash<instanceId,value>>的双层Map结构
 * 通信：默认使用Jersey和XStream配合Json组为service和client之间的通信     
 */
@EnableEurekaServer
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
//		@SuppressWarnings("resource")
//		Scanner scan = new Scanner(System.in); 
//		String profiles = scan.nextLine(); 
//		new SpringApplicationBuilder(EurekaServerApplication.class).profiles(profiles).run(args);  
	}
}
