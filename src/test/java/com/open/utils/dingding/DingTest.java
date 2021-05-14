package com.open.utils.dingding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.open.utils.dingding.model.DingParamBaseBo;
import com.open.utils.dingding.model.DingTextParamBo;
import com.open.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;
import java.util.function.BiFunction;

/**
 *
 * @author ：zc
 * @createTime ：2021/4/25 13:52
 */
@Slf4j
public class DingTest {

    private static final String accessToken = "f2ed383f6bc103c1d8d0c605948b8e5bbf291fe4e5d1d69d9b3468ac1e696a6b";

    private static final String serverUrl = "https://oapi.dingtalk.com/robot/send?access_token=" + accessToken;


    @Test
    public void testText() {
        String content = "hello Exception";
        boolean atAll = true;
        List<String> mobiles = Arrays.asList("");
        DingParamBaseBo paramBo = new DingTextParamBo(content, atAll, mobiles);
        DingManage dingManage = new DingManage();
        dingManage.sendMsg(paramBo);
    }

    @Test
    public void testRestGetWithParam() {
        String param = buildRequestParam("116.481028", "39.989643",
                "116.465302", "40.004717");
        String url = "https://restapi.amap.com/v3/direction/driving?" + param;
        String result = HttpUtils.sendGet(url);
        log.info(result);

    }



    private String  buildRequestParam(String sourceLongitude, String sourceLatitude,
                                     String goalLongitude, String goalLatitude) {
        Map<String, Object> param = new HashMap<String, Object>();
        //起始经纬度
        param.put("origin", sourceLongitude + "," + sourceLatitude);
        //目标经纬度
        param.put("destination", goalLongitude + "," + goalLatitude);
        param.put("extensions", "base");
        param.put("output", "JSON");
        //应用key
        param.put("key", "eef203e01accb25312672eea4cbb5fc4");
        //线路策略
        param.put("strategy", "20");

        return buildGetRequestParam(param);
    }

    private String buildGetRequestParam(Map<String, Object> map) {
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        StringBuilder param = new StringBuilder();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue().toString();
            param.append("&").append(key).append("=").append(value);
        }
        return param.substring(1);
    }


}
