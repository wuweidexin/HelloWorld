package com.chen.javaee.queue;

public interface IQueue {
	//初始化队列
	public void init() throws Exception;
	//停止队列
	public void stop();
	//发送队列消息
	public void sendMessage(String topic, String json);
	//订阅队列
	public void subscribe(String topic, QueueMessageListener listener);
	//对列大小
	public long size(String topic);
	
	public long qps(String topic);
	//删除某个主题
	public void delete(String topic);
	//延迟删除对列
	public void delayDelete(String topic, int seconds);
	//删除pub消息？
	public void delPubMessage(String topic, String json);
}
