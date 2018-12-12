package com.zc.springboot.controller.demo1;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping(method = RequestMethod.GET, value = { "hello" })
	public Map<String, String> getString() {
		Map<String, String> result = new HashMap<>();
		result.put("name", "TOME");
		result.put("age", "18");
		return result;
//		return "hello springboot，我是get请求";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = { "helloPost" })
	public String getPostString() {
		return "hello springboot，我是post请求";
	}
	
	/*
	 * 测试使用Mock ，来接收参数
	 */
	@RequestMapping(method = RequestMethod.POST, value = { "receiveParam" })
	public String getTestMockMvc(String name, String age) {
		return "我是来接收参数的，你呢";
	}
	
	

}