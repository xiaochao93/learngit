package com.duia.english.common.exception;

/**
 * Created by liuhao on 2018/4/13.
 */
public class CustomException extends Exception {
    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
