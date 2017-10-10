package com.chen.database.jedis.util;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class JedisUtils {

	private static JedisPool pool = null;
	
	private static Logger logger = LoggerFactory.getLogger(JedisUtils.class); 

	private static Lock lock = new ReentrantLock();
	
    public static int retryNum = 3;//jedis重连次数

	

	// 每个线程共享一个连接
	private static ThreadLocal<Jedis> connWrapper = new ThreadLocal<Jedis>();
	
	// 每个线程共享一个连接
	private static ThreadLocal<Pipeline> pipelineWrapper = new ThreadLocal<Pipeline>();
	
	public static Jedis jedis() {
		
		JedisPool pool = getJedisPool();
		
		Jedis jedis = null;
		try{
			
			jedis = pool.getResource();			
		}catch(JedisConnectionException e){
			
			if(jedis != null){
				jedis.close();
				close(jedis);
			}
			logger.error("",e);
			return null;
		}
		return jedis;
	}
	
	
	/**返回一个线程共用一个redis连接
	 * @return
	 */
	public static Jedis myClient() {	
	int count = 0;
	Jedis jedis = null;
	do {
		try {
			jedis = connWrapper.get();
			if (jedis == null) {
				jedis=  jedis();
				connWrapper.set(jedis);
			}

		} catch (JedisConnectionException e) { 
			// 出现链接异常的时候归还jedis
			if(jedis != null){
				jedis.close();
				close(jedis);
			}
			logger.error("",e);
			try {
				Thread.sleep(2000); //休眠2秒
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	} while (jedis == null && count < retryNum);
	 return jedis;
	 }
	
	/**返回一个线程共用一个redis连接
	 * @return
	 */
	public static Pipeline myPipeline() {	
	int count = 0;
	Pipeline pipeline = null;
	do {
		try {
			pipeline = pipelineWrapper.get();
			if (pipeline == null) {
				pipeline=  myClient().pipelined();
				pipelineWrapper.set(pipeline);
			}

		} catch (Exception e) { 
			// 出现链接异常的时候归还jedis
			if(pipeline != null){
				pipeline.sync();
				close(myClient());
			}
			logger.error("",e);
			try {
				Thread.sleep(2000); //休眠2秒
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	} while (pipeline == null && count < retryNum);
	 return pipeline;
	 }
	

	/**
	 * 刷新管道数据，释放单线程redis管道连接
	 */
	public static void closePipelineConnection() {
		Pipeline pipeline=pipelineWrapper.get();
		if(pipeline!=null){
			pipeline.sync();
		}
		Jedis jedis = connWrapper.get();
		if (jedis != null) {
			try {
				close(jedis);
			} catch (Exception e) {
				logger.error("Can not close database connection", e);
			}
			// 释放掉保存的对象
			pipelineWrapper.remove();
			connWrapper.remove();
		}

	}
	

	/**利用jedis高效率的管道技术老保存key，默认保存24小时
	 * @param metaKey
	 * @param key
	 */
	public static void savePipelineKeysToCache(String metaKey,String key){
		savePipelineKeysToCache(metaKey, key, REDIS_EXPIRE_TIME);
	}
	
	/**利用jedis高效率的管道技术来保存key
	 * @param metaKey
	 * @param key
	 * @param cacheTime
	 */
	public static void savePipelineKeysToCache(String metaKey,String key, int cacheTime){
		//String keysStr = JedisUtils.getFromCache(metaKey);
		Jedis jedis=JedisUtils.jedis();
		String keysStr =  jedis.get(metaKey);
		JedisUtils.close(jedis);
		JSONArray jsonArr = new JSONArray();
		if(!StringUtils.isEmpty(keysStr)){
			jsonArr = JSONArray.fromObject(keysStr);
		}
		if(!jsonArr.contains(key)){
			jsonArr.add(key);
		}
		JedisUtils.savePipelineToCache(metaKey, jsonArr.toString());
	}
	
	
	/**增加管道保存
	 * @param key
	 * @param value
	 */
	public static void savePipelineToCache(String key,String value){
		savePipelineToCache(key, value,REDIS_EXPIRE_TIME);
	}
	
	
	/**
	 * @param key
	 * @param value
	 * @param cacheTime
	 */
	public static void savePipelineToCache(String key,String value, int cacheTime){
		Pipeline pipeline=JedisUtils.myPipeline();
		pipeline.setex(key, cacheTime ,  value);
	//	saveToCache(key, value, cacheTime);
	}
	
	
	public static void main(String[] args) {
		PropertiesUtils.init("D:\\soft\\dev_tools\\git\\common\\src\\test\\resources\\common.properties");
		long beginTime=System.currentTimeMillis();
/*		for(int i=0;i<5000;i++){
			JedisUtils.saveKeysToCache("test:"+i, i);	
		}*/
/*		System.out.println("cost1:"+(System.currentTimeMillis()-beginTime));
		beginTime=System.currentTimeMillis();
		for(int j=0;j<5000;j++){
			JedisUtils.savePipelineKeysToCache("testpel1:"+j, "testple1:"+j);
			System.out.println("get:"+JedisUtils.myClient().get("testpe1l:"+j));
			

		}*/
		Set<String>set =JedisUtils.myClient().zrange("10001:z_user_group_list:812a3d05-39a3-11e6-8825-005056ac6b20", 0, -1);
		System.out.println(set.size());
		JedisUtils.closePipelineConnection();
		System.out.println("cost2:"+(System.currentTimeMillis()-beginTime));
		
	}
	
	
	public static JedisPool getJedisPool() {

		if (pool == null) {
			
        	lock.lock();        	
        	try{
        		if(pool == null){
            		JedisPoolConfig config = new JedisPoolConfig();
            		config.setMaxTotal(CacheConstants.REDIS_POOL_MAXACTVE);
            		if(StringUtils.isEmpty(CacheConstants.REDIS_PASSWORD)){
            			pool = new JedisPool(config, CacheConstants.REDIS_URL, CacheConstants.REDIS_PORT);
            		}else{
            			pool = new JedisPool(config, CacheConstants.REDIS_URL, CacheConstants.REDIS_PORT,
            					CacheConstants.REDIS_TIMEOUT_IN_MILLIS,CacheConstants.REDIS_PASSWORD);
            		}
            	}
        	}catch(Exception e){
        		logger.error("",e);
        	}finally{
        		lock.unlock();
        	}
        }
        return pool;
    }
	public static void invalid(Jedis redis){
		try{
    		if (redis != null) {
                getJedisPool().returnBrokenResource(redis);
            }
    	}catch(Exception e){
    		logger.info(e.getMessage(), e);
    	}
	}
    public static void close(Jedis redis) {
        
    	try{
    		if (redis != null) {
                getJedisPool().returnResource(redis);
            }
    	}catch(Exception e){
    		logger.info(e.getMessage(), e);
    	}
    }
    public static String getFromCache(String key){
		Jedis jedis = null;
		String ret = null;
		try{
			jedis = JedisUtils.jedis();
			if(jedis != null && jedis.get(key) != null){
				long start = System.currentTimeMillis();
				ret = jedis.get(key);
				logger.info("get from redis cost:"+(System.currentTimeMillis()-start)+"ms ,key:"+key);
			}
		}catch(Exception e){
			logger.error("保存缓存失败,key:"+key);
			logger.error("保存缓存失败",e);
			if(jedis != null){
				JedisUtils.invalid(jedis);//将连接失效，避免重用返回错误的对象
				jedis = null;
			}	
		}finally{
			try{
				if(jedis != null){
					JedisUtils.close(jedis);
				}
			}catch(Exception e){
				logger.error("",e);
			}
		}
		return ret;
	}
    public static long del(String[] keys){
		Jedis jedis = null;
		long ret = 0l;
		try{
			jedis = JedisUtils.jedis();
			if(jedis != null){
				long start = System.currentTimeMillis();
				ret =  jedis.del(keys);
				logger.info("delete from redis cost:"+(System.currentTimeMillis()-start)+"ms ,key:"+StringUtils.join(keys,","));
			}
		}catch(Exception e){
			logger.error("删除缓存失败,key:"+StringUtils.join(keys,","));
			logger.error("删除缓存失败",e);
			if(jedis != null){
				JedisUtils.invalid(jedis);//将连接失效，避免重用返回错误的对象
				jedis = null;
			}	
		}finally{
			try{
				if(jedis != null){
					JedisUtils.close(jedis);
				}
			}catch(Exception e){
				logger.error("",e);
			}
		}
		return ret;
	}
	public static final int REDIS_EXPIRE_TIME = 24*60*60;
	
	
	public static void saveToCache(String key,String value){
		saveToCache(key, value, REDIS_EXPIRE_TIME);
	}
	
	
	
	
	public static void saveToCache(String key, String value, int cacheTime) {
		Jedis jedis = null;
		try{
			jedis = JedisUtils.jedis();
			if(jedis != null){
				long start = System.currentTimeMillis();
				jedis.setex(key, cacheTime , value);
				logger.info("save to redis cost:"+(System.currentTimeMillis()-start)+"ms ,key:"+key);
			}	
		}catch(Exception e){
			logger.error("保存缓存失败,key:"+key+" value:"+value);
			logger.error("保存缓存失败",e);
			if(jedis != null){
				JedisUtils.invalid(jedis);
				jedis = null;
			}
		}finally{
			try{
				if(jedis != null){
					JedisUtils.close(jedis);
				}
			}catch(Exception e){
				logger.error("",e);
			}
		}
	}
	
	public static List<String> mget(String[] keys){
		Jedis jedis = null;
		List<String> ret = new LinkedList<String>();
		try{
			jedis = JedisUtils.jedis();
			if(jedis != null){
				long start = System.currentTimeMillis();
				ret = jedis.mget(keys);
				logger.info("get from redis cost:"+(System.currentTimeMillis()-start)+"ms ,key:"+keys);
			}
		}catch(Exception e){
			logger.error("获取缓存失败,key:"+keys);
			logger.error("保存缓存失败",e);
			if(jedis != null){
				JedisUtils.invalid(jedis);//将连接失效，避免重用返回错误的对象
				jedis = null;
			}	
		}finally{
			try{
				if(jedis != null){
					JedisUtils.close(jedis);
				}
			}catch(Exception e){
				logger.error("",e);
			}
		}
		return ret;
	}
	private static final String REDIS_POOL_JMX_PREFIX = "org.apache.commons.pool2:type=GenericObjectPool,name=pool1";
	private static final String[] REDIS_POOL_JMX_ATTRS = {"NumActive","NumIdle","NumWaiters"};
	/**
	 * 通过jmx获取redis pool的状态
	 * @author leon_yan
	 * @return
	 */
	public static Map<String,Object> getRedisPoolStatus(){
		Map<String,Object> status = new HashMap<String,Object>();
		ObjectName on;
		try {
			on = new ObjectName(REDIS_POOL_JMX_PREFIX);
			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
			for(String attr : REDIS_POOL_JMX_ATTRS){
				Object value = mBeanServer.getAttribute(on, attr);
				status.put(attr, value);
			}
		} catch (Exception e) {
			logger.error("",e);
		} 
		return status;
	}
	/**
	 * @author leon_yan
	 * 为了避免使用keys从redis中模糊查找的性能问题，将key 也保存到redis中
	 * @param metaKey：作为保存keys的key,比如：CacheConstants.OPENSYS_OPENORG__PERSONBYUPDATETIME+eid
	 * @param key：需要保存的key
	 */
	public static void saveKeysToCache(String metaKey,String key){
		String keysStr = JedisUtils.getFromCache(metaKey);
		JSONArray jsonArr = new JSONArray();
		if(!StringUtils.isEmpty(keysStr)){
			jsonArr = JSONArray.fromObject(keysStr);
		}
		if(!jsonArr.contains(key)){
			jsonArr.add(key);
		}
		JedisUtils.saveToCache(metaKey, jsonArr.toString());
	}
	/**
	 * @author leon_yan
	 * 为了避免使用keys从redis中模糊查找的性能问题，通过元key 获取keys
	 * @param metaKey：作为保存keys的key,比如：CacheConstants.OPENSYS_OPENORG__PERSONBYUPDATETIME+eid
	 */
	public static Set<String> getKeysFromCache(String metaKey){
		String keysStr = JedisUtils.getFromCache(metaKey);
		JSONArray jsonArr = new JSONArray();
		if(!StringUtils.isEmpty(keysStr)){
			jsonArr = JSONArray.fromObject(keysStr);
		}
		Set<String> ret = new HashSet<String>();
		for(int i=0,size=jsonArr.size();i<size;i++){
			ret.add(jsonArr.getString(i));
		}
		return ret;
	}
	
    public static long hincrBy(String key,String field,long integer){
    	Jedis jedis = null;
		long ret = 0l;
		try{
			jedis = JedisUtils.jedis();
			if(jedis != null){
				long start = System.currentTimeMillis();
				ret =  jedis.hincrBy(key, field,integer);
				logger.info("hincrBy from redis cost:"+(System.currentTimeMillis()-start)+"ms ,key:"+key);
			}
		}catch(Exception e){
			logger.error("获取自增数据失败,key:"+key,e);			
			if(jedis != null){
				JedisUtils.invalid(jedis);//将连接失效，避免重用返回错误的对象
				jedis = null;
			}	
		}finally{
			try{
				if(jedis != null){
					JedisUtils.close(jedis);
				}
			}catch(Exception e){
				logger.error("",e);
			}
		}
		return ret;
    }
    
    /**
     * key不存在则存储，存在不覆盖
     */
    public static long setnx(String key,String value){
    	Jedis jedis = null;
		long ret = 0;
		try{
			jedis = JedisUtils.jedis();
			if(jedis != null){
//				long start = System.currentTimeMillis();
				ret =  jedis.setnx(key, value);
//				logger.info("setnx from redis cost:"+(System.currentTimeMillis()-start)+"ms ,key:"+key);
			}
		}catch(Exception e){
			logger.error("获取setnx数据失败,key:"+key,e);			
			if(jedis != null){
				JedisUtils.invalid(jedis);//将连接失效，避免重用返回错误的对象
				jedis = null;
			}	
		}finally{
			try{
				if(jedis != null){
					JedisUtils.close(jedis);
				}
			}catch(Exception e){
				logger.error("",e);
			}
		}
		return ret;
    }
    
    /**
     * key不存在则存储，存在时不覆盖，使用时请特别注意：
     *  此处的设置key和设置超时并不是一个统一的原子操作
     * （如小概率事件：设置key成功了，但是设置超时失败了，此时会返回了成功：
     * 因为如果返回了失败，由于key不会过期，所以后续调用setnx会一直失败），因此，
     * 需要其它的辅佐手段检查key是否过期（如：key-value的value字段存放当前日期，
     * 锁定时，检查日期字段，看锁是否过期等）
     * @date 2015-11-25
     * @author wenxiang_xu
     */
    public static long setnx(String key,String value,int timeoutSeconds){
    	Jedis jedis = null;
		long ret = 0;
		try{
			jedis = JedisUtils.jedis();
			if(jedis != null){
//				long start = System.currentTimeMillis();
				ret =  jedis.setnx(key, value);
				if(ret==1){
					jedis.expire(key, timeoutSeconds);
				}
//				logger.info("setnx from redis cost:"+(System.currentTimeMillis()-start)+"ms ,key:"+key);
			}
		}catch(Exception e){
			logger.error("获取setnx数据失败,key:"+key,e);			
			if(jedis != null){
				JedisUtils.invalid(jedis);//将连接失效，避免重用返回错误的对象
				jedis = null;
			}	
		}finally{
			try{
				if(jedis != null){
					JedisUtils.close(jedis);
				}
			}catch(Exception e){
				logger.error("",e);
			}
		}
		return ret;
    }
    
    /**
     * 设置新值并获取旧值
     */
    public static String getSet(String key,String value){
    	Jedis jedis = null;
		String ret = null;
		try{
			jedis = JedisUtils.jedis();
			if(jedis != null){
//				long start = System.currentTimeMillis();
				ret =  jedis.getSet(key, value);
//				logger.info("getSet from redis cost:"+(System.currentTimeMillis()-start)+"ms ,key:"+key);
			}
		}catch(Exception e){
			logger.error("获取getSet数据失败,key:"+key,e);			
			if(jedis != null){
				JedisUtils.invalid(jedis);//将连接失效，避免重用返回错误的对象
				jedis = null;
			}	
		}finally{
			try{
				if(jedis != null){
					JedisUtils.close(jedis);
				}
			}catch(Exception e){
				logger.error("",e);
			}
		}
		return ret;
    }
    
    /**
     * 删除key
     */
    public static long del(String key){
		Jedis jedis = null;
		long ret = 0l;
		try{
			jedis = JedisUtils.jedis();
			if(jedis != null){
//				long start = System.currentTimeMillis();
				ret =  jedis.del(key);
//				logger.info("delete from redis cost:"+(System.currentTimeMillis()-start)+"ms ,key:"+key);
			}
		}catch(Exception e){
			logger.error("删除缓存失败,key:"+key);
			logger.error("删除缓存失败",e);
			if(jedis != null){
				JedisUtils.invalid(jedis);//将连接失效，避免重用返回错误的对象
				jedis = null;
			}	
		}finally{
			try{
				if(jedis != null){
					JedisUtils.close(jedis);
				}
			}catch(Exception e){
				logger.error("",e);
			}
		}
		return ret;
	}
    
    /**
     * 与getFromCache一致
     */
    public static String get(String key){
    	Jedis jedis = null;
		String ret = null;
		try{
			jedis = JedisUtils.jedis();
			if(jedis != null){
//				long start = System.currentTimeMillis();
				ret =  jedis.get(key);
//				logger.info("getnx from redis cost:"+(System.currentTimeMillis()-start)+"ms ,key:"+key);
			}
		}catch(Exception e){
			logger.error("获取get数据失败,key:"+key,e);			
			if(jedis != null){
				JedisUtils.invalid(jedis);//将连接失效，避免重用返回错误的对象
				jedis = null;
			}	
		}finally{
			try{
				if(jedis != null){
					JedisUtils.close(jedis);
				}
			}catch(Exception e){
				logger.error("",e);
			}
		}
		return ret;
    }
    
    
    /**
     * 从redis队列尾部取值
     * @param key
     * @param start: 起始index,可以为负值，如果是负值，则是从尾部开始计算,-1为尾部第一个
     * @param end: 结束的index,可以为负值，如果是负值，则是从尾部开始计算
     * @return
     */
    public static List<String> lrange(String key,int start,int end){
    	Jedis jedis = null;
		List<String> ret = new ArrayList<String>();
		try{
			jedis = JedisUtils.jedis();
			if(jedis != null){
//				long startTime = System.currentTimeMillis();
				ret =  jedis.lrange(key,start,end);
//				logger.info("lrange from redis cost:"+(System.currentTimeMillis()-startTime)+"ms ,key:"+key);
			}
		}catch(Exception e){
			logger.error("lrange失败",e);
			if(jedis != null){
				JedisUtils.invalid(jedis);//将连接失效，避免重用返回错误的对象
				jedis = null;
			}	
		}finally{
			try{
				if(jedis != null){
					JedisUtils.close(jedis);
				}
			}catch(Exception e){
				logger.error("",e);
			}
		}
		return ret;
	}
}
