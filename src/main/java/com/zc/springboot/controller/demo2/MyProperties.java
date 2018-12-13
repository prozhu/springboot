package com.zc.springboot.controller.demo2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *  读取配置文件中的值
 * @author ：djzc
 * @createTime ：2018年12月12日 下午3:00:28 
 * @updateTime ：2018年12月12日 下午3:00:28
 */
@Component
public class MyProperties {

	@Value("${com.zc.title}")
	private String title;

	@Value("${com.zc.desc}")
	private String desc;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
