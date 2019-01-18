package com.duia.english.common.constant;

/**
 * Created by liuhao on 2018/4/11.
 */
public class ResponseEntity {
    public  static Result OK = new Result().setState(0).setMessage("success");

    public  static Result FAILURE = new Result().setState(-1).setMessage("failure");

    public  static Result NOT_FOUND = new Result().setState(-404).setMessage("找不到内容");

    public  static Result SERVER_ERROR = new Result().setState(-500).setMessage("服务器错误");

    public  static Result VALIDATION_FAILURE = new Result().setState(-201).setMessage("参数校验失败");

    public  static Result EXCEPTION = new Result().setState(-100);

    public static Result EXCEPTION(String message) {
        return new Result(-100, message);
    }

    public static Result UNKNOWUSER() {
        return new Result(-99, "未知用户");
    }

    public static Result FAILURE(String message) { return new Result(-1, message); }

    public static Result OK(Object data) { return new Result(0, "success",data); }

    public static Result READY(int state, String message, Object data) {
        return new Result(state, message, data);
    }

}
