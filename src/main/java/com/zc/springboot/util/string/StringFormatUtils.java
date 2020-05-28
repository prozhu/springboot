package com.zc.springboot.util.string;

import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * 字符串转换的工具类
 * @author ：djzc
 * @createTime ：2020年5月20日 下午3:42:35 
 * @updateTime ：2020年5月20日 下午3:42:35
 */
@Slf4j
public class StringFormatUtils {

	/**
	 * formData 格式的字符串，转换为json 字符串（主要为了接口传参准备的）
	 *  例如：原始值：version=web&goalDayId=bcbaaa56f3ef4e178b4a98252f418e4f
	 *     转换后的值：{"goalDayId":"bcbaaa56f3ef4e178b4a98252f418e4f","version":"web"}
	 * @author ：djzc
	 * @createTime ：2020年5月20日 下午3:32:21 
	 * @updateTime ：2020年5月20日 下午3:32:21 
	 * @alterMan：djzc
	 * @param formDataStr 原始字符串
	 * @return：   
	 */
	public static String formDataToJson(String formDataStr) {
		if (ObjectUtils.isEmpty(formDataStr)) {
			log.error("传递的字符串为空，请检查");
			return "";
		}

		JSONObject result = new JSONObject();
		// 根据 "&"符号，分隔
		String[] split = formDataStr.split("&");
		if (ObjectUtils.isEmpty(split)) {
			log.error("传递的字符串不符合规范！");
			return "";
		}

		for (int i = 0; i < split.length; i++) {
			// 在根据 "="号进行分隔
			String[] fieldArr = split[i].split("=");
			String tempValue = "";
			if (fieldArr.length == 2) {
				tempValue = fieldArr[1];
			}
			result.put(fieldArr[0], tempValue);
		}
		return result.toJSONString();
	}
}
