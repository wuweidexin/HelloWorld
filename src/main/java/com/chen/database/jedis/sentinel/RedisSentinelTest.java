package com.chen.database.jedis.sentinel;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class RedisSentinelTest {
    static JedisSentinelPool redisSentinelJedisPool = null;
    public static void main(String[] args) {
        RedisSentinelTest t = new RedisSentinelTest();
        String key = "S_STU";
        t.set(key, "我是哨兵二号");
        System.out.println(t.get(key));
    }
    
    public void set(String key, String value) {
        JedisSentinelPool pool = getJedisSentinelPool();
        Jedis jedis = pool.getResource();
        try {
            jedis.set(key, value);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
        }finally {
            if(pool!=null){
                pool.returnResource(jedis);
            }
        }
    }
    public String get(String key) {
        String val = null;
        JedisSentinelPool pool = getJedisSentinelPool();
        Jedis jedis = pool.getResource();
        try {
            val = jedis.get(key);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
        }finally {
            if(pool!=null){
                pool.returnResource(jedis);
            }
        }
        return val;
    }

    private static JedisSentinelPool getJedisSentinelPool() {
        if(null != redisSentinelJedisPool) return redisSentinelJedisPool;
        Set<String> sentinels = new HashSet<String>();
        String hostAndPort1 = "120.24.255.77:26380";
        String hostAndPort2 = "120.24.255.77:26381";
        String hostAndPort3 = "120.24.255.77:26382";
        sentinels.add(hostAndPort1);
        sentinels.add(hostAndPort2);
        sentinels.add(hostAndPort3);
        String masterName = "mymaster";
        String password = "123456q";
        redisSentinelJedisPool =  new JedisSentinelPool(masterName, sentinels, password);
        return redisSentinelJedisPool;
    }
}
