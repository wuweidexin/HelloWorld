package com.chen.javase.designpatterns.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Intermediary implements InvocationHandler{
    private Object sub;
    
    public Intermediary(Object sub) {
        this.sub = sub;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开始代理");
        method.invoke(sub, args);
        System.out.println("结束代理");
        return null;
    }

}
