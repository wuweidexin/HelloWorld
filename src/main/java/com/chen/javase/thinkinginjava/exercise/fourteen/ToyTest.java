package com.chen.javase.thinkinginjava.exercise.fourteen;

/**
 * 14.2.1-2
 * 无论什么时候想要引用一个类，都需要先得到这个类的引用
 * @author junquan_chen
 *
 */
interface HasBatteries{}
interface Waterproof{}
interface Shoots{}
interface Test{}
class Toy{
	Toy(){} // 如果将默认构造器注视掉，将会导致调用up.newInstance();进行初始化的时候失败
	Toy(int i){}
}
class FancyToy extends Toy implements HasBatteries, 
	Waterproof, Shoots, Test{
	FancyToy(){super(1);}
}
public class ToyTest {
	static void printInfo(Class cc){
		System.out.println(cc.getName() + "  " + cc.isInterface() );
		
		System.out.println("Simple name: " + cc.getSimpleName() );
		
		System.out.println("Canonical name: " + cc.getCanonicalName() );
	}
	
	public static void main(String[] args) {
		Class c = null;
		try {
			c = Class.forName("com.chen.javase.thinkinginjava.exercise.fourteen.FancyToy");
		} catch (Exception e) {
			System.out.println("Can't find FancyToy");
			System.exit(1);
		}
		printInfo(c);
		for(Class face : c.getInterfaces() ){
			printInfo(face);
		}
		Class up = c.getSuperclass();
		Object obj = null;
		
		try {
			obj = up.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		printInfo(obj.getClass());
	}
}
