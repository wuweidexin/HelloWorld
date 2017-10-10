package com.chen.database.jedis.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chen.database.jedis.dao.RedisDO;

import redis.clients.jedis.Jedis;

public class TestJedisService {
	Jedis jedis;
	static int kvModel = 1;
	static int kvHashModel = 2;
	
	
	public TestJedisService(){
		jedis = RedisDO.open("120.24.255.77", 6380, 100000, "icyz&123", 10000);
//		jedis = RedisDO.open("120.24.255.77", 6380, 100000);
	}
	public static void main(String[] args) {
		TestJedisService t = new TestJedisService();
		System.out.println("操作开始\n");
//		t.addData();
//		t.deleteDate("foo");
//		t.deleteDate("sose");
//		t.getData("sose", kvHashModel);
		t.testData();
		RedisDO.destroyPool();
		System.out.println("操作结束\n");
	}
	public void testData(){
		
		String pk = "TEST";
		//直接设置进去
		/*jedis.set(pk+":temp", "nihao");*/
		
		//在原来的key的value基础上添加值
		//jedis.append(pk+":temp", "hello");
		
		//一次添加多个值  key1,val1,key2,val2这样排列的字符串
		//jedis.mset(pk+":key1","val1",pk+":key2","val2");	
		
		//不错在则存储，存在不处理
		//jedis.setnx(pk+":key3", "加油中国");
		
		//具有有效期的存储数据
		//jedis.setex(pk+":exdata", 5, "只有5秒生命的数据");
		
		//jedis中的list数据
		String groupId = "G001";
		String msgId = "M001";
		String key = pk+":"+groupId+":"+msgId;
		/*jedis.rpush(key, "hello1");
		jedis.rpush(key, "hello2");
		jedis.rpush(key, "hello3");
		List<String> list = jedis.lrange(key, 0, -1);
		for(String item : list) {
			System.out.println(item);
		}*/
		//System.out.println("数组长度：" + jedis.llen(key));
		
		//排序 这种排序一般是带有score的情况
		//List<String> list = jedis.sort(key);
		//for(String item : list) {
		//	System.out.println(item);
		//}
		//返回指定
		String userId = "U002";
		String val = "do";
		jedis.hset(key, userId, val);
		//System.out.println(jedis.lrange(key, 0, 1));
		
	}
	
	
	public void addData(int model){
		try {
//			jedis.set("foo", "bar");
//			String foobar = jedis.get("foo");
			jedis.zadd("sose", 0, "car");
			jedis.zadd("sose", 0, "bike");
			Set<String> sose = jedis.zrange("sose", 0, -1);
			System.out.println("数据增加成功。\n");
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	
	public void deleteDate(String key){
		try {
			jedis.del(key);
			System.out.println("删除了key:" + key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getData(String key, int model) {
		try {
			boolean exit = jedis.exists(key);
			if(exit) {
				if(kvModel == model) {
					/*
					 * 直接获取key/value的形式
					 */
					String value = jedis.get(key);
					System.out.println("查询出来的结果是：" + value);
				} else if(kvHashModel == model) {
					System.out.println("结果为：\n");
//					Map<String, String> re = jedis.hgetAll(key);
//					for(String item : re.keySet()) {
//						System.out.println(re.get(item) + "\n");
//					}
					Set<String> re = jedis.hkeys(key);
					for(String v : re) {
						System.out.println(v + "\n");
					}
					
				} else {
					
				}
			} else {
				System.out.println("你要查找的key："+key+"不存在\n");
			}
			
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		
	}
	
}
