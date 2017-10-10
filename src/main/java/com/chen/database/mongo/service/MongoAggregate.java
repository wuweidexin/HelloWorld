package com.chen.database.mongo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chen.database.mongo.model.ActiveStat;
import com.chen.database.mongo.util.MongoUtil;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MongoAggregate {
	public static void main(String[] args) throws Exception {
//		testFind();
//		testAggregateOneGroup();
//		testAggregateTowGroup();
//		testAggregateGroup();
//		testAggregateGroupTStat();
//		testAggregateGroupOutNotExit();
		testCount();
	
	}
	/**
	 * 通过getDB来获取数据库进行操作
	 */
	public static void testFind(){
		MongoClient mc = MongoUtil.getClient(false);
		DB db = mc.getDB("logstat");
		Set<String> name = db.getCollectionNames();
		for(String tempname : name) {
			System.out.format("name = %s\n", tempname);
		}
		DBCollection loginout = db.getCollection("T_LoginOut");
		DBObject dbo = new BasicDBObject("_id", "577dd2e384ae67c1f7c798eb");
		DBCursor dbc = loginout.find(dbo);
		if(dbc.hasNext()) {
			DBObject re = dbc.next();
			for(String key : re.keySet()) {
				System.out.format("key=%s, value=%s \n", key, re.get(key));
			}
		} else {
			System.out.println("没有数据");
		}
		mc.close();
	}
	/**
	 * 使用的是老版本的API, 单一分组
	 * mongo drive 2.2
	 * @throws Exception
	 */
	public static void testAggregateOneGroup() throws Exception{

		MongoClient mc = MongoUtil.getClient(false);
		DB db = mc.getDB("logstat");
		//首先通过去重进行分组
		DBCollection loginout = db.getCollection("T_LoginOut");
		
		DBObject gf = new BasicDBObject("userId", "$userId");
		gf.put("networkId", "$networkId");
		DBObject groupUserFields = new BasicDBObject("_id", gf);
		groupUserFields.put("time", new BasicDBObject("$max", "$time"));
		groupUserFields.put("count", new BasicDBObject("$sum", 1));
		DBObject group = new BasicDBObject("$group", groupUserFields);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//2016-07-07 14:12:00 = 2016-07-07T06:12:00.000Z
		//2016-07-07T06:03:12.80Z
		Date from = sdf.parse("2016-07-07 14:02:00");
		Date to = sdf.parse("2016-07-07 14:05:00");
		
		BasicDBObject query = new BasicDBObject();
		query.put("type", "IN");
		if(null != from && null != to) {
			BasicDBObject dates = new BasicDBObject("$gte", from);
			dates.put("$lte", to);
			query.put("time", dates);
		}
		BasicDBObject match = new BasicDBObject("$match", query);
		
		/* query Group result */
		AggregationOutput output = null;
		//老的mongo支持的
		output = loginout.aggregate(match, group);
		
		Iterable<DBObject> itable = output.results();
		Iterator<DBObject> ita = itable.iterator();
		JSONArray ar = new JSONArray();
		while(ita.hasNext()) {
			DBObject dbo = (DBObject) ita.next();
//			JSONObject json = new JSONObject();
////	        json.put("count", dbo.get("count"));
//	        DBObject gjson = (DBObject)dbo.get("_id");
//	        json.put("userId", gjson.get("userId"));
//	        json.put("networkId", gjson.get("networkId"));
//	        Date date = (Date) dbo.get("time");
//	        json.put("time", date.getTime());
//	        ar.add(json);
			System.out.println(dbo);
		}
       System.out.println(ar.toString());
       mc.close();
	}
	
	/**
	 * 分组查询 多分组
	 * mongo drive 2.2
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	public static void testAggregateTowGroup() throws ParseException{

		MongoClient mc = MongoUtil.getClient(false);
		DB db = mc.getDB("logstat");
		//首先通过去重进行分组
		DBCollection loginout = db.getCollection("T_LoginOut");
		
		DBObject gf = new BasicDBObject("userId", "$userId");
		gf.put("networkId", "$networkId");
		DBObject groupUserFields = new BasicDBObject("_id", gf);
		groupUserFields.put("time", new BasicDBObject("$max", "$time"));
		DBObject group = new BasicDBObject("$group", groupUserFields);
		
		
		DBObject nwdbo = new BasicDBObject("networkId", "$_id.networkId");
		nwdbo.put("userId", "$_id.userId");
		nwdbo.put("time", "$time");
		DBObject nwgdbo = new BasicDBObject("_id", nwdbo);
		nwgdbo.put("count", new BasicDBObject("$sum", 1));
		DBObject nwg = new BasicDBObject("$group", nwgdbo);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//2016-07-07 14:12:00 = 2016-07-07T06:12:00.000Z
		//2016-07-07T06:03:12.80Z
		Date from = sdf.parse("2016-07-07 14:02:00");
		Date to = sdf.parse("2016-07-07 14:05:00");
		
		BasicDBObject query = new BasicDBObject();
		query.put("type", "IN");
		if(null != from && null != to) {
			BasicDBObject dates = new BasicDBObject("$gte", from);
			dates.put("$lte", to);
			query.put("time", dates);
		}
		BasicDBObject match = new BasicDBObject("$match", query);
		
		/* query Group result */
		AggregationOutput output = null;
		//老的mongo支持的
//		output = loginout.aggregate(match, group);
//		output = loginout.aggregate(match, group, nwg);
		output = loginout.aggregate(match, group, nwg);
		
		Iterable<DBObject> itable = output.results();
		Iterator<DBObject> ita = itable.iterator();
		JSONArray ar = new JSONArray();
		while(ita.hasNext()) {
			DBObject dbo = (DBObject) ita.next();
//			JSONObject json = new JSONObject();
////	        json.put("count", dbo.get("count"));
//	        DBObject gjson = (DBObject)dbo.get("_id");
//	        json.put("userId", gjson.get("userId"));
//	        json.put("networkId", gjson.get("networkId"));
//	        Date date = (Date) dbo.get("time");
//	        json.put("time", date.getTime());
//	        ar.add(json);
			System.out.println(dbo);
		}
       System.out.println(ar.toString());
       mc.close();
	}
	
	/**
	 * 分组查询 多分组
	 * mongo drive 3.2
	 * @throws ParseException
	 */
	public static void testNewAggregateTowGroup() throws ParseException{

		MongoClient mc = MongoUtil.getClient(false);
		DB db = mc.getDB("logstat");
		//首先通过去重进行分组
		DBCollection loginout = db.getCollection("T_LoginOut");
		
		DBObject gf = new BasicDBObject("userId", "$userId");
		gf.put("networkId", "$networkId");
		DBObject groupUserFields = new BasicDBObject("_id", gf);
		groupUserFields.put("time", new BasicDBObject("$max", "$time"));
		DBObject group = new BasicDBObject("$group", groupUserFields);
		
		
		DBObject nwdbo = new BasicDBObject("networkId", "$networkId");
		nwdbo.put("userId", "$userId");
		DBObject nwg = new BasicDBObject("_id", nwdbo);
		nwg.put("count", new BasicDBObject("$sum", 1));
		
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//2016-07-07 14:12:00 = 2016-07-07T06:12:00.000Z
		//2016-07-07T06:03:12.80Z
		Date from = sdf.parse("2016-07-07 14:02:00");
		Date to = sdf.parse("2016-07-07 14:05:00");
		
		BasicDBObject query = new BasicDBObject();
		query.put("type", "IN");
		if(null != from && null != to) {
			BasicDBObject dates = new BasicDBObject("$gte", from);
			dates.put("$lte", to);
			query.put("time", dates);
		}
		BasicDBObject match = new BasicDBObject("$match", query);
		
		/* query Group result */
		AggregationOutput output = null;
		//read a specified number fo datta records
		List<DBObject> list = new ArrayList<DBObject>();
		list.add(match);
		list.add(group);
		//mongo3.2新支持
		output = loginout.aggregate(list);
		
		Iterable<DBObject> itable = output.results();
		Iterator<DBObject> ita = itable.iterator();
		JSONArray ar = new JSONArray();
		while(ita.hasNext()) {
			DBObject dbo = (DBObject) ita.next();
//			JSONObject json = new JSONObject();
////	        json.put("count", dbo.get("count"));
//	        DBObject gjson = (DBObject)dbo.get("_id");
//	        json.put("userId", gjson.get("userId"));
//	        json.put("networkId", gjson.get("networkId"));
//	        Date date = (Date) dbo.get("time");
//	        json.put("time", date.getTime());
//	        ar.add(json);
			System.out.println(dbo);
		}
       System.out.println(ar.toString());
       mc.close();
	}
	
	public static void testAggregateGroup(){
		MongoClient mc = MongoUtil.getClient(false);
		DB db = mc.getDB("test");
		//首先通过去重进行分组
		DBCollection loginout = db.getCollection("T_UserLogin");
		
		DBObject gf = new BasicDBObject("userId", "$userId");
		gf.put("networkId", "$networkId");
		DBObject groupUserFields = new BasicDBObject("_id", gf);
		groupUserFields.put("msgCounts", new BasicDBObject("$sum", "$msgCount"));
		groupUserFields.put("time", new BasicDBObject("$max", "$createTime"));
		DBObject group = new BasicDBObject("$group", groupUserFields);
		/* query Group result */
		AggregationOutput output = null;
		//read a specified number fo datta records
		List<DBObject> list = new ArrayList<DBObject>();
		list.add(group);
		//mongo3.2新支持
		output = loginout.aggregate(list);
		Iterable<DBObject> itable = output.results();
		Iterator<DBObject> ita = itable.iterator();
		JSONArray ar = new JSONArray();
		while(ita.hasNext()) {
			DBObject dbo = (DBObject) ita.next();
//			JSONObject json = new JSONObject();
////	        json.put("count", dbo.get("count"));
//	        DBObject gjson = (DBObject)dbo.get("_id");
//	        json.put("userId", gjson.get("userId"));
//	        json.put("networkId", gjson.get("networkId"));
//	        Date date = (Date) dbo.get("time");
//	        json.put("time", date.getTime());
//	        ar.add(json);
			System.out.println(dbo);
		}
       System.out.println(ar.toString());
       mc.close();
	}

	public static void testAggregateGroupTStat1(){
		MongoClient mc = MongoUtil.getClient(false);
		DB db = mc.getDB("logstat");
		//首先通过去重进行分组
		DBCollection loginout = db.getCollection("T_ActiveStat");
		
		System.out.println(loginout.count());
	}
	
	public static void testAggregateGroupTStat(){
		String networkId = "101";
		int start = 15,limit = 15;
		String order = "sends";
		Date endDate = new Date();
		Date startDate = null;
		Calendar fromc = Calendar.getInstance();
		fromc.add(Calendar.DAY_OF_MONTH, -9);
		startDate = fromc.getTime();
		
		MongoClient mc = MongoUtil.getClient(false);
		DB db = mc.getDB("logstat");
		//首先通过去重进行分组
		DBCollection loginout = db.getCollection("T_ActiveStat");
		
		DBObject gf = new BasicDBObject("userId", "$userId");
		gf.put("userName", "$userName");
		gf.put("account", "$account");
		gf.put("dept", "$dept");
		DBObject groupUserFields = new BasicDBObject("_id", gf);
		groupUserFields.put("sends", new BasicDBObject("$sum", "$sends"));
		groupUserFields.put("logins", new BasicDBObject("$sum", "$logins"));
		groupUserFields.put("likes", new BasicDBObject("$sum", "$likes"));
		groupUserFields.put("belikes", new BasicDBObject("$sum", "$belikes"));
		groupUserFields.put("replys", new BasicDBObject("$sum", "$replys"));
		groupUserFields.put("bereplys", new BasicDBObject("$sum", "$bereplys"));
		groupUserFields.put("msgCounts", new BasicDBObject("$sum", "$msgCounts"));
		groupUserFields.put("lastLoginTime", new BasicDBObject("$max", "$lastLoginTime"));
		DBObject group = new BasicDBObject("$group", groupUserFields);
		BasicDBObject query = new BasicDBObject();
		query.put("networkId", networkId);
		BasicDBObject dates = new BasicDBObject("$gte", startDate);
		dates.put("$lte", endDate);
		query.put("createTime", dates);
		
		BasicDBObject match = new BasicDBObject("$match", query);
		int sint = 1;
		if(order.contains("-")) {
			sint = -1;
			order = order.substring(1);
		}
		DBObject ord = new BasicDBObject(order, sint);
		ord.put("_id.userId", 1);
		DBObject ordery = new BasicDBObject("$sort", ord);
		AggregationOutput output = null;
		if(start != -1 && limit != -1){
			DBObject limitDb = new BasicDBObject("$limit", limit);
			DBObject skipDb = new BasicDBObject("$skip", start);
			output = loginout.aggregate(match, group, ordery, skipDb, limitDb);
		} else {
			output = loginout.aggregate(match, group, ordery);
		}
		
		Iterable<DBObject> cr = output.results();
		List<DBObject> crList = (List<DBObject>) cr;
		List<ActiveStat> list = new ArrayList<ActiveStat>(crList.size());
		List<Map<String, Object>> account = new ArrayList<Map<String, Object>>(crList.size());
		if (crList != null) {
			for (DBObject key : crList) {
				if (key != null) {
					ActiveStat as = new ActiveStat();
					DBObject jsonIdData = (DBObject) key.get("_id");
					as.setAccount((String)jsonIdData.get("account"));
					as.setUserId((String)jsonIdData.get("userId"));
					as.setUserName((String)jsonIdData.get("userName"));
					as.setDept((String)jsonIdData.get("dept"));
					Object sobj = key.get("sends");
					as.setSends(Long.parseLong(sobj.toString()));
					Object lobj = key.get("belikes");
					as.setBelikes(Long.parseLong(lobj.toString()));
					Object leobj = key.get("likes");
					as.setLikes(Long.parseLong(leobj.toString()));
					Object llobj = key.get("logins");
					as.setLogins(Long.parseLong(llobj.toString()));
					Object robj = key.get("replys");
					as.setReplys(Long.parseLong(robj.toString()));
					Object brobj = key.get("bereplys");
					as.setBereplys(Long.parseLong(brobj.toString()));
					Object mcobj = key.get("msgCounts");
					as.setMsgCounts(Long.parseLong(mcobj.toString()));
					as.setLastLoginTime((Date)key.get("lastLoginTime"));
					list.add(as);
					Map map = new HashMap<String, Object>();
					map.put("account", (String)jsonIdData.get("account"));
					map.put("userId", (String)jsonIdData.get("userId"));
					account.add(map);
				}
			}
			
			System.out.println(JSONArray.fromObject(account).toString());
		}
    
       mc.close();
	}
	
	public static void testAggregateGroupTStat2(){
		try {
			String networkId = "101";
			int start = 0,limit = 100;
			String order = "sends";
		
			Date startDate = null;
			Calendar fromc = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			fromc.setTime(sdf.parse("2016-09-11 00:00:00.000"));
			startDate = fromc.getTime();
			Calendar toc = Calendar.getInstance();
			toc.setTime(sdf.parse("2016-09-17 23:59:59.999"));
			Date endDate = new Date();//toc.getTime();
			
			
			MongoClient mc = MongoUtil.getClient(false);
			DB db = mc.getDB("logstat");
			//首先通过去重进行分组
			DBCollection loginout = db.getCollection("T_ActiveStat");
			
			DBObject gf = new BasicDBObject("userId", "$userId");
			gf.put("userName", "$userName");
			gf.put("account", "$account");
			gf.put("dept", "$dept");
			DBObject groupUserFields = new BasicDBObject("_id", gf);
			groupUserFields.put("sends", new BasicDBObject("$sum", "$sends"));
			groupUserFields.put("logins", new BasicDBObject("$sum", "$logins"));
			groupUserFields.put("likes", new BasicDBObject("$sum", "$likes"));
			groupUserFields.put("belikes", new BasicDBObject("$sum", "$belikes"));
			groupUserFields.put("replys", new BasicDBObject("$sum", "$replys"));
			groupUserFields.put("bereplys", new BasicDBObject("$sum", "$bereplys"));
			groupUserFields.put("msgCounts", new BasicDBObject("$sum", "$msgCounts"));
			groupUserFields.put("lastLoginTime", new BasicDBObject("$max", "$lastLoginTime"));
			DBObject group = new BasicDBObject("$group", groupUserFields);
			BasicDBObject query = new BasicDBObject();
			query.put("networkId", networkId);
			BasicDBObject dates = new BasicDBObject("$gte", startDate);
			dates.put("$lte", endDate);
			query.put("createTime", dates);
			
			BasicDBObject match = new BasicDBObject("$match", query);
			int sint = 1;
			if(order.contains("-")) {
				sint = -1;
				order = order.substring(1);
			}
			DBObject ord = new BasicDBObject(order, sint);
			DBObject ordery = new BasicDBObject("$sort", ord);
			AggregationOutput output = null;
			if(start != -1 && limit != -1){
				DBObject limitDb = new BasicDBObject("$limit", limit);
				DBObject skipDb = new BasicDBObject("$skip", start);
				output = loginout.aggregate(match, group, ordery, skipDb, limitDb);
			} else {
				output = loginout.aggregate(match, group, ordery);
			}
			
			Iterable<DBObject> cr = output.results();
			List<DBObject> crList = (List<DBObject>) cr;
			List<ActiveStat> list = new ArrayList<ActiveStat>(crList.size());
			List<Map<String, Object>> account = new ArrayList<Map<String, Object>>(crList.size());
			if (crList != null) {
				for (DBObject key : crList) {
					if (key != null) {
						ActiveStat as = new ActiveStat();
						DBObject jsonIdData = (DBObject) key.get("_id");
						as.setAccount((String)jsonIdData.get("account"));
						as.setUserId((String)jsonIdData.get("userId"));
						as.setUserName((String)jsonIdData.get("userName"));
						as.setDept((String)jsonIdData.get("dept"));
						Object sobj = key.get("sends");
						as.setSends(Long.parseLong(sobj.toString()));
						Object lobj = key.get("belikes");
						as.setBelikes(Long.parseLong(lobj.toString()));
						Object leobj = key.get("likes");
						as.setLikes(Long.parseLong(leobj.toString()));
						Object llobj = key.get("logins");
						as.setLogins(Long.parseLong(llobj.toString()));
						Object robj = key.get("replys");
						as.setReplys(Long.parseLong(robj.toString()));
						Object brobj = key.get("bereplys");
						as.setBereplys(Long.parseLong(brobj.toString()));
						Object mcobj = key.get("msgCounts");
						as.setMsgCounts(Long.parseLong(mcobj.toString()));
						as.setLastLoginTime((Date)key.get("lastLoginTime"));
						list.add(as);
						Map map = new HashMap<String, Object>();
						map.put("account", (String)jsonIdData.get("account"));
						map.put("userId", (String)jsonIdData.get("userId"));
						account.add(map);
					}
				}
				
				System.out.println(JSONArray.fromObject(account).toString());
			}
			
			mc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 */
	public static void testAggregateGroupOutNotExit(){
		MongoClient mc = MongoUtil.getClient(false);
		DB db = mc.getDB("test");
		//首先通过去重进行分组
		DBCollection loginout = db.getCollection("T_UserLogin");
		
		DBObject gf = new BasicDBObject("userId", "$userId");
		gf.put("networkId", "$networkId");
		DBObject groupUserFields = new BasicDBObject("_id", gf);
		groupUserFields.put("msgCounts", new BasicDBObject("$sum", "$msgCount"));
		groupUserFields.put("time", new BasicDBObject("$max", "$createTime"));
		DBObject group = new BasicDBObject("$group", groupUserFields);
		
		BasicDBObject query = new BasicDBObject();
		query.put("lastLoginTime", new BasicDBObject("$exists", true));
		BasicDBObject match = new BasicDBObject("$match", query);
		
		/* query Group result */
		AggregationOutput output = null;
		//read a specified number fo datta records
		List<DBObject> list = new ArrayList<DBObject>();
		list.add(match);
		list.add(group);
		//mongo3.2新支持
		output = loginout.aggregate(list);
		Iterable<DBObject> itable = output.results();
		Iterator<DBObject> ita = itable.iterator();
		JSONArray ar = new JSONArray();
		while(ita.hasNext()) {
			DBObject dbo = (DBObject) ita.next();
//			JSONObject json = new JSONObject();
////	        json.put("count", dbo.get("count"));
//	        DBObject gjson = (DBObject)dbo.get("_id");
//	        json.put("userId", gjson.get("userId"));
//	        json.put("networkId", gjson.get("networkId"));
//	        Date date = (Date) dbo.get("time");
//	        json.put("time", date.getTime());
//	        ar.add(json);
			System.out.println(dbo);
		}
       System.out.println(ar.toString());
       mc.close();
	}
	
	public static void testCount(){
		String openId = "f25f315e-3ff3-11e6-8825-005056ac6b20";
		String eid = "105";
		
		Calendar yc = Calendar.getInstance();
        yc.add(Calendar.DAY_OF_MONTH, -2);
        yc.set(Calendar.HOUR_OF_DAY, 24);
        yc.set(Calendar.MINUTE,0);
        yc.set(Calendar.SECOND,0); 
        Date from = yc.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(from));
        
        Calendar yc1 = Calendar.getInstance();
        yc1.add(Calendar.DAY_OF_MONTH, -1);
        yc1.set(Calendar.HOUR_OF_DAY, 23);
        yc1.set(Calendar.MINUTE,59);
        yc1.set(Calendar.SECOND,59); 
        Date to = yc1.getTime();
        System.out.println(sdf.format(to));
        
        MongoClient mc = MongoUtil.getClient(false);
		DB db = mc.getDB("logstat");
		//首先通过去重进行分组
		DBCollection xtMsg = db.getCollection("T_XTMessageRecord");
		
		DBObject gf = new BasicDBObject("openId", "$openId");
		DBObject groupUserFields = new BasicDBObject("_id", gf);
		groupUserFields.put("count", new BasicDBObject("$sum", 1));
		groupUserFields.put("sendTime", new BasicDBObject("$max", "$sendTime"));
		DBObject group = new BasicDBObject("$group", groupUserFields);
		
		BasicDBObject query = new BasicDBObject();
		query.put("sendTimeStr", sdf1.format(to));
		query.put("eid", eid);
		query.put("openId", openId);
		
		BasicDBObject match = new BasicDBObject("$match", query);
		AggregationOutput  output = xtMsg.aggregate(match, group);
		Iterable<DBObject> cr = output.results();
		List<DBObject> crList = (List<DBObject>) cr;
		for(DBObject obj : crList) {
			System.out.println(obj.get("count"));
			System.out.println(obj.get("sendTime"));
		}
	}
}
