
package com.zc.springboot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.zc.springboot.controller.HelloController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

//	springmvc 的测试框架
	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
	}

	/*
	 * 使用Mock测试get请求
	 */
	@Test
	public void getHello() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
	}
	
	
	/*
	 * 使用MOck测试post请求
	 */
	@Test
	public void getHelloPost() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/helloPost").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
	}
	
	@Test
	public void TestDeliveryParam() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>() ;
		params.add("name", "zhuc");
		params.add("age", "18");
		mvc.perform(MockMvcRequestBuilders.post("/receiveParam").params(params).accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	public void contextLoads() {
	}

}
