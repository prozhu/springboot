package com.open.utils.dingding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.open.utils.dingding.model.DingMarkdownParamBo;
import com.open.utils.dingding.model.DingParamBaseBo;
import com.open.utils.dingding.model.DingTextParamBo;
import com.open.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * 推送钉钉消息
 * @author ：zc
 * @createTime ：2021/4/25 15:14
 */
@Slf4j
@Component
public class DingManage {

    /**
     * 钉钉webhook
     */
    private static final String accessToken = "e023be02595f6a5545fb934b4a264be903d94d239a0dcfffbd6aa5b73fb312f5";
    private static final String serverUrl = "https://oapi.dingtalk.com/robot/send?access_token=" + accessToken;

    //https://oapi.dingtalk.com/robot/send?access_token=e023be02595f6a5545fb934b4a264be903d94d239a0dcfffbd6aa5b73fb312f5
    /**
     *  钉钉推送
     * @param param
     * @return
     */
    public boolean sendMsg(DingParamBaseBo param) {
        log.info("钉钉推送参数为：{}", JSON.toJSONString(param));
        String msgType = param.getMsgtype();
        switch (msgType) {
            case "text":
                return sendTextMsg((DingTextParamBo) param);
            case "markdown":
                return sendMarkdownMsg((DingMarkdownParamBo) param);
        }
        return true;
    }


    /**
     * 发送markdown消息
     * @param param
     * @return
     */
    private boolean sendMarkdownMsg(DingMarkdownParamBo param) {
        JSONObject result = sendRequest(param);
        log.info(result.toJSONString());
        if (!Objects.equals("ok", result.getString("errmsg"))) {
            log.error("钉钉推送异常{}", result.getString("errmsg"));
            return false;
        }
        return true;
    }

    private JSONObject sendRequest(DingParamBaseBo param) {
        Map<String, Object> map = JSONObject.parseObject(JSON.toJSONString(param));
        String result = HttpUtils.sendPostRequest(serverUrl, map);
        return JSON.parseObject(result);
    }

    /**
     * 发送文本消息
     * @param param
     * @return
     */
    private boolean sendTextMsg(DingTextParamBo param) {
        JSONObject result = sendRequest(param);
        log.info(result.toJSONString());
        if (!Objects.equals("ok", result.getString("errmsg"))) {
            log.error("钉钉推送异常{}", result.getString("errmsg"));
            return false;
        }
        return true;
    }

}



