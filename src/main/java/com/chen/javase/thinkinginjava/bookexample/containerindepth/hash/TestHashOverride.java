package com.chen.javase.thinkinginjava.bookexample.containerindepth.hash;

import java.util.HashSet;
import java.util.Set;

/**
 * @author chen
 */
public class TestHashOverride {
	public static void main(String[] args) {
		//测试没有重写hashCode的
		RectangleNotOR r1 = new RectangleNotOR(3, 3);
		RectangleNotOR r2 = new RectangleNotOR(3, 5);
		RectangleNotOR r3 = new RectangleNotOR(3, 3);
		Set<RectangleNotOR> set = new HashSet<>();
		set.add(r1);
		set.add(r2);
		set.add(r3);
		System.out.println(set.size());
		//测试重写了
		Rectangle r11 = new Rectangle(3, 3);
		Rectangle r21 = new Rectangle(3, 5);
		Rectangle r31 = new Rectangle(3, 3);
		Set<Rectangle> set1 = new HashSet<>();
		set1.add(r11);
		set1.add(r21);
		set1.add(r31);
		System.out.println(set1.size());
	}
}
