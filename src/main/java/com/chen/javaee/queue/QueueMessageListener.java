package com.chen.javaee.queue;

import java.util.concurrent.Executor;

/**
 * 对列消息监听类
 * @author junquan_chen
 *
 */
public interface QueueMessageListener {
	/**
	 * 接收消息
	 * @param topic
	 * @param msg
	 */
	public void recieveMessage(String topic, String msg);
	/**
	 * 获取执行者
	 * @return
	 */
	public Executor getExecutor();
}	
