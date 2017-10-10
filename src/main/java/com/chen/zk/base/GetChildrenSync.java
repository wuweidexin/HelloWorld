package com.chen.zk.base;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author chen
 */
public class GetChildrenSync implements Watcher{
	public static ZooKeeper zooKeeper;
	public static void main(String[] args) {
		try {
			//创建连接，要实现Watcher来进行监控节点
//			zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new MyWatcher());
			zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new GetChildrenSync());
			System.out.println(zooKeeper.getChildren("/", true));
			Thread.sleep(50000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void process(WatchedEvent event) {
		if(event.getState() == KeeperState.SyncConnected) {
			if(event.getType()== EventType.None && null == event.getPath()) {
				doSomething(zooKeeper);
			} else {
				if(event.getType() == EventType.NodeChildrenChanged) {
					try {
						System.out.println(zooKeeper.getChildren("/", true));
					} catch (KeeperException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	private void doSomething(ZooKeeper zooKeeper2) {
		System.out.println("第一次创建");
	}
}
