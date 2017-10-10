package com.chen.database.mongo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bson.Document;

import com.chen.database.mongo.util.MongoUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import net.sf.json.JSONObject;

public class MongoOperate {
	public static void main(String[] args) {
		MongoOperate mo = new MongoOperate();
		System.out.println("查询开始");
//		mo.updateUN();
		try {
//			getActiveStat();
//			mo.addData();
			mo.testBaseQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MongoUtil.close();
		System.out.println("链接关闭");
	}
	public void addData(){
		MongoClient mc = MongoUtil.getClient(false);
		MongoDatabase database =  mc.getDatabase("test");
		MongoCollection<Document> collection = database.getCollection("T_UserLogin");
		List<Document> list = createUserLoginDoc();
		collection.insertMany(list);
	}
	
	private List<Document> createUserLoginDoc(){
		String u1 = "18028752937";
		String u2 = "18028751812";
		String u3 = "18666930547";
		String nw1 = "101";
		String nw2 = "102";
		
		List<Document> list = new ArrayList<Document>(5);
		Document d1 = new Document();
		d1.put("_id", UUID.randomUUID().toString());
		d1.put("networkId", nw1);
		d1.put("userId", u1);
		d1.put("msgCount", 2);
		d1.put("lastLoginTime", new Date());
		d1.put("createTime", new Date());
		Document d11 = new Document();
		d11.put("_id", UUID.randomUUID().toString());
		d11.put("networkId", nw1);
		d11.put("userId", u1);
		d11.put("msgCount", 5);
		d11.put("createTime", new Date());
		
		
		Document d2 = new Document();
		d2.put("_id", UUID.randomUUID().toString());
		d2.put("networkId", nw1);
		d2.put("userId", u2);
		d2.put("msgCount", 5);
		d2.put("lastLoginTime", new Date());
		d2.put("createTime", new Date());
		Document d21 = new Document();
		d21.put("_id", UUID.randomUUID().toString());
		d21.put("networkId", nw1);
		d21.put("userId", u2);
		d21.put("msgCount", 5);
		d21.put("lastLoginTime", new Date());
		d21.put("createTime", new Date());
		
		Document d3 = new Document();
		d3.put("_id", UUID.randomUUID().toString());
		d3.put("networkId", nw2);
		d3.put("userId", u3);
		d3.put("createTime", new Date());
		
		list.add(d1);
		list.add(d11);
		list.add(d2);
		list.add(d21);
		list.add(d3);
		return list;
	}
	
	
	public void updateUN(){
		MongoClient mc = MongoUtil.getClient(false);
		MongoDatabase database =  mc.getDatabase("ossDev");
		MongoCollection<Document> collection = database.getCollection("T_UserNetwork");
		BasicDBObject db = new BasicDBObject();
		db.put("partnerType", 1);
		FindIterable<Document> result = collection.find(db);
		MongoCursor<Document> cursor = result.iterator();
		int i = 0;
		while(cursor.hasNext()) {
			
			Document doc = cursor.next();
			if(null != doc) {
				String id = doc.getString("_id");
				BasicDBObject tempdb = new BasicDBObject();
				tempdb.put("_id", id);
				doc.remove("_id");
				doc.put("partnerType", "1");
				Document upt = new Document("$set", doc);
				collection.updateOne(tempdb, upt);
				System.out.println("更新第"+(i++)+"用户id为 == " + id + " 的数据后为  "+JSONObject.fromObject(doc).toString());
			}
		}
	}
	
	public static void getActiveStat() throws Exception{
		MongoClient mc = MongoUtil.getClient(false);
		MongoDatabase database =  mc.getDatabase("logstat");
		MongoCollection<Document> collection = database.getCollection("T_LoginOut");
		BasicDBObject db = new BasicDBObject();
		db.put("networkId", "101");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//2016-07-07 14:12:00 = 2016-07-07T06:12:00.000Z
		//2016-07-07T06:03:12.80Z
		Date from = sdf.parse("2016-06-30 00:00:00");
		Date to = sdf.parse("2016-07-06 24:00:00");

		BasicDBObject dates = new BasicDBObject("$gte", from);
		dates.put("$lte", to);
		db.put("time", dates);
		
		
		FindIterable<Document> result = collection.find(db);
		MongoCursor<Document> cursor = result.iterator();
		int i = 0;
		Map<String, Object> filterMap = new HashMap<String, Object>();
		while(cursor.hasNext()) {
			Document doc = cursor.next();
			if(null != doc) {
				String userId = doc.getString("userId");
				if(!filterMap.containsKey(userId)) {
					filterMap.put(userId, doc.getDate("time"));
				}
			}
		}
		System.out.println("发现记录数是："+filterMap.size());
		for(String temp :filterMap.keySet()) {
			System.out.println(temp);
		}
	}
	
	public static void testBaseQuery(){
		try {
			String networkId = "119";
			String userId = "d30516a3-39a1-11e6-8825-005056ac6b20";
			String appId = "53685c4c0364667d32e403d8";
			
			MongoClient mc = MongoUtil.getClient(false);
			MongoDatabase database =  mc.getDatabase("logstat");
			MongoCollection<Document> collection = database.getCollection("T_LoginOut");
			
			BasicDBObject db = new BasicDBObject();
//			db.put("networkId", networkId);
//			db.put("userId", userId);
//			db.put("appId", appId);
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			//2016-07-07 14:12:00 = 2016-07-07T06:12:00.000Z
//			//2016-07-07T06:03:12.80Z
//			Date from = sdf.parse("2016-09-19 00:00:00");
//			Date to = sdf.parse("2016-09-20 24:00:00");
//
//			BasicDBObject dates = new BasicDBObject("$gte", from);
//			dates.put("$lte", to);
//			db.put("time", dates);
			db.put("_id", "57e1e10b84aec00825bae0fb");
			
			BasicDBObject sort = new BasicDBObject("time", -1);
			
			FindIterable<Document> result = collection.find(db).sort(sort).limit(1);
			MongoCursor<Document> cursor = result.iterator();
			int i = 0;
//			Map<String, Object> filterMap = new HashMap<String, Object>();
			while(cursor.hasNext()) {
				Document doc = cursor.next();
				if(null != doc) {
					userId = doc.getString("userId");
//					if(!filterMap.containsKey(userId)) {
//						filterMap.put(userId, doc.getDate("time"));
//					}
					System.out.println(JSONObject.fromObject(doc).toString());
				}
			}
//			System.out.println("发现记录数是："+filterMap.size());
//			for(String temp :filterMap.keySet()) {
//				System.out.println(temp);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		/*
		CriteriaQuery<LoginOut> query = super.getQuery();
		query.field("userId").equal(userId.toString());
		query.field("networkId").equal(nw.toString());
		if (appId != null) {
			query.field("appId").equal(appId);
		}
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, -1);
		query.field("time").greaterThanOrEq(c.getTime());
		query.field("time").lessThan(date);
		query.order("-time");
		query.limit(1);
		Results<LoginOut> result = super.getDAO().query(query);
		if(result.hasNext()) {
			CriteriaQuery<LoginOut> nquery = super.getQuery();
			nquery.field("_id").equal(result.next().getId());
			Updates up = this.getUpdates();
			up.set("time", date);
			super.getDAO().update(nquery, up);
		}*/
	}
}
