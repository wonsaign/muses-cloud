package com.wangs.service;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringJUnitConfig(value=EurekaServiceProvideApplication.class)
@WebAppConfiguration
public class EurekaServiceInjector1ApplicationTests {

	private MockMvc mvc;
	
	@Before
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(new DemoController()).build();
	}
	
    @Test
    public void contextLoads(){
    	try {
    		mvc.perform(
    				MockMvcRequestBuilders.get("/hello")
    				.accept(org.springframework.http.MediaType.APPLICATION_JSON))
    				.andExpect(status().isOk())
    				.andExpect(content().string(equalTo("hello , Feign hello.")));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
