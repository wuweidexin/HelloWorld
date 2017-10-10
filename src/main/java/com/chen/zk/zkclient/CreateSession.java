package com.chen.zk.zkclient;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

/**
 * @author chen
 */
public class CreateSession {
	private ZkClient zkClient = null;
	public CreateSession(){
		zkClient = new ZkClient("127.0.0.1:2181", 10000, 10000);
	}
	
	public static void main(String[] args) {
		//验证是否连接成功
		CreateSession cs = new CreateSession();
		System.out.println(cs.zkClient.countChildren("/"));
		//创建节点
		//cs.createNode();
		//获取子节点
	}
	
	private void createNode(){
		zkClient.create("/node_zk_1", "zk_test", CreateMode.PERSISTENT);
		System.out.println("createNode success...");
	}
}
