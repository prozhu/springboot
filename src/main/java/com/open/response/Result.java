package com.open.response;

import lombok.Data;

/**
 * 响应参数类
 * @author zc
 * @date 2024/9/10 17:28
 */
@Data
public class Result<T> {
    private String message;
    private int code;
    private T data;
    boolean success;

    /**
     * 成功
     * @author zc
     * @date 2024/9/10 17:38
     * @param msg 消息
     * @return com.open.response.Result
     */
    public static Result success(String msg) {
        Result data = new Result();
        data.setCode(200);
        data.setSuccess(Boolean.TRUE);
        data.setMessage(msg);
        return data;
    }


    /**
     * 成功
     * @author zc
     * @date 2024/9/10 17:38
     * @param result 数据
     * @return com.open.response.Result
     */
    public static Result success(Object result) {
        Result data = new Result();
        data.setCode(200);
        data.setSuccess(Boolean.TRUE);
        data.setMessage("success");
        data.setData(result);
        return data;
    }


    /**
     * 失败
     * @author zc
     * @date 2024/9/10 17:44
     * @param msg 消息
     * @return com.open.response.Result
     */
    public static Result fail(String msg) {
        Result data = new Result();
        data.setCode(200);
        data.setSuccess(Boolean.FALSE);
        data.setMessage(msg);
        return data;
    }

}
