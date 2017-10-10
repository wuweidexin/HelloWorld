package com.chen.database.jedis.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import com.alibaba.fastjson.JSON;
import com.chen.database.jedis.other.WrapXedis;
import com.chen.database.jedis.other.WrapXedisClient;
import com.chen.database.jedis.other.XedisClient;

import redis.clients.jedis.Tuple;

public class RegionTest {
	public static final String SYSKEY = "00000";//系统分区
	public RegionBean getUserCurrentRegion(String userId, String groupId,
			long score) {
		String key = XedisClient.genKey(SYSKEY, Key.Z_USER_GROUP_RECORD, userId, groupId);
		WrapXedis xedis = WrapXedisClient.myClient();
		Set<String> regionSet = xedis.zrevrangeByScore(key,
				String.valueOf(score), "-inf", 0, 1);
		String[] regionArray = regionSet.toArray(new String[regionSet.size()]);
		if (regionArray.length >= 1) {
			RegionBean regionBean = JSON.parseObject(regionArray[0],
					RegionBean.class);
			if (regionBean.contains(score)) {
				return regionBean;
			}
		}
		return null;
	}
	/*
	 * 00000:z_user_group_record:
	 * 4f94e008-39ab-11e6-8825-005056ac6b20:
	 * 4f94e008-39ab-11e6-8825-005056ac6b20-XT-10001
	 */
	/**
	 * 00000:z_user_group_record:81b8aa35-39a0-11e6-8825-005056ac6b20:09e74ce5-d393-4fb8-b882-97891f604e2a
	 * 
	 * 00000:z_user_group_record:81b8aa35-39a0-11e6-8825-005056ac6b20:81b8aa35-39a0-11e6-8825-005056ac6b20-XT-10001
	 */
	
	public static void main(String[] args) {
		String userId = "81b8aa35-39a0-11e6-8825-005056ac6b20";
		String groupId = "81b8aa35-39a0-11e6-8825-005056ac6b20-XT-10001";
		long score = 1473700681150L;
		RegionTest t = new RegionTest();
//		RegionBean re = t.getUserCurrentRegion(userId, groupId, score);
//		System.out.println(re);
		
		t.backAllRegion(userId, groupId);
//		t.resetRegion(userId, groupId);
	}
	
	public void backAllRegion(String userId, String groupId){
		WrapXedis xedis = WrapXedisClient.myClient();
		String keyUserGroupRecord = XedisClient.genKey(SYSKEY, Key.Z_USER_GROUP_RECORD, userId, groupId);
		Set<Tuple> setTup = xedis.zrangeByScoreWithScores(keyUserGroupRecord, "-inf", "+inf", 0, 500);
		String keyBak = XedisClient.genKey(SYSKEY, Key.Z_USER_GROUP_RECORD+"_bak", userId, groupId);
		xedis.zremrangeByScore(keyBak, "-inf", "+inf");
		
		if(null != setTup) {
			 Iterator<Tuple> iterator = setTup.iterator();
	            while (iterator.hasNext()) {
	                Tuple tuple = iterator.next();
	                double score = Double.valueOf(tuple.getScore());
	                String val = tuple.getElement();
	                Map<String, Double> scoreMembers = new HashMap<String, Double>();
	                scoreMembers.put(val, score);
	                xedis.zadd(keyBak, scoreMembers);
	            }
		}
		xedis.zremrangeByScore(keyUserGroupRecord, "-inf", "+inf");
		String keyGroupMsg = XedisClient.genKey(SYSKEY, Key.Z_GROUP_MSG, groupId);
		Set<Tuple> setTupGroupMsg = xedis.zrangeByScoreWithScores(keyGroupMsg, "-inf", "+inf", 0, 1);
		Iterator<Tuple> iterator = setTupGroupMsg.iterator();
        while (iterator.hasNext()) {
            Tuple tuple = iterator.next();
            double score = Double.valueOf(tuple.getScore());
            String val = tuple.getElement();
            Map<String, Double> scoreMembers = new HashMap<String, Double>();
            scoreMembers.put("["+Double.valueOf(tuple.getScore()).longValue()+"]", score);
            xedis.zadd(keyUserGroupRecord, scoreMembers);
        }
	}
	private void resetRegion(String userId, String groupId) {
		WrapXedis xedis = WrapXedisClient.myClient();
		String key = XedisClient.genKey(SYSKEY, Key.Z_GROUP_MSG, groupId);
		Set<Tuple> setTup = xedis.zrangeByScoreWithScores(key, "-inf", "+inf", 0, 1);
		Iterator<Tuple> iterator = setTup.iterator();
        while (iterator.hasNext()) {
            Tuple tuple = iterator.next();
            double score = Double.valueOf(tuple.getScore());
            String val = tuple.getElement();
           
        }
	}
	
