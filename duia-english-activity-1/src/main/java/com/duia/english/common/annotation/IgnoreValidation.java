package com.duia.english.common.annotation;

import java.lang.annotation.*;

/**
 * Created by liuhao on 2018/3/9.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface IgnoreValidation {
}
