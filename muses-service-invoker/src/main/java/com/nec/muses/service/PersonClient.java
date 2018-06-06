package com.nec.muses.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Feign
 * 定义在接口上,是声明式REST客户端,并且具有重试机制
 * 对于声明了@FeignClient注解的接口，在容器启动后，会被转换为beanDefinition->转变为Bean放入IOC容器中->
 * JDK实现接口（未实现的）方法代理->SynchronizedMethodHandle拦截->requestTemplate->executeAndDecode->
 * request
 *
 *	
 */
//@FeignClient(value="provider",fallback=PersonClient.class)//指定降级实现类
@FeignClient(value="provider")//指定降级实现类
public interface PersonClient {
	
	//@RequestLine("GET /hello")
	@RequestMapping(value="/hello",method=RequestMethod.GET)
	String sayHello();
}
