package com.chen.zk.base;

import java.io.IOException;

import org.apache.zookeeper.ZooKeeper;

/**
 * @author chen
 */
public class CreateSession {
	public static ZooKeeper zooKeeper;
	public static void main(String[] args) {
		try {
			//创建连接，要实现Watcher来进行监控节点
//			zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new MyWatcher());
			zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new AsynWatcher());
			System.out.println(zooKeeper.getState());
			Thread.sleep(50000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
