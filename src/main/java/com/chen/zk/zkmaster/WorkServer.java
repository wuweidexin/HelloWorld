package com.chen.zk.zkmaster;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.CreateMode;

/**
 * @author chen
 */
public class WorkServer {
	private boolean running = false;
	private String rootPath = "/zkmaster";
	private ZkClient zkClient;
	private IZkDataListener dataListener;
	private ConfigData masterData;
	private ConfigData serverData;
	//设定延时线程
	private ScheduledExecutorService delayExecutor = Executors.newScheduledThreadPool(1);
	private int delayTime = 5;
	
	public WorkServer(ConfigData configData) {
		this.serverData = configData;
		this.dataListener = new IZkDataListener() {
			
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				// TODO Auto-generated method stub
				//应对网络抖动
				if(null != masterData && masterData.getName().equals(serverData.getName())) {
					takeMaster();
				} else {
					delayExecutor.schedule(new Runnable() {
						@Override
						public void run() {
							takeMaster();
						}
					}, delayTime, TimeUnit.SECONDS);
				}
			}
			
			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	
	public void start() throws Exception{
		if(running) {
			throw new Exception("sever has startup...");
		}
		running = true;
		zkClient.subscribeDataChanges(rootPath, dataListener);
		takeMaster();
	}
	
	public void stop() throws Exception{
		if(!running) {
			throw new Exception("sever has stop...");
		}
		running = false;
		zkClient.unsubscribeDataChanges(rootPath, dataListener);
	}
	
	
	private void takeMaster(){
		if(!running) {
			return;
		}
		try{
			zkClient.create(rootPath, serverData, CreateMode.EPHEMERAL);
			masterData = serverData;
			System.out.println("服务端："+masterData.getIp()+":"+masterData.getPort()+" 是master");
			//测试，每间隔5S释放一次master
//			delayExecutor.schedule(new Runnable() {
//				@Override
//				public void run() {
//					if(checkMaster()) {
//						release();
//					}
//				}
//			}, delayTime, TimeUnit.SECONDS);
			
		} catch(ZkNodeExistsException e) {
			ConfigData configData = zkClient.readData(rootPath);
			if(configData == null) {
				takeMaster();
			}
		}
	}
	
	public void release(){
		
		if(checkMaster()) {
			zkClient.delete(rootPath);
			zkClient.unsubscribeDataChanges(rootPath, dataListener);
		}
		
	}
	
	private boolean checkMaster(){
		try {
			ConfigData master = zkClient.readData(rootPath);
			if(master.getName().equals(serverData.getName())) {
				return true;
			}
		} catch (ZkNoNodeException e) {
			return false;
		} catch (ZkInterruptedException e) {
			checkMaster();
		} catch(ZkException e) {
			return false;
		}
		return false;
	}


	public ZkClient getZkClient() {
		return zkClient;
	}


	public void setZkClient(ZkClient zkClient) {
		this.zkClient = zkClient;
	}
	
}
