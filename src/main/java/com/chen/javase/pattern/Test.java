package com.chen.javase.pattern;

public class Test {
	public static void main(String[] args) {
		String phone = "+8618028752937";
		String phone1 = "18028752937";
		String tp = "0775-49891621";
		String tp1 = "0775-4989162-112";
		System.out.println(RegexUtils.checkMobile(phone));
		System.out.println(RegexUtils.checkMobile(phone1));
		System.out.println(RegexUtils.checkPhone(tp));
		System.out.println(RegexUtils.checkPhone(tp1));
	}
}
