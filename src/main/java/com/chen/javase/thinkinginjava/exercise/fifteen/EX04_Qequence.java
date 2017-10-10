package com.chen.javase.thinkinginjava.exercise.fifteen;

public class EX04_Qequence<T> {
	private Object[] items;
	private int next = 0;
	private EX04_Qequence(int size){
		items = new Object[size];
	}
	public void add(T x) {
		if(next < items.length) {
			items[next ++] = x;
		}
	}
	private class SequenceSelector<T> implements EX04_Selector<T>{
		private int i = 0;
		@Override
		public boolean end() {
			return i == items.length;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T current() {
			return (T) items[i];
		}

		@Override
		public void next() {
			if(i < items.length){
				i ++;
			}
		}
		
	}
	public EX04_Selector<T> selector(){
		return new SequenceSelector<T>();
	}
	
}
