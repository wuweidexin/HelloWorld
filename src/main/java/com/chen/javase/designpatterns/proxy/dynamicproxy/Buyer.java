package com.chen.javase.designpatterns.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
/**
 * @ClassName: Buyer 
 * @Description: 代理模式的灵活应用就是在运行的时候动态的改变被代理的类和执行的接口
 * 比如下面的Seller是被代理的类，sellhouse是要执行的接口
 * @author junquan_chen 
 * @date 2017年11月1日 下午3:22:11
 */
public class Buyer {
    public static void main(String[] args) {
        Seller sel = new Seller();
        InvocationHandler handler = new Intermediary(sel);
        Class cla = sel.getClass();
        
        SellHouse selhouse = (SellHouse) Proxy.newProxyInstance(cla.getClassLoader(), cla.getInterfaces(), handler);
        selhouse.sellhouse();
    }

}
