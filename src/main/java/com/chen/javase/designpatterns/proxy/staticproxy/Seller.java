package com.chen.javase.designpatterns.proxy.staticproxy;

/**
 * @ClassName: Seller 
 * @Description: 买房者想要卖房，委托中介去卖房
 * @author junquan_chen 
 * @date 2017年11月1日 下午12:41:07
 */
public class Seller extends SellHouse{

    @Override
    public void sellhouse() {
        System.out.println("签订合同");
        System.out.println("卖出房子");
        System.out.println("收获现金");
    }

}
