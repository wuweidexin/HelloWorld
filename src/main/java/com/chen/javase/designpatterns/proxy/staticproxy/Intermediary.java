package com.chen.javase.designpatterns.proxy.staticproxy;
/**
 * @ClassName: Intermediary 
 * @Description: 中介公司类，专门做房产中介
 * @author junquan_chen 
 * @date 2017年11月1日 下午12:39:49
 */
public class Intermediary extends SellHouse{
    Seller seller = null;
    @Override
    public void sellhouse() {
       System.out.println("见买家，谈好价格");
       if(seller == null) {
           seller = new Seller();
       }
       seller.sellhouse();
       getIntermediateFee();
    }
    /**
     * @Title: getIntermediateFee 
     * @Description: 收取中介费用
     * @param     设定文件 
     * @return void    返回类型 
     * @throws
     */
    private void getIntermediateFee() {
        System.out.println("收取中介费用5个点");
    }

}
