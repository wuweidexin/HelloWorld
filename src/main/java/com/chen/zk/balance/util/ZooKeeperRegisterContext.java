package com.chen.zk.balance.util;

import org.I0Itec.zkclient.ZkClient;

import com.chen.zk.zkmaster.ConfigData;

/**
 * @author chen
 */
public class ZooKeeperRegisterContext {
	private String path;
	private ZkClient zkClient;
	private Object data;
	
	public ZooKeeperRegisterContext() {
		super();
	}

	public ZooKeeperRegisterContext(String path, ZkClient zkClient, Object data) {
		super();
		this.path = path;
		this.zkClient = zkClient;
		this.data = data;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ZkClient getZkClient() {
		return zkClient;
	}

	public void setZkClient(ZkClient zkClient) {
		this.zkClient = zkClient;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	
}
