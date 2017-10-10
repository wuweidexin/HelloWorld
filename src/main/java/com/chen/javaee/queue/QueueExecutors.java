package com.chen.javaee.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 对列执行者
 * @author junquan_chen
 *
 */
public class QueueExecutors {
	/*
	 * 创建线程池
	 */
	public static ExecutorService newCachedThreadPool(int min, int max, ThreadFactory threadFactory) {
		long time = 60L;
		BlockingQueue<Runnable> workQueue;
		if(min == max) {
			time = 0L;
			workQueue = new LinkedBlockingQueue<Runnable>();
		} else {
			workQueue = new SynchronousQueue<Runnable>();
		}
		return new ThreadPoolExecutor(min, max, time, TimeUnit.SECONDS, workQueue);
	}
}
