package com.chen.database.jedis.other;

/**
 * User: Janon
 * Date: 14-1-20 下午4:38
 */
public class WrapXedisClient {
    private static WrapXedis wrapXedis = new WrapXedis("");
    private static WrapXedis wrapQueueXedis = new WrapXedis("queue");
    private static WrapXedis wrapCacheXedis = new WrapXedis("cache");

    public static WrapXedis myClient() {
        if (wrapXedis == null)
            wrapXedis = new WrapXedis("");
        return wrapXedis;
    }

    public static WrapXedis myCacheClient() {
        if (wrapCacheXedis == null)
            wrapCacheXedis = new WrapXedis("cache");
        return wrapCacheXedis;
    }

    public static WrapXedis myQueueClient() {
        if (wrapQueueXedis == null)
            wrapQueueXedis = new WrapXedis("queue");
        return wrapQueueXedis;
    }

}
