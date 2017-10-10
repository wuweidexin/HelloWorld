package com.chen.javaee.queue;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;

public class QueueThreadFactory implements ThreadFactory{
	private static final AtomicInteger poolNumber = new AtomicInteger(1);
	private final ThreadGroup group;
	private final AtomicInteger threadNumber = new AtomicInteger(1);
	private final String namePrefix;
	public QueueThreadFactory(String name) {
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup():
		Thread.currentThread().getThreadGroup();
		if(StringUtils.isBlank(name)) {
			namePrefix = "pool-"+poolNumber.getAndIncrement() + "-thread-";
		} else {
			namePrefix = name + "-thread-";
		}
	}
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, namePrefix+threadNumber.getAndIncrement(), 0);
		if(t.isDaemon()) {
			t.setDaemon(false);
		}
		if(t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}
		return t;
	}
	
}
