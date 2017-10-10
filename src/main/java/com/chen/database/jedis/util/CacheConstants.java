package com.chen.database.jedis.util;


public class CacheConstants {
	public final static String REDIS_URL = PropertiesUtils.getInstance().getProperty("redis.host");
	
	public final static String REDIS_PASSWORD = PropertiesUtils.getInstance().getProperty("redis.password");
	
	public final static int REDIS_PORT = Integer.parseInt(PropertiesUtils.getInstance().getProperty("redis.port"));
	public final static int REDIS_TIMEOUT_IN_MILLIS = Integer.parseInt(PropertiesUtils.getInstance().getProperty("redis.timeout.millis","5000"));
	
	public final static int REDIS_POOL_MAXACTVE = Integer.parseInt(PropertiesUtils.getInstance().getProperty("redis.pool.maxActive","500"));
	//分布式锁
	public static final String REDIS_LOCK_KEY = "opensys:lock:Redis:";
}
