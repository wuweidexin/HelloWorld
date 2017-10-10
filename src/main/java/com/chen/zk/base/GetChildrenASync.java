package com.chen.zk.base;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * @author chen
 */
public class GetChildrenASync implements Watcher{
	public static ZooKeeper zooKeeper;
	public static void main(String[] args) {
		try {
			//创建连接，要实现Watcher来进行监控节点
//			zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new MyWatcher());
			zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, new GetChildrenASync());
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
					zooKeeper.getChildren("/", true, new IChildren2Callback(),"");
				}
			}
		}
	}
	private void doSomething(ZooKeeper zooKeeper2) {
		System.out.println("第一次创建");
	}
	static class IChildren2Callback implements AsyncCallback.Children2Callback{

		@Override
		public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
			StringBuffer sbf = new StringBuffer();
			sbf.append("rc = "+ rc).append("\n");
			sbf.append("path = "+ path).append("\n");
			sbf.append("ctx = "+ ctx).append("\n");
			sbf.append("children = "+ children).append("\n");
			sbf.append("stat = "+ stat).append("\n");
			System.out.println(sbf.toString());
		}
		
	}
}
