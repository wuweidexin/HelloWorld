package com.chen.database.jedis.other;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chen.util.PropertiesUtil;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class XedisClient {

    private static final Logger logger = LoggerFactory.getLogger(XedisClient.class);
    private static Map<String, InnerXedisClient> xedisClientMap = new HashMap<String, InnerXedisClient>();
    private static Lock lock = new ReentrantLock();
    public final static String CRLF = "\r\n";
    
    private static class InnerXedisClient {
        private String type;
        private BlockingQueue<Xedis> pool;
        private int maxConn = 5000;
        private AtomicInteger xedisCount = new AtomicInteger();
        private List<XshardInfo> shards;

        InnerXedisClient(String type) {
            this.type = type;

            shards = new ArrayList<XshardInfo>(1);
            String url = XedisClient.class.getResource("/").getPath();
            Properties properties = PropertiesUtil.loadProperties("config.properties");

            String defaultKeyPref = "";
            String keyPref = defaultKeyPref;
            if (StringUtils.isNotEmpty(type)) {
                keyPref = defaultKeyPref + type + ".";
            }

            String host = properties.getProperty(keyPref + "redis.host", "");
            if (StringUtils.isEmpty(host)) {
                keyPref = defaultKeyPref;
                host = properties.getProperty(keyPref + "redis.host", "127.0.0.1");
            }

            maxConn = Integer.parseInt(properties.getProperty(keyPref + "redis.pool", "5000"));
            pool = new LinkedBlockingQueue<Xedis>(maxConn);

            XshardInfo info = new XshardInfo(host, Integer.parseInt(properties.getProperty(keyPref + "redis.port", "6379")), Integer.parseInt(properties.getProperty(keyPref + "redis.timeout", "2000")));// 211.151.125.147   //172.20.192.134 //192.168.204.66//172.20.192.181
            info.name = "";
            info.password = properties.getProperty(keyPref + "redis.password", "");
            shards.add(info);
            logger.info(type + " Redis Info: {}", info);
        }

        String getInfo() {
            if (shards.size() > 0) {
                XshardInfo xshardInfo = shards.get(0);
                return xshardInfo.getHost() + ":" + xshardInfo.getPort();
            }
            return "";
        }

        String getType() {
            return type;
        }

        Xedis getClient() {
            long begin = System.currentTimeMillis();
            boolean createNew = false;
            try {
                Xedis xedis;
                try {
                    int count = xedisCount.get();
                    int l = count / 10;
                    if (l < 20 && count < maxConn) {
                        xedis = pool.poll();
                    } else if (l < 100 && count < maxConn) {
                        xedis = pool.poll(l * 2, TimeUnit.MILLISECONDS);
                    } else if (count < maxConn - 1) {
                        xedis = pool.poll(500L, TimeUnit.MILLISECONDS);
                    } else {
                        xedis = pool.take();
                    }
                } catch (InterruptedException ignore) {
                    xedis = null;
                }
                if (xedis == null) {
                    createNew = true;
                    xedis = new Xedis(getType(), shards);
                    xedisCount.incrementAndGet();
                }
                return xedis;
            } finally {
                long t = System.currentTimeMillis() - begin;
                if (t > 20) {
                    String s = "Xedis.getClient(cost:" + t + (createNew ? " create new client)" : ")");
                    if (!ThreadLocalLog.addLog(s)) {
                        LoggerFactory.getLogger("Slow").info(s);
                    }
                }
            }
        }

        void returnClient(Xedis j) {
            if (j == null) return;
            boolean closeClient = false;
            long begin = System.currentTimeMillis();
            if (xedisCount.get() + 100 > maxConn) {
                closeClient = true;
                j.disconnect();
                xedisCount.decrementAndGet();
            } else {
                pool.offer(j);
            }
            long t = System.currentTimeMillis() - begin;
            if (t > 20) {
                String s = "Xedis.returnClient(cost:" + t + (closeClient ? " close client)" : ")");
                if (!ThreadLocalLog.addLog(s)) {
                    LoggerFactory.getLogger("Slow").info(s);
                }
            }
        }

        public int getXedisCount() {
            return xedisCount.get();
        }

        public int getMaxConn() {
            return maxConn;
        }
    }

    private static InnerXedisClient getInnerXedisClient(String type) {
        if (type == null) type = "";
        InnerXedisClient xedisClient = xedisClientMap.get(type);
        if (xedisClient == null) {
            lock.lock();
            try {
                xedisClient = xedisClientMap.get(type);
                if (xedisClient == null) {
                    xedisClient = new InnerXedisClient(type);
                    xedisClientMap.put(type, xedisClient);
                }
            } finally {
                lock.unlock();
            }
        }
        return xedisClient;
    }

    static Xedis getClient(String type) {
        return getInnerXedisClient(type).getClient();
    }

    static void returnClient(Xedis j) {
        if (j == null) return;
        getInnerXedisClient(j.getType()).returnClient(j);
    }

    public static String getXedisCountInfo() {
        StringBuilder sb = new StringBuilder();
        for (InnerXedisClient xedisClient : xedisClientMap.values()) {
            if (StringUtils.isEmpty(xedisClient.getType())) {
                sb.append("<default>");
            } else {
                sb.append(xedisClient.getType());
            }
            sb.append("(").append(xedisClient.getInfo()).append(")");
            sb.append(":").append(xedisClient.getXedisCount()).append("/").append(xedisClient.getMaxConn()).append(CRLF);
        }
        return sb.toString();
    }

    public static int getDefaultXedisCount() {
        InnerXedisClient xedisClient = xedisClientMap.get("");
        if (xedisClient != null) return xedisClient.getXedisCount();
        return 0;
    }

    public static int getDefaultXedisMaxConn() {
        InnerXedisClient xedisClient = xedisClientMap.get("");
        if (xedisClient != null) return xedisClient.getMaxConn();
        return 0;
    }

    public static String genKey(Object... keys) {
        return StringUtils.join(keys, ":");
    }
}
