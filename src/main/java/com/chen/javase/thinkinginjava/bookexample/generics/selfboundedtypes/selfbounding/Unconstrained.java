package com.chen.javase.thinkinginjava.bookexample.generics.selfboundedtypes.selfbounding;

import com.chen.javase.thinkinginjava.bookexample.generics.selfboundedtypes.curiouslyrecurringgenerics.BasicHolder;


/**
 * @author chen
 * 通过参数限定泛型的类型，叫自己限定？
 */
public class Unconstrained {
	public static void main(String[] args) {
		BasicOther b = new BasicOther(),
				b2 = new BasicOther();
		b.set(new Other());
		Other other = b.get();
		b.f();
	}
}


class Other {

}
class BasicOther extends BasicHolder<Other>{}