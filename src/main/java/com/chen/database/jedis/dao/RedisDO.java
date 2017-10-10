package com.chen.database.jedis.dao;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisDO {
	public static Jedis jedis;
	public static JedisPool pool;

	public static Jedis open(String host, int port, int timeout, String password, int database) {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		JedisPool pool = new JedisPool(poolConfig, host, port, timeout, password, database);
		jedis = pool.getResource();
		return jedis;
	}
	
	public static Jedis open(String host, int port, int timeout) {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		pool = new JedisPool(poolConfig, host, port, timeout);
		jedis = pool.getResource();
		return jedis;
	}
	
	/**
	 * 关闭jedis
	 */
	public static void close(){
		jedis.close();
		jedis = null;
	}
	
	public static void destroyPool(){
		pool.destroy();
	}
}

