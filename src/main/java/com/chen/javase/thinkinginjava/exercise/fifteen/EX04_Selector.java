package com.chen.javase.thinkinginjava.exercise.fifteen;

public interface EX04_Selector<T> {
	boolean end();
	T current();
	void next();
}
