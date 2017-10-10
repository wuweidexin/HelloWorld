package com.chen.javaee.queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;

import com.chen.database.jedis.other.WrapXedis;

/**
 * redis队列
 * @author junquan_chen
 *
 */
public class RedisQueue implements IQueue {
	private static String PREF = "MyRedisListQueue";
	private List<RedisQueueMessageListener> queueMessageListnerList = new ArrayList<RedisQueueMessageListener>();
	private Map<String, AtomicLong> queueSizeMap = new HashMap<String, AtomicLong>();
	private Map<String, Counter> counterMap = new HashMap<String, Counter>();
	private Lock lock = new ReentrantLock();
	private WrapXedis xedisClient;
	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendMessage(String topic, String json) {
		// TODO Auto-generated method stub

	}

	@Override
	public void subscribe(String topic, QueueMessageListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public long size(String topic) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long qps(String topic) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(String topic) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delayDelete(String topic, int seconds) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delPubMessage(String topic, String json) {
		// TODO Auto-generated method stub

	}
	
	private static class RedisQueueMessageListener implements Runnable {
		private String topic;
		private QueueMessageListener listener;
		private Executor defaultExecutor = null;
		private Lock lock = new ReentrantLock();
		private AtomicBoolean isStop = new AtomicBoolean(false);
		private String name = null;
		private AtomicLong inqueue = new AtomicLong(0L);
		private long maxInQueue = 5L;
		private long minInQueue = 1L;
		private AtomicLong queueSize;
		private Counter counter;
		private WrapXedis xedisClient;
		public String getName(){
			return name;
		}
		
		@Override
		public void run() {
			Executor executor = getExecutor();
		}
		private Executor getExecutor() {
			Executor executor = listener.getExecutor();
			if(executor == null) {
				if(defaultExecutor == null) {
					lock.lock();
					try {
						if(defaultExecutor == null) {
							defaultExecutor = QueueExecutors.newCachedThreadPool(1, 5, new QueueThreadFactory(topic));
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
					}
				}
			}
			return null;
		}

		private void addExecutor(Executor executor, ThreadFactory threadFactory, boolean autoStop) {
			RedisQueueMessageExecutor msgExecutor = new RedisQueueMessageExecutor(topic, listener, inqueue, queueSize, isStop, autoStop, counter, xedisClient);
			if(threadFactory != null) {
				Thread thread = threadFactory.newThread(msgExecutor);
				thread.start();
			} else {
				executor.execute(msgExecutor);
			}
		}
	}
	private static class RedisQueueMessageExecutor implements Runnable {
		private String topic;
		private QueueMessageListener listener;
		private AtomicLong inqueue;
		private AtomicLong queueSize;
		private AtomicBoolean isStop;
		private boolean autoStop;
		private Counter counter;
		private WrapXedis xedisClient;
		
		public RedisQueueMessageExecutor(String topic, QueueMessageListener listener, AtomicLong inqueue,
				AtomicLong queueSize, AtomicBoolean isStop, boolean autoStop, Counter counter, WrapXedis xedisClient) {
			super();
			this.topic = topic;
			this.listener = listener;
			this.inqueue = inqueue;
			this.queueSize = queueSize;
			this.isStop = isStop;
			this.autoStop = autoStop;
			this.counter = counter;
			this.xedisClient = xedisClient;
			inqueue.incrementAndGet();
		}
		@Override
		public void run() {
			try {
				innerRun();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		private void innerRun() {
			int emptyCount = 0;
			int exeCount = 0;
			while(!isStop.get()) {
				boolean isOk = true;
				String msg = null;
				try {
					if(queueSize.get() > 0L) {
						msg = xedisClient.lpop(topic);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(!StringUtils.isBlank(msg)) {
					counter.add();
					queueSize.decrementAndGet();
					emptyCount = 0;
					onMessage(topic, msg);
					exeCount ++;
					if(exeCount > 200) {
						exeCount = 0;
						sleep(100L);
					}
				} else if(isOk) {
					exeCount = 0;
					emptyCount ++;
					if(emptyCount > 60 && autoStop) {
						break;
					} else {
						sleep(500L);
					}
				} else {
					exeCount = 0;
					emptyCount = 0;
				}
				
			}
		}
		private void sleep(long time) {
			try {
				Thread.sleep(time);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		private void onMessage(String channel, String msg) {
			try {
				listener.recieveMessage(channel, msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
