package com.open.utils.http;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Map;

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
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        //设置超时时间
        factory.setConnectTimeout(6000);
        //设置响应时间
        factory.setReadTimeout(2000);
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    /**
     * 发送Get 请求（不带请求参数）
     * @param url
     * @return
     */
    public static String sendGet(String url) {
        RestTemplate restTemplate = restTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        return forEntity.getBody().toString();
    }


    /**
     * 发送Get 请求（带请求参数）
     * @param url 请求路径
     * @param param 传参
     * @return
     */
    public static JSONObject sendGet(String url, Map<String, Object> param) {
        RestTemplate restTemplate = restTemplate();
        JSONObject result = restTemplate.getForObject(url, JSONObject.class, param);
        return result;
    }

    /**
     * 发送post请求
     * @param url
     * @param params
     * @return
     */
    public static JSONObject sendPostRequest(String url, Map params) {
        RestTemplate client = new RestTemplate();
        return client.postForEntity(url, params, JSONObject.class).getBody();
    }
}
