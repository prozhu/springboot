package com.open.exceptions;

/**
 * 自定义参数校验异常
 * @author zc
 * @createTime 2021/1/7 13:48
 */
public class ParamValidatorException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String msg;

    public ParamValidatorException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
