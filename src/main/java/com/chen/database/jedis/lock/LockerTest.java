package com.chen.database.jedis.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.chen.database.jedis.util.JedisUtils;
import com.chen.database.jedis.util.PropertiesUtils;

public class LockerTest {
	
	public LockerTest() {
		PropertiesUtils.init("D:/gittest/HelloWorld/config.properties");
	}
	
	public static void main(String[] args) {
		LockerTest lt = new LockerTest();
		lt.testLock();
		lt.testLock();
		lt.testLock();
	}
	public void testLock(){
		String eid = "111";
		String lockValue = RedisLockUtils.getLockValue();
		String lockKey = RedisLockUtils.getLockKey(eid);
		try{
			if(RedisLockUtils.acquireLock(lockKey, lockValue)){
				Thread.sleep(8000);
			} else {
				//申请锁失败
				System.out.println("锁申请失败，人员添加失败，因为其他管理员正在进行相关组织人员信息的变更，请等候..."+ eid);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			RedisLockUtils.releaseLock(lockKey, lockValue);
		}
	}
}
