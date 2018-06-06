package com.nec.muses.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nec.muses.bean.Book;

@FeignClient(value="provider")
public interface BookService {

	@RequestMapping(value="/book/{bookId}",method = RequestMethod.GET)
	Book getBook(@PathVariable("bookId")Integer bookId);
}
