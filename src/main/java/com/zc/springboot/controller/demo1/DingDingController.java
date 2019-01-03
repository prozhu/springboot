package com.zc.springboot.controller.demo1;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dingtalk.chatbot.DingtalkChatbotClient;
import com.dingtalk.chatbot.SendResult;
import com.dingtalk.chatbot.message.TextMessage;
import com.zc.springboot.config.DingDingConstantConfig;

/**
 * 测试钉钉机器人的controller
 * @author ：djzc
 * @createTime ：2019年1月2日 下午5:02:02 
 * @updateTime ：2019年1月2日 下午5:02:02
 */
@RestController
public class DingDingController {

	/**
	 * 从配置文件中读取机器人的webhook
	 */
	@Autowired
	private DingDingConstantConfig config;

	@RequestMapping(method = RequestMethod.GET, value = { "testDingDing" })
	public SendResult testDingDing(String name, String age) throws IOException {
		DingtalkChatbotClient client = new DingtalkChatbotClient();
		TextMessage message = new TextMessage("我就是我, 是不一样的烟火");
		ArrayList<String> atMobiles = new ArrayList<String>();
		atMobiles.add("15927294078");
		message.setAtMobiles(atMobiles);
//		message.setIsAtAll(true);
		SendResult result = client.send(config.getCharbotWebhook(), message);
		System.out.println(result);
		return result;
	}

}
