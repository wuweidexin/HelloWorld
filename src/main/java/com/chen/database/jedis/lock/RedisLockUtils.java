package com.chen.database.jedis.lock;

import org.apache.commons.lang.StringUtils;

import com.chen.database.jedis.util.CacheConstants;
import com.chen.database.jedis.util.JedisUtils;

public class RedisLockUtils {
	
	private static long EXPIRED = 600*1000;//10分钟
	
	/**
	 * 获取一个lockkey
	 * @param key
	 * @return
	 */
	public static String getLockKey(String key) {
		return CacheConstants.REDIS_LOCK_KEY+key;		
	}
	
	/**
	 * 获取一个锁值，过期时间为指定值
	 * 需要注意服务器的时间差
	 * @param expired
	 * @return
	 */
	public static String getLockValue(long expired) {
		return ""+(System.currentTimeMillis()+expired+1);		
	}
	
	/**
	 * 获取一个锁值，过期时间为默认值
	 * 需要注意服务器的时间差
	 * @return
	 */
	public static String getLockValue() {
		return ""+(System.currentTimeMillis()+EXPIRED+1);		
	}
	
	/**
	 * 申请锁，会做过期时间进行判断
	 * 需要注意服务器的时间差
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean acquireLock(String key,String value) {		
		
		if(StringUtils.isEmpty(value)||StringUtils.isEmpty(key)){
			return false;
		} 
	    boolean success = false;
  
	    long acquired = JedisUtils.setnx(key, value);
	    
	    if (acquired == 1){
	        success = true;
	    //SETNX失败，说明锁仍然被其他对象保持，检查其是否已经超时
	    }else {
	    	String old = JedisUtils.get(key);
	    	if(StringUtils.isEmpty(old)){
	    		return false;
	    	}
	        long oldValue = Long.valueOf(old);
	 
	        if (oldValue < System.currentTimeMillis()) {
	            String getValue = JedisUtils.getSet(key, String.valueOf(value));   
	            if(StringUtils.isEmpty(getValue)){
		    		return false;
		    	}

	            if (Long.valueOf(getValue) == oldValue) {
	                success = true;
	            }else{ 
	                success = false;
	            }
	        }else{         
	            success = false;
	        }
	    }    
	    return success;      
	}
	 
	/**
	 * value相同才会释放锁,避免释放非自己申请的锁
	 * @param key
	 * @param value
	 */
	public static void releaseLock(String key,String value) {
		if(StringUtils.isEmpty(value)||StringUtils.isEmpty(key)){
			return ;
		}   
		String val = JedisUtils.get(key);
		if(StringUtils.isEmpty(val)){
			return;
		}
	    // 避免删除非自己获取得到的锁
	    if (Long.valueOf(value).equals(Long.valueOf(val))){
	    	JedisUtils.del(key);     
	    }
	   
	}
}
