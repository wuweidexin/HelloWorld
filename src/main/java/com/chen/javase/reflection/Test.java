package com.chen.javase.reflection;


public class Test {
	public static void main(String[] args) throws Exception {
		Test t = new Test();
		t.reflect_field();
	}
	/**
	 * 反射获取属性
	 */
	public void reflect_field() throws Exception{
		CommonReflection<AddressbookBean> c = new CommonReflection<AddressbookBean>("com.chen.reflection.AddressbookBean");
		c.getFileds();
	}
	/**
	 * 反射执行方法
	 */
	public void invoke_method() {
		CommonReflection<Person> c = new CommonReflection<Person>("com.chen.reflection.Person");
//		c.getAllMethodsName();
//		c.excuteMethods("getPhone");
		c.excuteMethodsWithParam("getSum",new Class[]{double.class, double.class}, new Object[]{3.2,8});
	}
}
