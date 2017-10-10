package com.chen.javase.thinkinginjava.bookexample.generics.tuple;
/**
 * 这个和三元组的实现是一样的
 * @author chen
 *
 * @param <A>
 * @param <B>
 * @param <C>
 * @param <D>
 */
public class FourTuple<A, B, C, D> extends ThreeTuple<A, B, C> {
	public final D fourth;
	
	public FourTuple(A a, B b, C c, D d) {
		super(a, b, c);
		this.fourth = d;
	}

	@Override
	public String toString() {
		return "FourTuple [fourth=" + fourth + ", third=" + third + ", first="
				+ first + ", second=" + second + "]";
	}
	
}
