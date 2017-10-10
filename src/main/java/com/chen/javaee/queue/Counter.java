package com.chen.javaee.queue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter {
	private static class CountItem {
		private long startSecond;
		private AtomicLong count = new AtomicLong(0L);
		public long getStartSecond(){
			return startSecond;
		}
		public void setStartSecond(long startSecond) {
			this.startSecond = startSecond;
		}
		public long getCount(){
			return count.get();
		}
		public void addCount(){
			count.incrementAndGet();
		}
	}
	private Lock lock = new ReentrantLock();
	private List<CountItem> list = new LinkedList<CountItem>();
	private int maxLog = 16;
	
	public void add(){
		lock.lock();
		long currentSecond = System.currentTimeMillis() / 1000;
		try {
			CountItem countItem = null;
			if(list.size() > 0) {
				countItem = list.get(0);
				if(currentSecond != countItem.getStartSecond()) {
					countItem = null;
				}
			}
			if(countItem == null) {
				countItem = new CountItem();
				countItem.setStartSecond(currentSecond);
				list.add(0, countItem);
				if(list.size() == maxLog) {
					list.remove(maxLog - 1);
				}
			}
			countItem.addCount();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public long get() {
		lock.lock();
		long rtn = 0L;
		try {
			long currentSecond = System.currentTimeMillis() / 1000;
			if(list.size() > 0) {
				CountItem item = list.get(0);
				if(item.getStartSecond() - currentSecond > 30) {
					list.clear();
				}
			}
			for(CountItem item : list) {
				if(item.getCount() > rtn) {
					rtn = item.getCount();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return rtn;
	}
	public void reset() {
		lock.lock();
		try {
			list.clear();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}
