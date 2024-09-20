package com.zc.springboot.controller.demo1;

import com.open.annotation.AccessLimit;
import com.open.annotation.RepeatSubmit;
import com.open.response.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 接口限流controller
 * @author Administrator
 * @date 2024/9/10 15:17
 */
@RestController
public class InterfaceLimitController {

    /**
     * 验证接口限流
     * @author zc
     * @date 2024/9/12 9:13
     * @return com.open.response.Result
     */
    @GetMapping("getUser")
    @AccessLimit(times = 10, timeUnit = TimeUnit.SECONDS, maxCount = 5, preKey = "getUser", msg = "服务请求达到最大限制，请勿重复访问！")
    public Result getUser() {
        return Result.success(new String[]{"1", "2"});
    }


    @PostMapping("saveUser")
    @RepeatSubmit(limitType = RepeatSubmit.Type.PARAM,lockTime = 5,timeUnit = TimeUnit.SECONDS,preKey = "saveUser",msg = "请求重复提交")
    public Result saveUser() {
        return Result.success("成功保存");
    }

}
