package com.zc.springboot.util;

import java.io.IOException;

import com.dingtalk.chatbot.DingtalkChatbotClient;
import com.dingtalk.chatbot.SendResult;
import com.dingtalk.chatbot.message.MarkdownMessage;

public class DingPushContentUtil {

	private boolean sendMarkDownMsg(MarkdownMessage message, String webHook) {
		DingtalkChatbotClient client = new DingtalkChatbotClient();
		SendResult result = new SendResult();
		try {
			result = client.send(webHook, message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.isSuccess();

	}

}
