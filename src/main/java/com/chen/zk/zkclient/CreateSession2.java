package com.chen.zk.zkclient;

import java.util.Date;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @author chen
 */
public class CreateSession2 {
	private ZkClient zkClient = null;
	private String path = "/node_zk_2";
	public CreateSession2(){
		zkClient = new ZkClient("127.0.0.1:2181", 10000, 10000,new SerializableSerializer());
	}
	
	public static void main(String[] args) {
		//验证是否连接成功
		CreateSession2 cs = new CreateSession2();
		System.out.println(cs.zkClient.countChildren("/"));
		//创建节点，使用序列化技术
		cs.createNode();
		//获取子节点
		cs.getData();
		cs.getDataWithStat();
		cs.getNodeChild();
		cs.delNode();
	}
	
	private void createNode(){
		User u = new User();
		u.setId(1);
		u.setName("chen");
		String repath = zkClient.create(path, u, CreateMode.PERSISTENT);
		System.out.println("createNode success and path is:" +  repath);
	}
	/**
	 * 获取数据
	 */
	private void getData() {
		 User u = zkClient.readData(path);
		 System.out.println(u.toString());
	}
	
	/**
	 * 获取数据，同时输出状态信息
	 */
	private void getDataWithStat() {
		Stat stat = new Stat();
		User u = zkClient.readData(path, stat);
		System.out.println("获取得到的数据对象是："+u.toString());
		System.out.println("得到的状态是："+ stat);
	}
	
	/**
	 * 获取子节点的信息
	 */
	private void getNodeChild() {
		List<String> list = zkClient.getChildren(path);
		System.out.println(list);
	}
	
	/**
	 * 获取子节点的信息
	 */
	private void delNode() {
		zkClient.delete(path);//删除当前节点
		zkClient.deleteRecursive(path);//循环删除
		System.out.println("删除节点完成");
	} 
}
