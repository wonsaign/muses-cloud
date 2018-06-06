package com.nec.muses.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nec.muses.service.PersonClient;

@RestController
@Configuration
public class FeignController {
	
	@Autowired
	private PersonClient personClient;
	
	@RequestMapping(value="/invokeHello",method=RequestMethod.GET)
	public String invokeHello() {
		return personClient.sayHello();
	}
}
