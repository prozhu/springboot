package com.open.utils.dingding.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * markdown 类钉钉推送
 * @author zc
 * @createTime 2021/4/28 17:11
 */
@NoArgsConstructor
@Data
public class DingMarkdownParamBo extends DingParamBaseBo {

    /**
     * markdown : {"title":"杭州天气","text":"#### 杭州天气 @150XXXXXXXX \n> 9度，西北风1级，空气良89，相对温度73%\n> ![screenshot](https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png)\n> ###### 10点20分发布 [天气](https://www.dingtalk.com) \n"}
     * at : {"atMobiles":["150XXXXXXXX"],"atUserIds ":["user123"],"isAtAll":false}
     */
    private MarkdownBean markdown;
    private AtBean at;


    /**
     * 有参构造
     * @param title 标题
     * @param content 内容
     * @param atAll 是否@所有人
     * @param mobiles  被@人的手机号码
     */
    public DingMarkdownParamBo(String title, String content, Boolean atAll, List<String> mobiles) {
        this.setMsgtype("markdown");
        if (!ObjectUtils.isEmpty(atAll)) {
            at = new DingMarkdownParamBo.AtBean();
            at.setIsAtAll(atAll);
            at.setAtMobiles(mobiles);
        }
        markdown = new MarkdownBean();
        markdown.setText(content);
    }

    @NoArgsConstructor
    @Data
    public static class MarkdownBean {
        /**
         * title : 杭州天气
         * text : #### 杭州天气 @150XXXXXXXX
         > 9度，西北风1级，空气良89，相对温度73%
         > ![screenshot](https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png)
         > ###### 10点20分发布 [天气](https://www.dingtalk.com)
         */
        private String title;
        private String text;
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
}
