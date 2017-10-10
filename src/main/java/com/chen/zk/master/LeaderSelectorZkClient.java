package com.chen.zk.master;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * @author chen
 */
public class LeaderSelectorZkClient {
	private final static int clientCount=5;
	private final static String zkServers = "127.0.0.1:2181";
	private final static int sessionTimeout = 10000;
	
	public static void main(String[] args) throws Exception {
		List<ZkClient> zkClientList = new ArrayList<ZkClient>();
		List<WorkServer> workserverList = new ArrayList<WorkServer>();
		try {
			for(int i = 0; i < clientCount; i ++) {
				ZkClient zkClient = new ZkClient(zkServers, sessionTimeout, sessionTimeout, new SerializableSerializer() );
				zkClientList.add(zkClient);
				
				ConfigData config = new ConfigData();
				config.setIp("127.0.0."+i);
				config.setName("服务器："+i);
				config.setPort("808"+i);
				
				WorkServer workServer = new WorkServer(config);
				workServer.setZkClient(zkClient);
				workServer.start();
			}
			System.out.println("敲击回车退出!\n");
			new BufferedReader(new InputStreamReader(System.in)).readLine();
		} finally {
			System.out.println("关闭服务...");
			for(int i = 0; i < workserverList.size(); i ++) {
				try {
					WorkServer temp = workserverList.get(i);
					temp.stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}

}
