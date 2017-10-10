package com.chen.javase.thinkinginjava.bookexample.generics.tuple;

public class FiveTuple<A, B, C, D, E> extends FourTuple<A, B, C, D> {
	public final E firth;
	public FiveTuple(A a, B b, C c, D d, E e) {
		super(a, b, c, d);
		this.firth = e;
	}
	@Override
	public String toString() {
		return "FiveTuple [firth=" + firth + ", fourth=" + fourth + ", third="
				+ third + ", first=" + first + ", second=" + second + "]";
	}
	
}
