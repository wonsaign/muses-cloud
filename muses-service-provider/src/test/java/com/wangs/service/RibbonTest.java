package com.wangs.service;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nec.muses.EurekaServiceProvideApplication;
import com.nec.muses.controller.DemoController;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringJUnitConfig(value=EurekaServiceProvideApplication.class)
@WebAppConfiguration
public class RibbonTest {

	public static void main(String[] args) {
		ILoadBalancer lb= new BaseLoadBalancer (); 
		
		List<Server> servers= new ArrayList<Server>(); 
		servers.add(new Server ("localhost", 9080)); 
		servers.add(new Server ("localhost", 9081 )) ; 
		lb.addServers(servers); 
		
		for (int i = 0; i < 6 ;i++) {
			Server s = lb. chooseServer (null); 
			System.out .println(s) ; 
		}
	}
}
