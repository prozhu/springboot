package com.open.utils.dingding;

/**
 * 消息类型枚举
 */
 public enum MsgTypeEnum {
     /**
      * 文本
      */
     text("text"),
     /**
      * 超链接
      */
     link("link"),

     /**
      * markdown
      */
     markdown("markdown");


    private String msgType;

    public String getMsgType() {
        return this.msgType;
    }

    MsgTypeEnum(String msgType) {
        this.msgType = msgType;
    }

}