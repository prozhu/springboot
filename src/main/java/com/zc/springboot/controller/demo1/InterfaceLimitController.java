package com.zc.springboot.controller.demo1;

import com.open.annotation.AccessLimit;
import com.open.annotation.AntiShake;
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
     * @return com.open.response.Result
     * @author zc
     * @date 2024/9/12 9:13
     */
    @GetMapping("getUser")
    @AccessLimit(times = 10, timeUnit = TimeUnit.SECONDS, maxCount = 5, preKey = "getUser", msg = "服务请求达到最大限制，请勿重复访问！")
    public Result getUser() {
        return Result.success(new String[]{"1", "2"});
    }


    /**
     * 验证防止接口重复提交
     * @return com.open.response.Result
     * @author zc
     * @date 2024/9/26 14:47
     */
    @PostMapping("saveUser")
    @RepeatSubmit(limitType = RepeatSubmit.Type.PARAM, lockTime = 5, timeUnit = TimeUnit.SECONDS, preKey = "saveUser", msg = "请求重复提交")
    public Result saveUser() {
        return Result.success("成功保存");
    }

    /**
     * 验证接口防抖
     * @return com.open.response.Result
     * @author zc
     * @date 2024/9/26 14:47
     */
    @PostMapping("clickButton")
    @AntiShake(value = 5000, timeUnit = TimeUnit.MILLISECONDS, preKey = "clickButton")
    public Result clickButton() {
        return Result.success("成功点击按钮");
    }
}
