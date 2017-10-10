package com.chen.javase.thinkinginjava.bookexample.generics.selfboundedtypes.argumentcovariance;
/**
 * @author chen
 * 这个是不是在设计数据库底层类的时候会用到呢
 */
public class CovariantReturnTypes {
	void test(DerivedGetter d) {
		Derived d2 = d.get();
	}
}

class Base{}

class Derived extends Base{}

interface OrdinaryGetter{
	Base get();
}

interface DerivedGetter extends OrdinaryGetter{
	Derived get();//会覆盖 OrdinaryGetter中的get方法
}