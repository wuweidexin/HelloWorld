package com.chen.database.jedis.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.util.*;

/**
 * Redis 客户端
 *
 * @author z
 */
public class Xedis extends Xsharded {
    private static final Logger logger = LoggerFactory.getLogger(Xedis.class);
    private String type;
    public Xedis(String type, List<XshardInfo> shards) {
        super(shards);
        this.type = type;
    }

    public void disconnect() {
        super.disconnect();
    }

    String getType() {
        return type;
    }

    public Transaction multi() {
        Jedis j = getShard(null);
        return j.multi();
    }

    public String set(String key, String value) {
        Jedis j = getShard(key);
        return j.set(key, value);
    }

    public String mset(String... keyvalue) {
        long begin = System.currentTimeMillis();
        try {
            Jedis j = getShard(keyvalue[0]);
            return j.mset(keyvalue);
        } finally {
            logger.debug("mset cost:{}", (System.currentTimeMillis() - begin));
        }
    }

    public String get(String key) {
        Jedis j = getShard(key);
        return j.get(key);
    }

    public List<String> mget(String... key) {
        long begin = System.currentTimeMillis();
        try {
            Jedis j = getShard(key[0]);
            return j.mget(key);
        } finally {
            logger.debug("mget cost:{}", (System.currentTimeMillis() - begin));
        }
    }

    public Boolean exists(String key) {
        Jedis j = getShard(key);
        return j.exists(key);
    }

    public String type(String key) {
        Jedis j = getShard(key);
        return j.type(key);
    }

    public Long expire(String key, int seconds) {
        Jedis j = getShard(key);
        return j.expire(key, seconds);
    }

    public Long ttl(String key) {
        Jedis j = getShard(key);
        return j.ttl(key);
    }

    public String setex(String key, int seconds, String value) {
        Jedis j = getShard(key);
        return j.setex(key, seconds, value);
    }

    public Long decrBy(String key, long integer) {
        Jedis j = getShard(key);
        return j.decrBy(key, integer);
    }

    public Long decr(String key) {
        Jedis j = getShard(key);
        return j.decr(key);
    }

    public Long incr(String key) {
        Jedis j = getShard(key);
        return j.incr(key);
    }

    public Long append(String key, String value) {
        Jedis j = getShard(key);
        return j.append(key, value);
    }

    public Long hset(String key, String field, String value) {
        Jedis j = getShard(key);
        return j.hset(key, field, value);
    }

    public String hget(String key, String field) {
        Jedis j = getShard(key);
        return j.hget(key, field);
    }

    public String hmset(String key, Map<String, String> hash) {
        Jedis j = getShard(key);
        return j.hmset(key, hash);
    }

    public List<String> hmget(String key, String... fields) {
        Jedis j = getShard(key);
        return j.hmget(key, fields);
    }

    public Long hincrBy(String key, String field, long value) {
        Jedis j = getShard(key);
        return j.hincrBy(key, field, value);
    }

    public Boolean hexists(String key, String field) {
        Jedis j = getShard(key);
        return j.hexists(key, field);
    }

    public Long del(String... key) {
        if (key == null || key.length == 0)
            return 0L;
        Jedis j = getShard(key[0]);
        return j.del(key);
    }

    public Long hdel(String key, String... fields) {
        Jedis j = getShard(key);
        return j.hdel(key, fields);
    }

    public Long hlen(String key) {
        Jedis j = getShard(key);
        return j.hlen(key);
    }

    public Set<String> hkeys(String key) {
        Jedis j = getShard(key);
        return j.hkeys(key);
    }

    public Set<String> keys(String key) {
        Jedis j = getShard(key);
        return j.keys(key);
    }

    public Long rpush(String key, String... strings) {
        Jedis j = getShard(key);
        return j.rpush(key, strings);
    }

    public Long llen(String key) {
        Jedis j = getShard(key);
        return j.llen(key);
    }

    public String lpop(String key) {
        Jedis j = getShard(key);
        return j.lpop(key);
    }

    public Long sadd(String key, String... members) {
        Jedis j = getShard(key);
        return j.sadd(key, members);
    }

    public Set<String> smembers(String key) {
        Jedis j = getShard(key);
        return j.smembers(key);
    }

    public Set<String> sinter(String... keys) {
        Jedis j = getShard(keys[0]);
        return j.sinter(keys);
    }

    public Long srem(String key, String... members) {
        Jedis j = getShard(key);
        return j.srem(key, members);
    }

