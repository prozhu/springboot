package com.zc.springboot.controller.demo1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.dingtalk.chatbot.message.Message;

/**
 * 构建消息推送的模型
 * @author ：djzc
 * @createTime ：2019年1月16日 下午4:21:10 
 * @updateTime ：2019年1月16日 下午4:21:10
 */
public class MarkdownMessage implements Message {

	private String webHook;

	private List<String> atMobiles;
	private boolean isAtAll;

	private String title;

	private List<String> items = new ArrayList<String>();

	public List<String> getAtMobiles() {
		return atMobiles;
	}

	public void setAtMobiles(List<String> atMobiles) {
		this.atMobiles = atMobiles;
	}

	public boolean isAtAll() {
		return isAtAll;
	}

	public void setIsAtAll(boolean isAtAll) {
		this.isAtAll = isAtAll;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWebHook() {
		return webHook;
	}

	public void setWebHook(String webHook) {
		this.webHook = webHook;
	}

	public void add(String text) {
		items.add(text);
	}

	public static String getBoldText(String text) {
		return "**" + text + "**";
	}

	public static String getItalicText(String text) {
		return "*" + text + "*";
	}

	public static String getLinkText(String text, String href) {
		return "[" + text + "](" + href + ")";
	}

	public static String getImageText(String imageUrl) {
		return "![image](" + imageUrl + ")";
	}

	public static String getHeaderText(int headerType, String text) {
		if (headerType < 1 || headerType > 6) {
			throw new IllegalArgumentException("headerType should be in [1, 6]");
		}

		StringBuffer numbers = new StringBuffer();
		for (int i = 0; i < headerType; i++) {
			numbers.append("#");
		}
		return numbers + " " + text;
	}

	public static String getReferenceText(String text) {
		return "> " + text;
	}

	public static String getOrderListText(List<String> orderItem) {
		if (orderItem.isEmpty()) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= orderItem.size() - 1; i++) {
			sb.append(String.valueOf(i) + ". " + orderItem.get(i - 1) + "\n");
		}
		sb.append(String.valueOf(orderItem.size()) + ". " + orderItem.get(orderItem.size() - 1));
		return sb.toString();
	}

	public static String getUnorderListText(List<String> unorderItem) {
		if (unorderItem.isEmpty()) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < unorderItem.size() - 1; i++) {
			sb.append("- " + unorderItem.get(i) + "\n");
		}
		sb.append("- " + unorderItem.get(unorderItem.size() - 1));
		return sb.toString();
	}

	public String toJsonString() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("msgtype", "markdown");

		Map<String, Object> markdown = new HashMap<String, Object>();
		markdown.put("title", title);

		StringBuffer markdownText = new StringBuffer();
		for (String item : items) {
			markdownText.append(item + "\n");
		}
		Map<String, Object> atItems = new HashMap<String, Object>();
		if (atMobiles != null && !atMobiles.isEmpty()) {
			atItems.put("atMobiles", atMobiles);
			for (String mobile : atMobiles) {
				markdownText.append("@").append(mobile).append(",");
			}
		}
		if (isAtAll) {
			atItems.put("isAtAll", isAtAll);
		}
		markdown.put("text", markdownText.toString());
		result.put("markdown", markdown);

		result.put("at", atItems);

		return JSON.toJSONString(result);
	}

}
