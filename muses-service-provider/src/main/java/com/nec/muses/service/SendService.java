package com.nec.muses.service;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

public interface SendService {

	@Output("MyInput")
	SubscribableChannel sendOrder();
}
