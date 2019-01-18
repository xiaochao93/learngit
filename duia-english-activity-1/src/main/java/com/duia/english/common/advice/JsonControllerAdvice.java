package com.duia.english.common.advice;

import com.duia.english.common.constant.ResponseEntity;
import com.duia.english.common.constant.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuhao on 2018/4/12.
 */
@RestControllerAdvice("com.duia.english.controller.rest")
public class JsonControllerAdvice {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 在其执行之前初始化数据绑定器
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 把值绑定到model,使全局model可以获取到该值
     *
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
    }

    /**
     * 全局异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result errorHandler(Exception ex, HttpServletRequest request) {
        StringBuffer uri = request.getRequestURL();
        logger.error("{}:", uri, ex);
        return ResponseEntity.EXCEPTION.setMessage(ex.getMessage() == null ? ex.getClass().getName() : ex.getMessage()).setData(uri + " 出现 " + ex.getClass().getName() + " 异常");
    }
}
