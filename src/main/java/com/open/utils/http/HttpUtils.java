package com.open.utils.http;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * http请求类的工具包
 * @author ：zc
 * @createTime ：2021/2/2 15:18
 */
public class HttpUtils {

    /**
     *  获取restTemplate
     * @return
     */
    private static RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    /**
     * 发送Get 请求（不带请求参数）
     * @param url
     * @return
     */
    public static String sendGet(String url) {
        RestTemplate restTemplate =  restTemplate();
        ResponseEntity responseEntity = restTemplate.getForEntity(url, String.class);
        return responseEntity.getBody().toString();
    }
}
