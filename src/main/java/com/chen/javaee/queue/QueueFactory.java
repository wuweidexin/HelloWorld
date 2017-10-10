package com.chen.javaee.queue;

public class QueueFactory {
	private static QueueFactory instance = null;
	private IQueue queue = null;
	
	private QueueFactory() {
		instance = this;
	}
	
	public static QueueFactory getInstance(){
		return instance;
	}
	
	private void init() throws Exception {
		queue = new RedisQueue();
	}
}
