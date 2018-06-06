package com.nec.muses.service;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ReceiveService {

	@Input("MyInput")
	SubscribableChannel myInput();
}
