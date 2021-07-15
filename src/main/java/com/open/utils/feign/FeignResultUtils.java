package com.open.utils.feign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;

/**
 * feign 接口调用后，返回结果处理工具类
 * @author ：zc
 * @createTime ：2019年12月26日 上午10:38:52
 * @updateTime ：2019年12月26日 上午10:38:52
 */
@Slf4j
public class FeignResultUtils {

    /**
     * 是否成功key标识
     */
    private static final String SUCCESS_KEY = "success";
    /**
     * 消息key标识
     */
    private static final String MSG_KEY = "msg";
    /**
     * 数据key标识
     */
    private static final String DATA_KEY = "data";
    private static final String LIST_KEY = "list";
    private static final String TOTAL_KEY = "total";

    /**
     * 将feign调用返回的数据，转换为实体对象
     * @param result   json数据
     * @param objClass 需要转换的类信息
     * @return 调用失败，则抛出异常
     * @author ：zc
     * @createTime ：2019年12月26日 上午11:29:44
     * @updateTime ：2019年12月26日 上午11:29:44
     * @alterMan：zc
     */
    public static <T> T feignResultConvertObj(String result, Class<T> objClass) {
        JSONObject dataObj = JSON.parseObject(result);
        boolean success = dataObj.getBoolean(SUCCESS_KEY);
        String msg = dataObj.getString(MSG_KEY);
        // 1. 请求是否成功
        isSuccess(success, msg);
        // 2. 请求成功
        String data = dataObj.getString(DATA_KEY);
        return JSON.parseObject(data, objClass);
    }

    /**
     * success为false则抛出自定义Exception
     * @param success feign请求是否成功标识
     * @author yhc
     * @date 2020/1/9 10:48
     */
    private static void isSuccess(boolean success, String msg) {
        if (!success) {
            log.error(msg);
            throw new RuntimeException(msg);
        }
    }


    /**
     * 将feign调用返回的数据，转换为实体对象集合
     * @param result   json数据
     * @param objClass 需要转换的类信息
     * @return 调用失败，则抛出异常
     * @author ：zc
     * @createTime ：2019年12月26日 下午1:11:42
     * @updateTime ：2019年12月26日 下午1:11:42
     * @alterMan：zc
     */
    public static <T> List<T> feignResultConvertList(String result, Class<T> objClass) {
        JSONObject dataObj = JSON.parseObject(result);
        boolean success = dataObj.getBoolean(SUCCESS_KEY);
        String msg = dataObj.getString(MSG_KEY);
        // 1. 请求是否成功
        isSuccess(success, msg);
        // 2. 请求成功
        String data = dataObj.getString(DATA_KEY);
        return JSON.parseArray(data, objClass);
    }


}
