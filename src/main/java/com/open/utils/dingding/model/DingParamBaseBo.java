package com.open.utils.dingding.model;

import lombok.Data;

/**
 * 钉钉推送的基础入参模型
 * @author ：zc
 * @createTime ：2021/4/27 11:25
 */
@Data
public abstract  class DingParamBaseBo {

    /**
     * 消息类型
     * 文本 (text)、链接 (link)、markdown(markdown)、ActionCard、FeedCard消息类型
     */
    public String msgtype;
}
