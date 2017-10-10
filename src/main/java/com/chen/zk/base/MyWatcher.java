package com.chen.zk.base;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;

/**
 * @author chen
 * zookeeper监听器
 */
public class MyWatcher implements Watcher{

	@Override
	public void process(WatchedEvent event) {
		System.out.println("收到事件:"+event.getState());
		if(event.getState() == KeeperState.SyncConnected) {
			doSomething();
		}
	}

	private void doSomething() {
		try {
			//同步创建节点
			String path = CreateSession.zooKeeper.create("/node_1", "hello zookeeper".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println(path);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
