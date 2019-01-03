package com.zc.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 钉钉机器人webhook 地址配置
 * @author ：djzc
 * @createTime ：2019年1月2日 下午4:02:13 
 * @updateTime ：2019年1月2日 下午4:02:13
 */
@Component
@PropertySource({ "classpath:dingDingWebhookConfig.properties" })
public class DingDingConstantConfig {

	@Value("${java.study.webhook}")
	private String charbotWebhook;

	public String getCharbotWebhook() {
		return charbotWebhook;
	}

	public void setCharbotWebhook(String charbotWebhook) {
		this.charbotWebhook = charbotWebhook;
	}

}
