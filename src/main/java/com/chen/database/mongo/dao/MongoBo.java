package com.chen.database.mongo.dao;


import org.bson.Document;

import com.chen.database.mongo.util.MongoUtil;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class MongoBo {
	public static MongoCollection<Document> findTableFirstCol(String tableName) {
		// TODO Auto-generated method stub
		MongoClient mc = MongoUtil.getClient(false);
		MongoDatabase database =  mc.getDatabase("ossDev");
		MongoCollection<Document> collection = database.getCollection(tableName);
		if(null != collection) {
			return collection;
		} 
		return null;
	}
	
	public static MongoCursor<String> getCollections(){
		MongoClient mc = MongoUtil.getClient(false);
		MongoDatabase database =  mc.getDatabase("ossDev");
		MongoIterable<String> mgoita = database.listCollectionNames();
		MongoCursor<String> cur= mgoita.iterator();
		return cur;
	}
}
