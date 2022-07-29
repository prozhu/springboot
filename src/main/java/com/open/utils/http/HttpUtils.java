package com.open.utils.http;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * http请求类的工具包
 * @author ：zc
 * @createTime ：2021/2/2 15:18
 */
public class HttpUtils {

    /**
     * 获取restTemplate
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
     * @param url 请求路径
     * @return
     */
    public static String sendGet(String url) {
        RestTemplate restTemplate = restTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        return forEntity.getBody().toString();
    }


    /**
     * 发送Get 请求（带请求参数）
     * @param url   请求路径
     * @param param 传参
     * @return
     */
    public static String sendGet(String url, Map<String, Object> param) {
        RestTemplate restTemplate = restTemplate();
        return restTemplate.getForObject(url, String.class, param);
    }

    /**
     * 发送post请求
     * @param url   请求路径
     * @param param 传参
     * @return
     */
    public static String sendPostRequest(String url, Map param) {
        RestTemplate client = new RestTemplate();
        return client.postForEntity(url, param, String.class).getBody();
    }

    /**
     * 发送post请求(不带参数)
     * @param url 请求路径
     * @return
     */
    public static String sendPostRequest(String url) {
        return sendPostRequest(url, null);
    }

    /**
     * 获取请求的ip地址
     * @author zhucheng
     * @date 2022/7/29 10:12
     * @param request
     * @return java.lang.String
     */
    public static String getIpAddr(HttpServletRequest request) {
        String unknown = "unknown";
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || unknown.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            String localHost = "127.0.0.1";
            String mac = "0:0:0:0:0:0:0:1";
            if (localHost.equals(ipAddress) || mac.equals(ipAddress)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
}
