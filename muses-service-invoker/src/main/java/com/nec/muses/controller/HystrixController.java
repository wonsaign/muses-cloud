package com.nec.muses.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nec.muses.bean.Book;
import com.nec.muses.service.BookService;
import com.nec.muses.service.PersonClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;

@RestController(value="hystrix")
@Configuration
public class HystrixController {
	
	@Autowired
	private PersonClient personClient;
	@Autowired
	private BookService bookService;
	
	private int catcheKey;
	
	@RequestMapping(value="/invokeHello",method=RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "getFallback",
			ignoreExceptions = {IOException.class},
			commandProperties = {
					@HystrixProperty(name="execution.isolation.thread.timeoutInmilliseconds",value="500")
					})
	public String invokeHello() {
		return personClient.sayHello();
	}
	
	@RequestMapping(value="/book/{bookId}",method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "getFallback",ignoreExceptions = {IOException.class})
	// 高并发,批处理,合并请求
	// 适用方式:请求延迟高;请求量大
//	@RequestMapping(value="/book/{bookIds}",method = RequestMethod.GET)  bookIds = {1,2,3,4}
	@HystrixCollapser(batchMethod="findAll",
			collapserProperties= {@HystrixProperty(name = "timerDelayInmilliseconds",value="100")})
	@CacheResult(cacheKeyMethod = "catcheMethod")// 高并发情况下开启缓存,缓存结果
	public String getById(
			@CacheKey("bookId") // 优先级要低于 cacheKeyMethod
			@PathVariable("bookId")Integer bookId) {
		Book book = bookService.getBook(bookId);
		System.out.println("书籍作者是："+book.getAuthor());
		return "SUCCESS";
	}
	
//	@RequestMapping(value="/book/ids/{bookId}",method = RequestMethod.GET)
//	@HystrixCommand(fallbackMethod = "getFallback",ignoreExceptions = {IOException.class})
//	@HystrixCollapser(batchMethod="findAll",
//			collapserProperties= {@HystrixProperty(name = "timerDelayInmilliseconds",value="100")})
//	@CacheResult(cacheKeyMethod = "catcheMethod")// 高并发情况下开启缓存,缓存结果
//	public String getAllBookById(
//			@CacheKey("bookId") // 优先级要低于 cacheKeyMethod
//			@PathVariable("bookId")List<Integer> bookIds) {
//		Book book = null;
//		for (Integer bookId : bookIds) {
//			book = bookService.getBook(bookId);
//		}
//		System.out.println("书籍作者是："+book.getAuthor());
//		return "SUCCESS";
//	}
	
	@HystrixCommand
	public List<Book> findAll(List<Integer> ids){
		List<Book> books = new ArrayList<>();
		for (Integer bookId : ids) {
			books.add(bookService.getBook(bookId));
		}
		return books;
	}
	
	@CacheRemove(commandKey = "getById") // 缓存移除
	public String updateBook(
			@CacheKey("bookId") //允许访问参数Book内部属性bookId作为缓存key
			@PathVariable("bookId")Book book) {
	//	Book book = bookService.updateBook(bookId);
	//	System.out.println("书籍作者是："+book.getAuthor());
		return "SUCCESS";
	}
	
	public int catcheMethod(int bookId) {
		return bookId;
	}
	
	public String getFallback(Integer id,Throwable e) {
//		assert "fail".equals(e.getMessage());
		return "返回信息异常";
	}
}
