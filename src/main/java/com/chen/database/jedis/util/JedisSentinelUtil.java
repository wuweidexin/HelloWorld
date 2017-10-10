package com.chen.database.jedis.util;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class JedisSentinelUtil {

    private static Logger logger = LoggerFactory.getLogger(JedisSentinelUtil.class);
    private static JedisSentinelPool redisSentinelJedisPool = null;
    public static int retryNum = 3;// jedis重连次数
    // 每个线程共享一个连接
    private static ThreadLocal<Jedis> connWrapper = new ThreadLocal<Jedis>();

    private static JedisSentinelPool getJedisSentinelPool() {
        if (null != redisSentinelJedisPool)
            return redisSentinelJedisPool;
        Set<String> sentinels = new HashSet<String>();
        String hostAndPort1 = "120.24.255.77:26380";
        String hostAndPort2 = "120.24.255.77:26381";
        String hostAndPort3 = "120.24.255.77:26382";
        sentinels.add(hostAndPort1);
        sentinels.add(hostAndPort2);
        sentinels.add(hostAndPort3);
        String masterName = "mymaster";
        String password = "123456q";
        redisSentinelJedisPool = new JedisSentinelPool(masterName, sentinels, password);
        return redisSentinelJedisPool;
    }

    private static Jedis jedis() {

        JedisSentinelPool pool = getJedisSentinelPool();

        Jedis jedis = null;
        try {

            jedis = pool.getResource();
        } catch (JedisConnectionException e) {

            if (jedis != null) {
                jedis.close();
                close(jedis);
            }
            logger.error("", e);
            return null;
        }
        return jedis;
    }

    /**
     * 返回一个线程共用一个redis连接
     * 
     * @return
     */
    public static Jedis myClient() {
        int count = 0;
        Jedis jedis = null;
        do {
            try {
                jedis = connWrapper.get();
                if (jedis == null) {
                    jedis = jedis();
                    connWrapper.set(jedis);
                }

            } catch (JedisConnectionException e) {
                // 出现链接异常的时候归还jedis
                if (jedis != null) {
                    jedis.close();
                    close(jedis);
                }
                logger.error("", e);
                try {
                    Thread.sleep(2000); // 休眠2秒
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        } while (jedis == null && count < retryNum);
        return jedis;
    }

    public static void close(Jedis redis) {

        try {
            if (redis != null) {
                getJedisSentinelPool().returnResource(redis);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        } finally {
            getJedisSentinelPool().returnBrokenResource(redis);
        }
    }
    
    
    public static void publishChannel(String key, String message) {
        Jedis jedis = JedisSentinelUtil.myClient();
        jedis.publish(key, message);
        JedisSentinelUtil.close(jedis);
    }
    
    public static void subscribeChannel(JedisPubSub jedisPubSub, String[] channels) {
        Jedis jedis = JedisSentinelUtil.myClient();
        jedis.subscribe(jedisPubSub, channels);
        JedisSentinelUtil.close(jedis);
    }
}
