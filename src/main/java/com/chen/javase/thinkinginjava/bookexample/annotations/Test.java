package com.chen.javase.thinkinginjava.bookexample.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义注解
 * @author chen
 */
//用来定义你的注解将应用于什么地方（例如是一个方法或一个域）
@Target(ElementType.METHOD) 
//用来定义该注解在哪一个级别可用
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {

}
