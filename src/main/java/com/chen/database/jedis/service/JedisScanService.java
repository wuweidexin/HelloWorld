package com.chen.database.jedis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.chen.database.jedis.dao.RedisDO;
import com.chen.database.jedis.other.WrapXedis;
import com.chen.database.jedis.other.WrapXedisClient;
import com.chen.database.jedis.other.XedisClient;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

/**
 * redis中通过迭代获取数据
 * @author chen
 * SCAN 命令用于迭代当前数据库中的数据库键。同时会返回新的游标
 * SSCAN 命令用于迭代集合键中的元素。
 * HSCAN 命令用于迭代哈希键中的键值对。
 * ZSCAN 命令用于迭代有序集合中的元素（包括元素成员和元素分值）
 */

public class JedisScanService {
	 public static final String SYSKEY = "00000";//系统分区

	//下面是迭代返回hash中的值的例子
	Jedis jedis;
	public JedisScanService() {
		jedis = RedisDO.open("127.0.0.1", 6380, 100000);
	}
	public static void main(String[] args) {
		JedisScanService js = new JedisScanService();
		js.scanByXedis();
	}

	public static String genKey(Object... keys) {
		if (keys.length > 0 && keys[0] == null) {
			keys[0] = SYSKEY;
		}
		return XedisClient.genKey(keys);
	}
	public void scanByXedis(){
		String cursor = "0";
		int limit = 500;
		String hgk = "h_group";
		String key = genKey(null, hgk);
        WrapXedis xedis = WrapXedisClient.myClient();
        ScanParams scanParams = new ScanParams();
        scanParams.count(limit);
        ScanResult<Entry<String, String>> sresult = xedis.hscan(key, cursor, scanParams);
        List<Entry<String, String>> result = sresult.getResult();
        for(Entry<String, String> item : result) {
        	System.out.println(item.getKey());
        	System.out.println(item.getValue());
        }
	}
	
	public void scanHash(){
		String hashcol = "00000:h_group";
		List<Entry<String, String>> result = new ArrayList<Entry<String, String>>();
		String cursor = "0";
		do{
			ScanParams params = new ScanParams();
			params.count(100);
//			params.match(hashcol);
			ScanResult<Entry<String, String>> tempre = jedis.hscan(hashcol, cursor, params);
			cursor = tempre.getStringCursor();
			List<Entry<String, String>> hre = tempre.getResult();
			if(hre.size() > 0) {
				result.addAll(hre);
			}
		} while(!cursor.equals("0"));
		
		System.out.println(result.size());
//		for(Entry<String, String> item : result) {
//			System.out.println(item.getKey());
//			System.out.println(item.getValue());
//		}
	}
}
