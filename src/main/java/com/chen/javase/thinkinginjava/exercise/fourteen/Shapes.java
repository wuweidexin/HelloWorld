package com.chen.javase.thinkinginjava.exercise.fourteen;

import java.util.Arrays;
import java.util.List;

import org.apache.poi.util.SystemOutLogger;

/**
 * 14.3
 * 默认的图形类
 * @author junquan_chen
 *
 */
abstract class Shape{
	void draw(){
		System.out.println(this + ".draw()");
	}
	abstract public String toString();
}
class Circle extends Shape{

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "circle";
	}}
class Square extends Shape{

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Square";
	}
}
class Triangle extends Shape{

	@Override
	public String toString() {
		return "Triangle";
	}
	
}

class Rhomboid extends Shape{

	@Override
	public String toString() {
		return "Rhomboid";
	}
	
}
public class Shapes {
	public static String rotate(Shape shape){
		if(shape instanceof Circle){
			return "rotate circle";
		} else {
			return "not the circle cannot rotate";
		}
	}
	public static void main(String[] args) {
//		List<Shape> shapeList = Arrays.asList(
//		new Circle(), new Square(), new Triangle()
//		);
//		for(Shape shape : shapeList)
//			shape.draw();
//		Shape sh = new Rhomboid();
		/* 14.2.3
		 * java 集成支持向上转型，不能进行向下转型，不安全，向下转型
		 * 将会导致运行时错误
		 */
//		Circle c = (Circle) sh;
		
		/****************14.2.4************/
//		if(sh instanceof Circle) {
//			Circle c1 = (Circle) sh;
//		} else {
//			System.out.println("not the circle type...");
//		}
		/*****************14.2.5***********/
//		for(Shape shape : shapeList)
//			System.out.println(rotate(shape));
		
		if(args.length > 0) {
			String type = args[0];
			try {
				Class cl = Class.forName("com.chen.javase.thinkinginjava.exercise.fourteen."+type);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
