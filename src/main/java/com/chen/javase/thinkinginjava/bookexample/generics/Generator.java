package com.chen.javase.thinkinginjava.bookexample.generics;
/**
 * 生成器接口
 * @author chen
 *
 * @param <T>
 */
public interface Generator<T> {
	T next();
}
