package com.open.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 限制重复提交的注解
 * @author Administrator
 * @date 2024/9/9 17:53
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatSubmit {
    /**
     * 定义了两种防止重复提交的方式，PARAM 表示基于方法参数来防止重复，TOKEN 则可能涉及生成和验证token的机制
     */
    enum Type {PARAM, TOKEN}

    /**
     * 设置默认的防重提交方式为基于方法参数。开发者可以不指定此参数，使用默认值。
     * @return Type
     */
    Type limitType() default Type.PARAM;

    /**
     * 时间单位,默认为秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 允许设置加锁的过期时间，默认为5秒。这意味着在第一次请求之后的5秒内，相同的请求将被视为重复并被阻止
     */
    long lockTime() default 5;

    //提供了一个可选的服务ID参数，通过token时用作KEY计算
    String serviceId() default "";

    /**
     * 提示语
     */
    String msg() default "请勿重复提交！";

    /**
     * redis key 的前缀
     */
    String preKey();

}
