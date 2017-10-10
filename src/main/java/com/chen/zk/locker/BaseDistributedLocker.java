package com.chen.zk.locker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;


/**
 * @author chen
 */
public class BaseDistributedLocker {
	public static final String ZK_LOCK_PATH = "/locker";
	public static final String ZK_ADDRESS_PORT = "localhost:2181";
	CuratorFramework client = null;
	InterProcessMutex lock = null;
	public BaseDistributedLocker() {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		this.client = CuratorFrameworkFactory.newClient(ZK_ADDRESS_PORT, retryPolicy);
		this.client.start();
		try {
			if(null == this.client.checkExists().forPath(ZK_LOCK_PATH))
				this.client.create().forPath(ZK_LOCK_PATH, "Hello".getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean acquire() throws Exception {
		lock = new InterProcessMutex(client, ZK_LOCK_PATH);
		return lock.acquire(10 * 1000, TimeUnit.SECONDS);
	}
	
	public static void main(String[] args) {
		BaseDistributedLocker bd = new BaseDistributedLocker();
		ExecutorService excutor = Executors.newCachedThreadPool();
		
		for(int i = 0; i < 5; i ++) {
			WorkThread t = new WorkThread(i);
			excutor.execute(t);
		}
		
	}

	public void release() throws Exception {
		lock.release();
		client.close();
		System.out.println("进程:成功释放锁，正在执行相关的操作...");
	}
	
	
	public static class WorkThread implements Runnable
	{
	    private BaseDistributedLocker locker;
	    private int num; 
	    public WorkThread() {
			// TODO Auto-generated constructor stub
		}
	    public WorkThread(int num) {
	    	this.locker = new BaseDistributedLocker();
	    	this.num = num;
		}
	    public void run()
	    {
	    	try {
				if(locker.acquire()) {
					System.out.println("进程:"+num+"成功获取锁，正在执行相关的操作...");
					Thread.sleep(10000);
				} 
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					locker.release();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	    }
	}

}
