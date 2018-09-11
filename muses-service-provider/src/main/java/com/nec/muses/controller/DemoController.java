package com.nec.muses.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nec.muses.bean.Book;
import com.nec.muses.bean.Person;
import com.nec.muses.service.SendService;

/**
 * @RestController 服务提供端，使用满足RESTful规范的面向服务的Rest模板请求
 *
 */
@RestController
@Configuration
public class DemoController {

	@Autowired
	private SendService sendService;
	
	@RequestMapping(value = "/send" , 
			method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public String sendRequest() {
		sendService.sendOrder().send(MessageBuilder.withPayload("hello mq".getBytes()).build());
		return "MQ SUCCESS";
	}
	
	
	@RequestMapping(value = "/person/{personId}" , 
			method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public Person findPerson(@PathVariable("personId")Integer personId) {
		return new Person(personId, "Inject1", 18);
	}

	@GetMapping("/hello")
//	@RequestMapping(value="/hello",method = RequestMethod.GET)
	public String hello() {
		return "hello , Feign hello.";
	}
	
	@RequestMapping(value = "/book/{bookId}" , 
			method = RequestMethod.GET , produces = MediaType.APPLICATION_JSON_VALUE)
	public Book findBook(@PathVariable("bookId")Integer bookId) {
		return new Book(bookId, "Zuul", "test");
	}
	
}
