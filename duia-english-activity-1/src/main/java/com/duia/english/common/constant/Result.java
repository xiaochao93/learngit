package com.duia.english.common.constant;

/**
 * Created by liuhao on 2018/4/11.
 */
public class Result {
    private Integer state;
    private String message;
    private Object data;

    //限制构造函数只能给ResponEntity用
    protected Result(){

    }

    protected Result(Integer state) {
        this.state = state;
    }


    protected Result(Integer state, String message) {
        this.state = state;
        this.message = message;
    }

    protected Result(Integer state, String message, Object data) {
        this.state = state;
        this.message = message;
        this.data = data;
    }

    public Integer getState() {
        return state;
    }

    public Result setState(Integer state) {
        this.state = state;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

}
