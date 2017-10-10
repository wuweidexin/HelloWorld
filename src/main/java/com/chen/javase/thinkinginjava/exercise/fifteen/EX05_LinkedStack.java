package com.chen.javase.thinkinginjava.exercise.fifteen;

public class EX05_LinkedStack<T> {
	private class Node{//去掉参数类型，依然能够访问到外部的参数类型，因此
		T item;
		Node next;
		Node(){
			item = null; next = null;
		}
		Node(T item, Node next){
			this.next = next;
			this.item = item;
		}
		boolean end(){
			return item == null && next == null;
		}
	}
	private Node top = new Node();
	public void push(T obj){
		top = new Node(obj, top);
	}
	public Object pop(){
		Object result = top.item;
		if(!top.end()) {
			top = top.next;
		}
		return result;
	}
	
	public static void main(String[] args) {
		EX05_LinkedStack<Integer> tem = new EX05_LinkedStack<Integer>();
		tem.push(12);
		Object obj = tem.pop();
		System.out.println(obj.getClass().getName());
	}
}
