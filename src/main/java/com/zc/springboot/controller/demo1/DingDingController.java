package com.zc.springboot.controller.demo1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	/**
	 * 测试markdown 类型的文本内容推送
	 * @author ：djzc
	 * @createTime ：2019年1月15日 上午9:58:48 
	 * @updateTime ：2019年1月15日 上午9:58:48 
	 * @alterMan：djzc
	 * @param name
	 * @param age
	 * @return
	 * @throws IOException：
	 */
	@RequestMapping(method = RequestMethod.GET, value = { "testDingDingMarkdown" })
	public SendResult testDingDingMarkdown() throws IOException {
		DingtalkChatbotClient client = new DingtalkChatbotClient();
		MarkdownMessage message = new MarkdownMessage();
		message.setTitle("东经云盘消息推送");
		message.add(MarkdownMessage.getHeaderText(3, "东经云盘消息推送"));
		message.add(MarkdownMessage.getReferenceText("reference text"));
		message.add("\n\n");

		message.add("normal text");
		message.add("\n\n");

		message.add("\n\n");

		message.add("\n\n");
		ArrayList<String> orderList = new ArrayList<String>();
		orderList.add("order item1");
		orderList.add("order item2");
		message.add(MarkdownMessage.getOrderListText(orderList));
		message.add("\n\n");

		List<String> atMobiles = new ArrayList<>();
		atMobiles.add("15927294078");
		atMobiles.add("13588043792");
		message.setAtMobiles(atMobiles);

		message.add(MarkdownMessage.getImageText(
				"http://img06.tooopen.com/images/20171130/tooopen_sy_229311329435.jpg"));
		message.add(MarkdownMessage.getLinkText("去东经云盘瞅一眼~~", "https://www.baidu.com/"));
		SendResult result = client.send(config.getCharbotWebhook(), message);
		System.out.println(result);
		return result;
	}

}
