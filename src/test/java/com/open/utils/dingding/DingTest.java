package com.open.utils.dingding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.open.utils.dingding.model.DingParamBaseBo;
import com.open.utils.dingding.model.DingTextParamBo;
import com.open.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


}
