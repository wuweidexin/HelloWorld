package com.chen.database.jedis.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.chen.database.jedis.other.WrapXedis;
import com.chen.database.jedis.other.WrapXedisClient;
import com.chen.database.jedis.other.XedisClient;

import net.sf.json.JSONObject;
import redis.clients.jedis.Tuple;


public class TestXedisZadd {
	
	public static final String SYSKEY = "00000";//系统分区

    public static String genKey(Object... keys) {
        if (keys.length > 0 && keys[0] == null) {
            keys[0] = SYSKEY;
        }
        return XedisClient.genKey(keys);
    }
	
	public static void main(String[] args) {
		TestXedisZadd t = new TestXedisZadd();
//		t.testGetZsetByzrevrangeByScore();
//		t.testZAdd();
		t.getAllEmailDomain();
	}
	public void testZAdd(){
	    String key = genKey(null, "testzset");
	    WrapXedis xedis =WrapXedisClient.myClient();
	    Map<String, Double> scoreMembers = new HashMap<String, Double>();
	    scoreMembers.put("a", 1.0);
	    xedis.zadd(key, scoreMembers);
	    
	    Map<String, Double> scoreMembers1 = new HashMap<String, Double>();
	    scoreMembers1.put("b", 1.0);
        xedis.zadd(key, scoreMembers1);
	}
	
	public void getGroupInfo(){
		String no = "";
		String userId = "";
		Date lastDate = new Date();
		Set<String> gidList = getGroupIdList(no, userId, lastDate);
		for(String groupId : gidList) {
			 String key = genKey(null, Key.H_GROUP);
			 WrapXedis xedis =WrapXedisClient.myClient();
			 String json = xedis.hget(key, groupId);
			 JSONObject jsonObj = JSONObject.fromObject(json);
			 String groupName = (String) jsonObj.get("groupName");
			 String createDate = (String) jsonObj.get("createDate");
			 System.out.println("组名：" + groupName + "  创建时间：" + createDate);
		}
	}
	
	/**
     * 获取某个组中最新一条消息
     */
    public String getNewestMessageDataInGroup(String groupId) {
        String key = genKey(null, Key.Z_GROUP_MSG, groupId);
        WrapXedis xedis = WrapXedisClient.myClient();
        Set<Tuple> ids = xedis.zrevrangeByScoreWithScores(key, "+inf", "-inf", 0, 1);
        if (ids != null) {
            Iterator<Tuple> iterator = ids.iterator();
            if (iterator.hasNext()) {
                Tuple tuple = iterator.next();
                GroupMessageData gm = new GroupMessageData(groupId, tuple.getElement(), Double.valueOf(tuple.getScore()).longValue());
                String msgkey = genKey(null, Key.H_MESSAGE);
                String msgJson = xedis.hget(msgkey, gm.getMsgId());
                System.out.println(msgJson);
            }
        }
		return null;
    }
    
    private long generateScore(Date date) {
        long time;
        if (date == null) time = System.currentTimeMillis();
        else time = date.getTime();
        return time;
    }
	
	public Set<String> getGroupIdList(String no, String userId, Date lastDate) {
		WrapXedis xedis = WrapXedisClient.myClient();
		String key = genKey(no, Key.Z_USER_GROUP_LIST, userId);
		Set<String> groupIdList = xedis.zrevrangeByScore(key, "+inf",
				lastDate == null ? "-inf" : "" + lastDate.getTime());
		return groupIdList;
	}
	public Set<String> getGroupList(String no, String userId, Date lastDate) {
		WrapXedis xedis = WrapXedisClient.myClient();
		String key = genKey(no, Key.Z_USER_GROUP_LIST, userId);
		Set<String> groupIdList = xedis.zrevrangeByScore(key, "+inf",
				lastDate == null ? "-inf" : "" + lastDate.getTime());
		return groupIdList;
	}
	 
	public Set<String> getAllEmailDomain() {
        WrapXedis xedis = WrapXedisClient.myClient();
        String key = genKey(null, "h_emaildomain:*");
        Set<String> hemaildomainSet = xedis.keys(key);
        System.out.println(hemaildomainSet);
        return null;
    }
	
	
	public void test(){
		WrapXedis xedis = WrapXedisClient.myClient();
		String key ="01000:test";
		Map<String, Double> scoreMembers = new HashMap<String, Double>();
		scoreMembers.put("zhongguo", 2121.1);
		xedis.zadd(key, scoreMembers);
	}

	public void testGetZsetByzrevrangeByScore() {
		String groupId = "9cd8eefe-7e46-11e6-8825-005056ac6b20-XT-10001";
		String key = genKey(null, Key.Z_GROUP_MSG, groupId);
		WrapXedis xedis = WrapXedisClient.myClient();
		Set<Tuple> ids = xedis.zrevrangeByScoreWithScores(key, "1486954898538", "1478242613904", 0, 500);
		if (ids != null) {
			Iterator<Tuple> iterator = ids.iterator();
			while (iterator.hasNext()) {
				Tuple tuple = iterator.next();
				System.out.println(tuple.getElement());
				String msgkey = genKey(null, Key.H_MESSAGE);
				String msgJson = xedis.hget(msgkey, tuple.getElement());
				System.out.println(msgJson);
			}
		}
	}
}
