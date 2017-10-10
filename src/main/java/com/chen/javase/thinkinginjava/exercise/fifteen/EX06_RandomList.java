package com.chen.javase.thinkinginjava.exercise.fifteen;

import java.util.ArrayList;
import java.util.Random;
/**
 * 内部类可以访问外部类
 * @author junquan_chen
 *
 * @param <T>
 */
public class EX06_RandomList<T> {
	private ArrayList<T> storage = new ArrayList<T>();
	private Random rand = new Random(47);
	public void add(T item) {
		storage.add(item);
	}
	public T select(){
		return storage.get(rand.nextInt(storage.size()));
	}
	
	public static void main(String[] args) {
		EX06_RandomList<Integer> list = new EX06_RandomList<Integer>();
		for(int i = 0; i < 10; i ++){
			list.add(i);
		}
		for(int i = 0; i < 10; i ++) {
			System.out.println(list.select());
		}
		
	}
}
