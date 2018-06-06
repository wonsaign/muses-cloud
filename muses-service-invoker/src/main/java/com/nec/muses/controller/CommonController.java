package com.nec.muses.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nec.muses.bean.Book;
import com.nec.muses.bean.Person;
import com.nec.muses.service.BookService;
import com.nec.muses.service.PersonService;

@RestController
@Configuration
public class CommonController {
	
	@Autowired
	private PersonService personService;
	@Autowired
	private BookService bookService;
	
	@RequestMapping(value="/book/{bookId}",method = RequestMethod.GET)
	public String saleBook(@PathVariable("bookId")Integer bookId) {
		Book book = bookService.getBook(bookId);
		System.out.println("书籍作者是："+book.getAuthor());
		return "SUCCESS";
	}
	
	/**
	 * RestTemplate实现了对Http请求的封装
	 * Ribbon是一个工具，是对于Http和Tcp客户端请求的负载均衡，可以将面向服务的RestTemplate请求
	 * 自动转换成客户端负载均衡的调用
	 * 原理：通过维护一个被@LoadBalacned修饰的RestTemplate的对象列表，初始化一个RestTemplateCustomizer
	 * 用来给需要客户端负载均衡RestTemplate增加LoadBalancedInterceptor拦截器。
	 * 
	 * 通过实现ILoadBalancer接口，它有三个默认的实现AbstractLoadBalanced,BaseLoadBalanced,DynamicServerListLoadBalanced
	 * 
	 */
	// 加入LoadBalanced可实现负载均衡
	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@RequestMapping(value = "/router/{personId}" , 
			method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public Person routerHy(@PathVariable("personId")Integer id) {
		return personService.getPerson(id);
	}
	
	/**
	 * eureka + restTemplate
	 * @return
	 */
	@RequestMapping(value = "/router" , 
			method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public String router() {
		RestTemplate rt = getRestTemplate();// spring 启动时已经初始化
		String json = "Im null";
		try {
			json = rt.getForObject("http://provider/person/1", String.class);
			if(json == null) {
				return "I cant find him , perhaps url is wrong";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
