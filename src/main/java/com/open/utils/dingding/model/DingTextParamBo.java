package com.open.utils.dingding.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 文本类钉钉推送
 * @author ：zc
 * @createTime ：2021/4/27 11:24
 */
@Data
public class DingTextParamBo extends DingParamBaseBo {


    /**
     * 被@人的信息
     * at : {"atMobiles ":["180xxxxxx"],"atUserIds ":["user123"],"isAtAll ":false}
     * text : {"content ":"我就是我, @XXX 是不一样的烟火"}
     */
    private AtBean at;
    /**
     * 文本实体类
     */
    private TextBean text;


    /**
     * 有参构造
     * @param content 内容
     * @param atAll 是否@所有人
     * @param mobiles  被@人的手机号码
     */
    public DingTextParamBo(String content, Boolean atAll, List<String> mobiles) {
        this.setMsgtype("text");
        if (!ObjectUtils.isEmpty(atAll)) {
            at = new AtBean();
            at.setIsAtAll(atAll);
            at.setAtMobiles(mobiles);
        }
        text = new TextBean();
        text.setContent(content);
    }

    @NoArgsConstructor
    @Data
    public static class AtBean {
        /**
         * atMobiles  : ["180xxxxxx"]
         * atUserIds  : ["user123"]
         * isAtAll  : false
         */
        private boolean isAtAll;
        private List<String> atMobiles;
        private List<String> atUserIds;
        public boolean getIsAtAll() {
            return this.isAtAll;
        }
        public void setIsAtAll(boolean isAtAll) {
            this.isAtAll = isAtAll;
        }
    }

    @NoArgsConstructor
    @Data
    public static class TextBean {
        /**
         * content  : 我就是我, @XXX 是不一样的烟火
         */
        private String content;
    }
}
