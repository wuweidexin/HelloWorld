package com.chen.javase.thinkinginjava.exercise.fourteen;

/**
 * 主要研究【类型信息】
 * @author junquan_chen
 *
 */

class Candy{
	static {System.out.println("Loading Candy");}
}

class Gum{
	static{
		System.out.println("Loading Gum");
	}
}

class Cookie{
	static{
		System.out.println("Loading Cookie");
	}
}
public class SweetShop {
	public static void main(String[] args) {
		System.out.println("inside main");
		new Candy();
		System.out.println("Affer creating Candy");
		try {
			//找不到对应的Gum类，会报ClassNotFoundException
			Class.forName("Gum");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Couldn't find Gum");
		}
		System.out.println("After Class.forName(\"Gum\")");
		new Cookie();
		System.out.println("After creating Cookie");
	}
}




