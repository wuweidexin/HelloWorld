package com.chen.database.mongo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import com.chen.database.mongo.model.Config;
import com.chen.util.PropertiesUtil;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoUtil {
	private static MongoClient mc = null;
	
	static Config init(){
		Properties properties = PropertiesUtil.loadProperties("E:/lightapp/HelloWorld/src/main/resources/config.properties");
		
		String host = properties.getProperty("mongo.host");
		String port = properties.getProperty("mongo.port");
		String db = properties.getProperty("mongo.db");
		String userName = properties.getProperty("mongo.username");
		String password = properties.getProperty("mongo.password");
		int portint = Integer.valueOf(port);
		
		return new Config(host, portint, db, userName, password);
	}
	public static MongoClient getClient(boolean withAuth){
		if(mc != null) {
			return mc;
		} else {
			Config cfg = init();
			open(cfg, withAuth);
			return mc;
		}
	}
	
	//获取客户端
	public static void open(Config cfg, boolean withAuth){
		MongoCredential credential = MongoCredential.createCredential(cfg.getUsername(),
                cfg.getDb(),
                cfg.getPassword().toCharArray());
		if(withAuth) {
			List<MongoCredential> list = new ArrayList<MongoCredential>(1);
			list.add(credential);
			mc = new MongoClient(new ServerAddress(cfg.getHost()), list);
			System.out.format("host=%s, port=%s, db=%s, userName=%s, password=%s\n", 
			cfg.getHost(), cfg.getPort(), cfg.getUsername(),cfg.getPassword());
		} else {
			mc = new MongoClient(cfg.getHost(), cfg.getPort());
			System.out.format("host=%s, prot=%s\n", cfg.getHost(), cfg.getPort());
		}
	}
	public static void close() {
		if(null != mc) {
			mc.close();
		}
	} 
}
