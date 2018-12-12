
package com.zc.springboot.demo1;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.zc.springboot.controller.demo1.HelloController;

/**
 * springboot 的入门篇（一）
 * @author ：djzc
 * @createTime ：2018年12月12日 下午2:40:38 
 * @updateTime ：2018年12月12日 下午2:40:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTest {

//	springmvc 的测试框架MockMvc
	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		// 初始化MockMvc对象
		mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
	}

	/*
	 * 使用Mock测试get请求
	 */
	@Test
	public void getHello() throws Exception {
		// perform：执行一个RequestBuilder请求，会自动执行SpringMVC的流程并映射到相应的控制器执行处理；
		mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
				// andExpect：验证结果是否正确，状态码200
				.andExpect(MockMvcResultMatchers.status().isOk())
				// 添加ResultHandler结果处理器，比如调试时打印结果到控制台（对返回的数据进行的判断）；
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	/*
	 * 使用MOck测试post请求
	 */
	@Test
	public void getHelloPost() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.post("/helloPost").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	public void TestDeliveryParam() throws Exception {
		// 使用Post方式，带多个参数的请求
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("name", "zhuc");
		params.add("age", "18");
		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.post("/receiveParam").params(params)
						.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print()).andReturn();
		String resultStr = result.getResponse().getContentAsString();
		System.out.println(resultStr);
	}

	@Test
	public void contextLoads() {
	}

}
