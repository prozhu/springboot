package com.open.exceptions;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 自定义异常 接口限制
 * @author Administrator
 * @date 2024/9/10 10:56
 */
@Data
@RequiredArgsConstructor
public class AccessLimitException extends RuntimeException {
    private String msg;

    public AccessLimitException(String msg) {
        this.msg = msg;
    }
}
