package com.chen.javase.thinkinginjava.bookexample.generics.selfboundedtypes.curiouslyrecurringgenerics;
/**
 * @author chen
 */
public class CRGWithBasicHolder {
	public static void main(String[] args) {
		Subtype st1 = new Subtype(), st2 = new Subtype();
		st1.set(st2);
		Subtype st3 = st1.get();
		st1.f();
		//set和get的都是确切的类型
	}
}

class Subtype extends BasicHolder<Subtype>{}