    public Long scard(String key) {
        Jedis j = getShard(key);
        return j.scard(key);
    }

    public Boolean sismember(String key, String member) {
        Jedis j = getShard(key);
        return j.sismember(key, member);
    }

    public Long zadd(String key, double score, String member) {
        Jedis j = getShard(key);
        return j.zadd(key, score, member);
    }

    public Long zadd(String key, Map<String, Double> scoreMembers) {
        long begin = System.currentTimeMillis();
        try {
            Jedis j = getShard(key);
            return j.zadd(key, scoreMembers);
        } finally {
            logger.debug("zadd {} cost:{}", scoreMembers.size(), (System.currentTimeMillis() - begin));
        }
    }
    
    public Double zincrby(String key, double score, String member) {
        Jedis j = getShard(key);
        return j.zincrby(key, score, member);
    }

    public Long zunionstore(String dstkey, ZParams params, String... sets) {
        Jedis j = getShard(dstkey);
        return j.zunionstore(dstkey, params, sets);
    }

    public Set<String> zrange(String key, long start, long end) {
        Jedis j = getShard(key);
        return j.zrange(key, start, end);
    }

    public Long zrem(String key, String... members) {
        Jedis j = getShard(key);
        return j.zrem(key, members);
    }

    public Long zcard(String key) {
        long begin = System.currentTimeMillis();
        try {
            Jedis j = getShard(key);
            return j.zcard(key);
        } finally {
            logger.debug("zcard cost:{}", (System.currentTimeMillis() - begin));
        }
    }

    public Double zscore(String key, String member) {
        Jedis j = getShard(key);
        return j.zscore(key, member);
    }

    public Long zcount(String key, double min, double max) {
        Jedis j = getShard(key);
        return j.zcount(key, min, max);
    }

    public Long zcount(String key, String min, String max) {
        Jedis j = getShard(key);
        return j.zcount(key, min, max);
    }

    public Set<String> zrangeByScore(String key, String min, String max) {
        Jedis j = getShard(key);
        return j.zrangeByScore(key, min, max);
    }

    public Set<String> zrevrangeByScore(String key, String max, String min) {
        Jedis j = getShard(key);
        return j.zrevrangeByScore(key, max, min);
    }

    public Set<String> zrangeByScore(String key, double min, double max,
                                     int offset, int count) {
        Jedis j = getShard(key);
        return j.zrangeByScore(key, min, max, offset, count);
    }

    public Set<String> zrangeByScore(String key, String min, String max,
                                     int offset, int count) {
        Jedis j = getShard(key);
        return j.zrangeByScore(key, min, max, offset, count);
    }

    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max,
                                              int offset, int count) {
        Jedis j = getShard(key);
        return j.zrangeByScoreWithScores(key, min, max, offset, count);
    }

    public Set<String> zrevrangeByScore(String key, double max, double min,
                                        int offset, int count) {
        Jedis j = getShard(key);
        return j.zrevrangeByScore(key, max, min, offset, count);
    }

    public Set<String> zrevrangeByScore(String key, String max, String min,
                                        int offset, int count) {
        Jedis j = getShard(key);
        return j.zrevrangeByScore(key, max, min, offset, count);
    }

    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min,
                                                 int offset, int count) {
        Jedis j = getShard(key);
        return j.zrevrangeByScoreWithScores(key, max, min, offset, count);
    }

    public Long zremrangeByScore(String key, String start, String end) {
        Jedis j = getShard(key);
        return j.zremrangeByScore(key, start, end);
    }

    public String info() {
        Jedis j = getShard(null);
        return j.info();
    }

    public String info(String section) {
        Jedis j = getShard(null);
        return j.info(section);
    }

    public ScanResult<String> scan(String cursor) {
        Jedis j = getShard(null);
        return j.scan(cursor);
    }

    public ScanResult<String> scan(String cursor, ScanParams scanParams) {
        Jedis j = getShard(null);
        return j.scan(cursor, scanParams);
    }

    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
        Jedis j = getShard(key);
        return j.hscan(key, cursor);
    }

    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, ScanParams scanParams) {
        Jedis j = getShard(key);
        return j.hscan(key, cursor, scanParams);
    }

    public ScanResult<Tuple> zscan(String key, String cursor) {
        Jedis j = getShard(key);
        return j.zscan(key, cursor);
    }

    public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
        Jedis j = getShard(key);
        return j.zscan(key, cursor, params);
    }

    public Long dbSize() {
        Jedis j = getShard(null);
        return j.dbSize();
    }
}
