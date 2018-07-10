package com.demo.annotation;

import java.lang.annotation.*;

/**
 * @Description: 锁的参数
 * @Author: chenfeixiang
 * @Date: Created in 11:53 2018/7/10
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheParam {

    /**
     * 字段名称
     *
     * @return String
     */
    String name() default "";
}
