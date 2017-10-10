package com.chen.database.jedis.other;

import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: Janon
 * Date: 14-1-20 下午4:35
 */
public class WrapXedis {
    private String type;

    WrapXedis(String type) {
        this.type = type;
    }

    public Xedis getClient() {
        return XedisClient.getClient(type);
    }

    public void returnClient(Xedis client) {
        XedisClient.returnClient(client);
    }

    public String set(String key, String value) {
        Xedis client = null;
        try {
            client = getClient();
            return client.set(key, value);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public String setex(String key, int seconds, String value) {
        Xedis client = null;
        try {
            client = getClient();
            return client.setex(key, seconds, value);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public String mset(String... keyvalue) {
        Xedis client = null;
        try {
            client = getClient();
            return client.mset(keyvalue);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public String get(String key) {
        long begin = System.currentTimeMillis();
        Xedis client = null;
        try {
            client = getClient();
            return client.get(key);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
            long t = System.currentTimeMillis() - begin;
            if (t > 10) {
                ThreadLocalLog.addLog("Xedis.get cost:" + t);
            }
        }
    }

    public List<String> mget(String... key) {
        long begin = System.currentTimeMillis();
        Xedis client = null;
        try {
            client = getClient();
            return client.mget(key);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
            long t = System.currentTimeMillis() - begin;
            if (t > 10) {
                ThreadLocalLog.addLog("Xedis.mget cost:" + t);
            }
        }
    }

    public Boolean exists(String key) {
        Xedis client = null;
        try {
            client = getClient();
            return client.exists(key);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long decr(String key) {
        Xedis client = null;
        try {
            client = getClient();
            return client.decr(key);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long decrBy(String key, long integer) {
        Xedis client = null;
        try {
            client = getClient();
            return client.decrBy(key, integer);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long incr(String key) {
        Xedis client = null;
        try {
            client = getClient();
            return client.incr(key);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long hsetQuiet(String key, String field, String value) {
        try {
            return hset(key, field, value);
        } catch (Throwable ignore) {
        }
        return null;
    }

    public Long hset(String key, String field, String value) {
        Xedis client = null;
        try {
            client = getClient();
            return client.hset(key, field, value);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public String hmset(String key, Map<String, String> hash) {
        Xedis client = null;
        try {
            client = getClient();
            return client.hmset(key, hash);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public String hgetQuiet(String key, String field) {
        try {
            return hget(key, field);
        } catch (Throwable ignore) {
        }
        return null;
    }

    public String hget(String key, String field) {
        long begin = System.currentTimeMillis();
        Xedis client = null;
        try {
            client = getClient();
            return client.hget(key, field);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
            long t = System.currentTimeMillis() - begin;
            if (t > 10) {
                ThreadLocalLog.addLog("Xedis.hget cost:" + t);
            }
        }
    }

    public Set<String> hkeys(String key) {
        Xedis client = null;
        try {
            client = getClient();
            return client.hkeys(key);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public List<String> hmgetQuiet(String key, String... fields) {
        try {
            return hmget(key, fields);
        } catch (Throwable ignore) {
        }
        return null;
    }

    public List<String> hmget(String key, String... fields) {
        long begin = System.currentTimeMillis();
        Xedis client = null;
        try {
            client = getClient();
            return client.hmget(key, fields);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
            long t = System.currentTimeMillis() - begin;
            if (t > 10) {
                ThreadLocalLog.addLog("Xedis.mget cost:" + t);
            }
        }
    }

    public Long hincrBy(String key, String field, long value) {
        Xedis client = null;
        try {
            client = getClient();
            return client.hincrBy(key, field, value);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long del(String... key) {
        if (key == null || key.length == 0)
            return 0L;
        Xedis client = null;
        try {
            client = getClient();
            return client.del(key);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long hdelQuiet(String key, String... fields) {
        try {
            return hdel(key, fields);
        } catch (Throwable ignore) {
        }
        return null;
    }

    public Boolean hexists(String key, String field) {
        Xedis client = null;
        try {
            client = getClient();
            return client.hexists(key, field);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long hdel(String key, String... fields) {
        Xedis client = null;
        try {
            client = getClient();
            return client.hdel(key, fields);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long hlenQuiet(String key) {
        try {
            return hlen(key);
        } catch (Throwable ignore) {
        }
        return 0L;
    }

    public Long hlen(String key) {
        Xedis client = null;
        try {
            client = getClient();
            return client.hlen(key);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Set<String> keys(String key) {
        Xedis client = null;
        try {
            client = getClient();
            return client.keys(key);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long sadd(String key, String... members) {
        Xedis client = null;
        try {
            client = getClient();
            return client.sadd(key, members);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long scard(String key) {
        Xedis client = null;
        try {
            client = getClient();
            return client.scard(key);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Set<String> smembers(String key) {
        Xedis client = null;
        try {
            client = getClient();
            return client.smembers(key);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Set<String> sinter(String... keys) {
        Xedis client = null;
        try {
            client = getClient();
            return client.sinter(keys);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long srem(String key, String... members) {
        if (members == null || members.length == 0) return 0L;
        Xedis client = null;
        try {
            client = getClient();
            return client.srem(key, members);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Boolean sismember(String key, String member) {
        Xedis client = null;
        try {
            client = getClient();
            return client.sismember(key, member);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long zadd(String key, double score, String member) {
        Xedis client = null;
        try {
            client = getClient();
            return client.zadd(key, score, member);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long zadd(String key, Map<String, Double> scoreMembers) {
        Xedis client = null;
        try {
            client = getClient();
            return client.zadd(key, scoreMembers);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }
    
    public Double zincrby(String key, Double score, String member) {
        Xedis client = null;
        try {
            client = getClient();
            return client.zincrby(key, score, member);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long zunionstore(String dstkey, ZParams params, String... sets) {
        Xedis client = null;
        try {
            client = getClient();
            return client.zunionstore(dstkey, params, sets);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Set<String> zrange(String key, long start, long end) {
        long begin = System.currentTimeMillis();
        Xedis client = null;
        try {
            client = getClient();
            return client.zrange(key, start, end);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
            long t = System.currentTimeMillis() - begin;
            if (t > 10) {
                ThreadLocalLog.addLog("Xedis.zrange cost:" + t);
            }
        }
    }

    public Long zrem(String key, String... members) {
        Xedis client = null;
        try {
            client = getClient();
            return client.zrem(key, members);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long zcard(String key) {
        Xedis client = null;
        try {
            client = getClient();
            return client.zcard(key);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Double zscore(String key, String member) {
        long begin = System.currentTimeMillis();
        Xedis client = null;
        try {
            client = getClient();
            return client.zscore(key, member);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
            long t = System.currentTimeMillis() - begin;
            if (t > 10) {
                ThreadLocalLog.addLog("Xedis.zscore cost:" + t);
            }
        }
    }

    public Long zcount(String key, double min, double max) {
        Xedis client = null;
        try {
            client = getClient();
            return client.zcount(key, min, max);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long zcount(String key, String min, String max) {
        Xedis client = null;
        try {
            client = getClient();
            return client.zcount(key, min, max);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Set<String> zrangeByScore(String key, String min, String max) {
        long begin = System.currentTimeMillis();
        Xedis client = null;
        try {
            client = getClient();
            return client.zrangeByScore(key, min, max);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
            long t = System.currentTimeMillis() - begin;
            if (t > 10) {
                ThreadLocalLog.addLog("Xedis.zrangeByScore cost:" + t);
            }
        }
    }

    public Set<String> zrevrangeByScore(String key, String max, String min) {
        Xedis client = null;
        try {
            client = getClient();
            return client.zrevrangeByScore(key, max, min);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Set<String> zrangeByScore(String key, String min, String max,
                                     int offset, int count) {
        Xedis client = null;
        try {
            client = getClient();
            return client.zrangeByScore(key, min, max, offset, count);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max,
                                              int offset, int count) {
        Xedis client = null;
        try {
            client = getClient();
            return client.zrangeByScoreWithScores(key, min, max, offset, count);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Set<String> zrevrangeByScore(String key, String max, String min,
                                        int offset, int count) {
        Xedis client = null;
        try {
            client = getClient();
            return client.zrevrangeByScore(key, max, min, offset, count);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min,
                                                 int offset, int count) {
        Xedis client = null;
        try {
            client = getClient();
            return client.zrevrangeByScoreWithScores(key, max, min, offset, count);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long zremrangeByScore(String key, String start, String end) {
        Xedis client = null;
        try {
            client = getClient();
            return client.zremrangeByScore(key, start, end);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long expire(String key, int seconds) {
        Xedis client = null;
        try {
            client = getClient();
            return client.expire(key, seconds);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long ttl(String key) {
        Xedis client = null;
        try {
            client = getClient();
            return client.ttl(key);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long rpush(String key, String... strings) {
        Xedis client = null;
        try {
            client = getClient();
            return client.rpush(key, strings);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public String lpop(String key) {
        Xedis client = null;
        try {
            client = getClient();
            return client.lpop(key);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long llen(String key) {
        Xedis client = null;
        try {
            client = getClient();
            return client.llen(key);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public String info() {
        Xedis client = null;
        try {
            client = getClient();
            return client.info();
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public String info(String section) {
        Xedis client = null;
        try {
            client = getClient();
            return client.info(section);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public ScanResult<String> scan(String cursor) {
        Xedis client = null;
        try {
            client = getClient();
            return client.scan(cursor);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public ScanResult<String> scan(String cursor, ScanParams scanParams) {
        Xedis client = null;
        try {
            client = getClient();
            return client.scan(cursor, scanParams);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
        Xedis client = null;
        try {
            client = getClient();
            return client.hscan(key, cursor);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, ScanParams scanParams) {
        Xedis client = null;
        try {
            client = getClient();
            return client.hscan(key, cursor, scanParams);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public ScanResult<Tuple> zscan(String key, String cursor) {
        Xedis client = null;
        try {
            client = getClient();
            return client.zscan(key, cursor);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
        Xedis client = null;
        try {
            client = getClient();
            return client.zscan(key, cursor, params);
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }

    public Long dbSize() {
        Xedis client = null;
        try {
            client = getClient();
            return client.dbSize();
        } catch (JedisConnectionException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } catch (ClassCastException ex) {
            if (client != null) client.setNeedGetNew(true);
            throw ex;
        } finally {
            returnClient(client);
        }
    }
}
