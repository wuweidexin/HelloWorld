package com.chen.database.jedis.pubsub;

import com.chen.database.jedis.util.JedisSentinelUtil;

public class PubChannel {
    
    public PubChannel() {
        // TODO Auto-generated constructor stub
    }
    
    public static void main(String[] args) {
        PubChannel p = new PubChannel();
        p.publishChannel();
    }

    private void publishChannel() {
        JedisSentinelUtil.publishChannel(ChannelConfig.ChannelKey, "今晚新闻联播，习大大来讲话");
    }

}
