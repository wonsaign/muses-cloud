package com.nec.muses.service;

import java.io.IOException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nec.muses.bean.Person;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;

/**
 * Hystrix demo
 * 
 *
 */
@Component
public class PersonService extends com.netflix.hystrix.HystrixCommand<Person>{

	protected PersonService(HystrixCommandGroupKey group) {
		// 自定义实现HystrixCommand配置方式
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(500))
				);
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private RestTemplate restTemplate;
	
	private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("CommandKey");
	private int id ;

	/**
	 * Hystrix
	 * 断路器定义：HystrixCircuitBreaker
	 * 判断流程
	 * 使用RxJava，通过queue方法，通过toBlockingObesrvable方法将数据以阻塞的方法发出，通过toFuture把阻塞数据转为Future，
	 * toFuture只是创建Future，并不阻塞。对于返回的结果Future，根据以下开关执行相应流程
	 * 1 缓存是否开启，打开直接返回Observable对象，否则执行Hystrix命令
	 * 2.1 断路器是否开启，打开直接fallback，关闭则向下走流程
	 * 2.2 线程池/信号量是否满载--可处理减少线程的消耗，已满fallback，，不满执行Hystrix命令
	 * 3 执行Hystrix命令 HystrixCommand.run返回一个单一结果或者异常/HystixObservableCommand.construct返回一个Observable对象或者error
	 * 4 fallback处理（服务降级）：通过重载fallback方法实现，以设计静态逻辑或者从缓存获取结果，而不是依赖请求的结果。
	 * 
	 * 实现方式:可使用注解，也可以继承HystrixCommand<T>/HystrixObeservableCommand<T>
	 * 
	 */
	// 同步实现 忽略异常 降级
	@HystrixCommand(fallbackMethod = "getPersonFallback",ignoreExceptions = {IOException.class})
	@CacheResult//缓存结果
	public Person getPerson(@CacheKey("id")Integer id) {
		Person p = null;
		try {
			p = restTemplate.getForObject("http://provider/person/{personId}",
					Person.class, id);
		} catch (Exception e) {
		//	new HystrixCircuitBreaker().isOpen(); 断路器是否打开
			e.printStackTrace();
		}
		return p;
	}

	//异步实现
	@HystrixCommand(fallbackMethod = "getPersonFallback")
	public Future<Person> getPersonAsy(final Integer id){
		try {

			this.id = id;
			return new AsyncResult<Person>() {
				@Override
				public Person invoke() {
					return restTemplate.getForObject("http://provider/person/{personId}",
							Person.class, id);
				}
			};
		} catch (Exception e) {
			// 指定异常 降级
			throw new RuntimeException("fail");
		}
	}
	
	//  回退方法:必须要有跟上面调用者一样的参数，必须在同一个类中，必须有显示声明无参构造方法。
	public Person getPersonFallback(Integer id,Throwable e) {
		assert "fail".equals(e.getMessage());
		return new Person(0, "Hystrix", -1);
	}
	
	
	// 高并发缓存
	@Override
	protected String getCacheKey() {
		// TODO Auto-generated method stub
		return String.valueOf(id);
	}
	// 刷新缓存
	public static void flushCache(int id) {
		HystrixRequestCache.getInstance(GETTER_KEY, HystrixConcurrencyStrategyDefault.getInstance()).clear(String.valueOf(id));
	}
	

	@Override
	protected Person run() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
