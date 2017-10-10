package com.chen.javase.thinkinginjava.bookexample.generics.tuple;
/**
 * 使用集成的方式来实现长度更长的元组
 * @author chen
 *
 * @param <A>
 * @param <B>
 * @param <C>
 */
public class ThreeTuple<A, B, C> extends TwoTuple<A, B> {
	public final C third;
	public ThreeTuple(A a, B b, C c) {
		super(a, b);
		third = c;
	}
	@Override
	public String toString() {
		return "ThreeTuple [third=" + third + ", first=" + first + ", second="
				+ second + "]";
	}
	
}
