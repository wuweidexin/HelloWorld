package com.chen.javase.thinkinginjava.bookexample.generics.tuple;
/**
 * 多元组，泛型的强化使用，一个类中加入很多的可能引入的类型
 * 元组工具库
 * @author chen
 *
 */
public class Tuple {
	public static <A, B> TwoTuple<A, B> tuple(A a, B b) {
		return new TwoTuple<A, B>(a, b);
	}
	public static <A, B, C> ThreeTuple<A, B, C> tuple(A a, B b, C c) {
		return new ThreeTuple<A, B, C>(a, b, c);
	}
	
	public static <A, B, C, D> FourTuple<A, B, C, D> tuple(A a, B b, C c, D d) {
		return new FourTuple<A, B, C, D>(a, b, c, d);
	}
	
	public static <A, B, C, D, E> FiveTuple<A, B, C, D, E> tuple(A a, B b, C c, D d, E e) {
		return new FiveTuple<A, B, C, D, E>(a, b, c, d, e);
	}
}
