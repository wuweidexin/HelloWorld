package com.chen.javase.designpatterns.proxy.staticproxy;

/**
 * @ClassName: Buyer 
 * @Description: 买房者，想要买房
 * @author junquan_chen 
 * @date 2017年11月1日 下午12:40:30
 */
public class Buyer {
    public static void main(String[] args) {
        SellHouse sell = new Intermediary();
        sell.sellhouse();
        System.out.println("买家买到了卖家卖 的房子");
    }

}
