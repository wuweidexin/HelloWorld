package com.chen.javase.thinkinginjava.bookexample.generics;

import java.util.Collection;
/**
 * 泛型的初步使用，通用类初始化生成器
 * @author chen
 *
 */
public class Generators {
	public static <T> Collection<T> fill(Collection<T> coll, Generator<T> gen, int n) {
		for(int i = 0; i < n; i ++) {
			coll.add(gen.next());
		}
		return coll;
	}
}
