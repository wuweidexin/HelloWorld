package com.chen.zk.base;

import org.apache.zookeeper.AsyncCallback;
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
public class AsynWatcher implements Watcher{

	@Override
	public void process(WatchedEvent event) {
		System.out.println("收到事件:"+event.getState());
		if(event.getState() == KeeperState.SyncConnected) {
			doSomething();
		}
	}

	private void doSomething() {
		//异步创建节点
		CreateSession.zooKeeper.create("/node_2", "hello zookeeper".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, new IStringCallback(),"Hello");
	}
	static class IStringCallback implements AsyncCallback.StringCallback{
		/**
		 * 返回码
		 * 完整路径
		 * 上下文
		 * 服务端返回的已经创建的真是路径
		 */
		@Override
		public void processResult(int rc, String path, Object ctx, String name) {
			StringBuffer sbf = new StringBuffer();
			sbf.append("rc = "+ rc).append("\n");
			sbf.append("path = "+ path).append("\n");
			sbf.append("ctx = "+ ctx).append("\n");
			sbf.append("name = "+ name).append("\n");
			System.out.println(sbf.toString());
		}
	}
}
