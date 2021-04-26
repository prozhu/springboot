package com.open.utils.dingding;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import com.taobao.api.Constants;
import com.taobao.api.DefaultTaobaoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

/**
 * 推送钉钉消息
 * @author ：zc
 * @createTime ：2021/4/25 15:14
 */
@Slf4j
@Service
public class DingManage {

    private static final String accessToken = "f2ed383f6bc103c1d8d0c605948b8e5bbf291fe4e5d1d69d9b3468ac1e696a6b";

    private static final String serverUrl = "https://oapi.dingtalk.com/robot/send?access_token=" + accessToken;

    /**
     *  钉钉推送(给全员推送)
     * @param msgTypeEnum 消息类型
     * @param content 内容
     * @return
     */
    public boolean sendMsg(MsgTypeEnum msgTypeEnum, String content) {
        switch (msgTypeEnum) {
            case text:
                return sendTextMsg(content);
        }
        return true;
    }


    /**
     * 发送文本消息
     * @param content
     * @return
     */
    private boolean sendTextMsg(String content) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        //设置消息类型
        request.setMsgtype(MsgTypeEnum.text.getMsgType());

        //设置消息内容
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(content);
        request.setText(text);

        //被 “@”方对象
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        //是否@ 所有人
        at.setIsAtAll(true);
        //@指定人
        // at.setAtMobiles(Arrays.asList("15927294078"));
        request.setAt(at);
        //设置超时时间
        DefaultDingTalkClient client =  new DefaultDingTalkClient(serverUrl);
        client.setConnectTimeout(3000);
        client.setReadTimeout(5000);

        OapiRobotSendResponse response = null;
        try {
            response = client.execute(request);
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("发送钉钉推送异常");
            return false;
        }
        if (!Objects.equals("ok", response.getErrmsg())) {
            log.error("发送钉钉推送异常{}", response.getErrmsg());
            return false;
        }
        log.info(JSON.toJSONString(response));
        return true;
    }


}



