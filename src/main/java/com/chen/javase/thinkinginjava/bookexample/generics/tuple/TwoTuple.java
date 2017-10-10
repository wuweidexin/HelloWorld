package com.chen.javase.thinkinginjava.bookexample.generics.tuple;
/**
 * 二元组方法
 * @author chen
 *
 * @param <A>
 * @param <B>
 */
public class TwoTuple<A, B> {
	//final类型一般是需要初始化的，这里在构造方法中做了初始化所以没有报错
	public final A first;
	public final B second;
	public TwoTuple(A a, B b) {
		first = a;
		second = b;
	}
	@Override
	public String toString() {
		return "TwoTuple [first=" + first + ", second=" + second + "]";
	}
}
