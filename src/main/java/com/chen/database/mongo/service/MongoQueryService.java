package com.chen.database.mongo.service;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import com.chen.database.mongo.dao.MongoBo;
import com.chen.database.mongo.model.KVModel;
import com.chen.database.mongo.util.MongoUtil;
import com.chen.opensourceframework.word.WordOperation;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class MongoQueryService {
	public static void main(String[] args) {
		
		getJson();
		MongoUtil.close();
	}
	public static void getJson(){
		//get cursor
		
		MongoCursor<String> ita = MongoBo.getCollections();
		int i = 0;
		Map<String, List<KVModel>> map = new HashMap<String, List<KVModel>>();
		List<String> list = new ArrayList<String>(map.size());
		while(ita.hasNext()) {
			//table name
			String table = ita.next();
			list.add(table);
		}
		Collections.sort(list,Collator.getInstance(java.util.Locale.ENGLISH));
		Comparator<KVModel> con = new Comparator<KVModel>() {
			
			public int compare(KVModel o1, KVModel o2) {
				String k1 = o1.getKey().substring(0, 1);
				String k2 = o2.getKey().substring(0, 1);
				return k1.compareTo(k2);
			}
		};
		for(String table :list) {
			MongoCollection<Document> col = MongoBo.findTableFirstCol(table);
			FindIterable<Document> fita = col.find();
			Document myDoc = fita.first();
			if(myDoc != null && myDoc.size() > 0) {
				String json = myDoc.toJson();
				List<KVModel> clo = new ArrayList<KVModel>();
				for(Entry<String, Object> item : myDoc.entrySet()) {
					String key = item.getKey();
					if(StringUtils.isBlank(key)) {
						continue;
					}
					Object obj = item.getValue();
					String type = obj.getClass().getName();
					int ind = type.lastIndexOf(".");
					String typename = type.substring(ind+1);
					KVModel kv = new KVModel(key,typename);
					clo.add(kv);
				}
				Collections.sort(clo,con);
//				for(KVModel temp : clo) {
//					System.out.println(temp.getKey());
//				}
				map.put(table, clo);
			}
		}
		WordOperation.createWordNew(list, map);
		System.out.println(map.size());
	}

}
//目前系统有个问题，那就是验证授权没有设定，全他妈是免密码登陆的