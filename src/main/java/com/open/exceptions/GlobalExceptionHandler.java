package com.open.exceptions;

import com.open.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获
 * @author Administrator
 * @date 2024/9/10 17:23
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理接口访问限制异常
     */
    @ExceptionHandler(AccessLimitException.class)
    public Result handleBizException(AccessLimitException ex){
        return Result.fail(ex.getMsg());
    }

}
