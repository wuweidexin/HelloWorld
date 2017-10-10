package com.chen.javase.thinkinginjava.bookexample.generics.selfboundedtypes.curiouslyrecurringgenerics;
/**
 * @author chen
 */
public class BasicHolder<T> {
	T element;
	public void set(T arg){
		element = arg;
	}
	public T get(){
		return element;
	}
	public void f(){
		System.out.println(element.getClass().getSimpleName());
	}
}