	public void test(){
		/*if(null == re) {
			GroupMessageData gm = t.getPrevMessageDataInGroup(groupId, score);
			long prevScore = 0;
			if(null != gm) {
				prevScore = gm.getScore();
			} else {
				GroupMessageData firstGm = t.getFirstMessageDataInGroup(groupId, score);
				prevScore = firstGm.getScore();
				System.out.println(JSON.toJSON(firstGm).toString());
			}
			RegionBean firstRegion = t.getFirstRegionBeanByRange(userId, groupId, score);
			System.out.println("Prev Msg Score="+prevScore);
			
			System.out.println("firstRegion ="+firstRegion);
		}*/
	}
	
	
	/**
	 * 获取某个组中指定一条消息
	 */
	public GroupMessageData getMessageDataInGroup(String groupId, String msgId) {
		Double score = null;//
		String key = XedisClient.genKey(SYSKEY, Key.Z_GROUP_MSG, groupId);
		WrapXedis xedis = WrapXedisClient.myClient();
		score = xedis.zscore(key, msgId);
		return new GroupMessageData(groupId, msgId, score.longValue());
	}
	
	/**
	 * 获取某个组中指定消息的上一条消息
	 */
	public GroupMessageData getPrevMessageDataInGroup(String groupId,String msgId) {
        GroupMessageData msgData = getMessageDataInGroup(groupId, msgId);
        if (msgData != null) {
            return getPrevMessageDataInGroup(groupId, msgData.getScore());
        }
        return null;
    }

	/**
	 * 获取组里之前的消息
	 * 
	 * @param groupId
	 * @param score
	 * @return
	 */
	public GroupMessageData getPrevMessageDataInGroup(String groupId, long score) {
		String key = XedisClient.genKey(SYSKEY, Key.Z_GROUP_MSG, groupId);
        WrapXedis xedis = WrapXedisClient.myClient();
        Set<Tuple> ids = xedis.zrevrangeByScoreWithScores(key, "(" + score, "-inf", 0, 1);
        return covertToGroupMessageData(groupId, ids);
    }
	
	/**
	 * 获取组里之前的消息
	 * 
	 * @param groupId
	 * @param score
	 * @return
	 */
	public GroupMessageData getFirstMessageDataInGroup(String groupId, long score) {
		String key = XedisClient.genKey(SYSKEY, Key.Z_GROUP_MSG, groupId);
        WrapXedis xedis = WrapXedisClient.myClient();
//        Set<Tuple> ids = xedis.zrevrangeByScoreWithScores(key, "(" + score, "-inf", 0, 1);
        Set<Tuple> ids = xedis.zrangeByScoreWithScores(key, "-inf", "+inf", 0, 1);
        return covertToGroupMessageData(groupId, ids);
    }
	
	private GroupMessageData covertToGroupMessageData(String groupId, Set<Tuple> ids) {
        if (ids != null) {
            Iterator<Tuple> iterator = ids.iterator();
            if (iterator.hasNext()) {
                Tuple tuple = iterator.next();
                return new GroupMessageData(groupId, tuple.getElement(), Double.valueOf(tuple.getScore()).longValue());
            }
        }
        return null;
    }
	
	public RegionBean getFirstRegionBeanByRange(String userId, String groupId, long score) {
		String key = XedisClient.genKey(SYSKEY, Key.Z_USER_GROUP_RECORD, userId, groupId);
		WrapXedis xedis = WrapXedisClient.myClient();
		Set<String> regionSet = xedis.zrange(key, 0, 1);
		for (String regionJson : regionSet) {
			RegionBean regionBean = JSON.parseObject(regionJson,
					RegionBean.class);
			if (!regionBean.contains(score)) {
				return regionBean;
			}
		}
		return null;
	}
	
	public RegionBean getFirstRegionBean(String userId, String groupId,
			long score) {
		RegionBean region = getUserCurrentRegion(userId, groupId, score);
		if (region == null) {
			region = getUserPrevRegion(userId, groupId, score);
		}
		return region;
	}
	
	public RegionBean getUserPrevRegion(String userId, String groupId,
			long score) {
		String key = XedisClient.genKey(null, Key.Z_USER_GROUP_RECORD, userId, groupId);
		WrapXedis xedis = WrapXedisClient.myClient();
		Set<String> regionSet = xedis.zrevrangeByScore(key,
				"(" + String.valueOf(score), "-inf", 0, 2);
		for (String regionJson : regionSet) {
			RegionBean regionBean = JSON.parseObject(regionJson,
					RegionBean.class);
			if (!regionBean.contains(score)) {
				return regionBean;
			}
		}
		return null;
	}
	
}
