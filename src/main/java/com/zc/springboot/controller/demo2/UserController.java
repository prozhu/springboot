package com.zc.springboot.controller.demo2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * springboot 第二天：web开发
 * @author ：djzc
 * @createTime ：2018年12月12日 下午2:48:34 
 * @updateTime ：2018年12月12日 下午2:48:34
 */
@RestController
public class UserController {

	@Autowired
	private MyProperties properties;

	@RequestMapping(method = RequestMethod.GET, value = { "getUserInfo" })
	public User getUsreInfo() {
		User user = new User();
		user.setName("张三");
		user.setAge("13");
		return user;
	}

	/**
	 * 读取配置文件中的信息
	 * @author ：djzc
	 * @createTime ：2018年12月12日 下午3:12:44 
	 * @updateTime ：2018年12月12日 下午3:12:44 
	 * @alterMan：djzc
	 * @return：
	 */
	@RequestMapping(method = RequestMethod.GET, value = { "getMyPropertiesInfo" })
	public MyProperties getMyPropertiesInfo() {
//		MyProperties properties = new MyProperties();
		return properties;
	}
}
