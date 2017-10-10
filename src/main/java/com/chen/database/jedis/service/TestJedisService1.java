package com.chen.database.jedis.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import com.chen.database.jedis.dao.RedisDO;

public class TestJedisService1 {
	static Jedis jedis;
	public TestJedisService1(){
		//jedis = RedisDO.open("192.168.22.209", 6380, 100000, null, 10000);
		jedis = RedisDO.open("192.168.22.209", 6379, 10000);
	}
	public static void main(String[] args) {
		TestJedisService1 t = new TestJedisService1();
		System.out.println("操作开始\n");
		String key = "10000:z_company_addressbook_opened";
		long count = jedis.zcount(key, 0, System.currentTimeMillis());
		
		System.out.println("一共数据是：" + count);
		Set<String> set = jedis.zrange(key, 0, 10);
		for(String val : set) {
			System.out.println(val);
		}
		//ZRANGE key start stop [WITHSCORES]
//		Set<Tuple> set = jedis.zrevrangeByScoreWithScores("00000:z_group_msg:00349052-f033-4978-9603-8119ab0fc9ca", "+inf", "-inf", 0, 1);
//		 Iterator<Tuple> iterator = set.iterator();
//         if (iterator.hasNext()) {
//             Tuple tuple = iterator.next();
//             System.out.println(tuple.getElement());
//         }
		
//		String key = BaseBO.genKey(null, Key.Z_GROUP_MSG,  uId);
//        WrapXedis xedis = WrapXedisClient.myClient();
//		String key = "00000:z_group_msg:02452438-8553-11e6-8825-005056ac6b20-XT-10001";
//        long size = jedis.zcard(key);
//        Set<String> msgIdSet = new HashSet<String>();
//        if (size > 0L) {
//            int pageSize = 500;
//            long page2 = size / pageSize + ((size % pageSize == 0 ? 0 : 1));
//            for (int i = 0; i < page2; i++) {
//                int begin = i * pageSize;
//                int end = (i + 1) * pageSize - 1;
//                Set<String> ids = jedis.zrevrange(key, begin, end);
////                Set<String> ids = jedis.zrevrangeByScore(key, begin, end);//xedis.zrevrangeByScore()
//                System.out.println(ids);
//            }
//        }
//		RedisDO.destroyPool();
//		System.out.println("操作结束\n");
	}
}
