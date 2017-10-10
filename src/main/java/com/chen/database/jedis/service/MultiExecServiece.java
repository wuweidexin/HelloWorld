package com.chen.database.jedis.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.chen.database.jedis.dao.RedisDO;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * @author chen
 * 验证redis的数据库事务
 */
public class MultiExecServiece {
	static Jedis jedis;
	String ip = "192.168.43.107";
	public MultiExecServiece(){
		//jedis = RedisDO.open("192.168.22.209", 6380, 100000, null, 10000);
		jedis = RedisDO.open(ip, 6379, 10000);
	}
	
	String key = "h_user";
	
	public void tran(){
		try {
			//开启事务
			Pipeline pl = jedis.pipelined();
			pl.multi();
			pl.hset(key, String.valueOf(System.currentTimeMillis()),
					"hehhehhe");
			pl.incr("s_user_count");
			pl.incr("s_user_count");
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			pl.incr("s_user_count");
			pl.exec();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RedisDO.destroyPool();
		}
		
	}
	
	public void testMutiExecMethod(){
		
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i = 0; i < 3; i ++) {
			exec.execute(new Counter());
		}
	}
	
	public static void main(String[] args) {
		System.out.println("执行开始");
		MultiExecServiece me = new MultiExecServiece();
//		me.testMutiExecMethod();
//		me.testHset();
		me.tran();
		System.out.println("执行结束");
	}
	private void testHset() {
		jedis.hset(key, String.valueOf(System.currentTimeMillis()),
				"hehhehhe");
		RedisDO.destroyPool();
		
	}
	class Counter implements Runnable {
		@Override
		public void run() {
			tran();
			System.out.println("线程执行中。。。");
		}
	}
	
	class Person {
		private String id;
		private String name;
		public Person() {
		}
		public Person(String id, String name) {
			this.id = id;
			this.name = name;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
