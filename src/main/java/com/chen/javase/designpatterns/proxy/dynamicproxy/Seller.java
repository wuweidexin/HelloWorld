package com.chen.javase.designpatterns.proxy.dynamicproxy;


public class Seller implements SellHouse{

    @Override
    public void sellhouse() {
        System.out.println("签订合同");
        System.out.println("卖出房子");
        System.out.println("收取放款");
    }

}
