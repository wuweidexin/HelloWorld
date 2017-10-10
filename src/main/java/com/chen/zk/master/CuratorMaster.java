package com.chen.zk.master;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorMaster {
	CuratorFramework client = null;
	public CuratorMaster() {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(10000, 5);
		client = CuratorFrameworkFactory.newClient("127.0.0.1:2180", 5000, 5000, retryPolicy);
	}
	public static void main(String[] args) {
		CuratorMaster cm = new CuratorMaster();
	}
	
	private void master() {
		
	}
}
