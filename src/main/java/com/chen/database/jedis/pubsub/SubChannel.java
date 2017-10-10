package com.chen.database.jedis.pubsub;

import com.chen.database.jedis.util.JedisSentinelUtil;

public class SubChannel {
    
    public SubChannel() {
    }
    public static void main(String[] args) {
        SubChannel s = new SubChannel();
        s.subscribeChannel();
    }
    
    private void subscribeChannel() {
        JedisSentinelUtil.subscribeChannel(new MessagePubSub(),  new String[]{ChannelConfig.ChannelKey});
    }
}
