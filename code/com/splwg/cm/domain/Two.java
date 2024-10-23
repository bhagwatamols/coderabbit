package com.splwg.cm.domain;

public class Two {

	
	public static void main(String[] args) {
		One o= new One();
		System.out.println(o.b);
		System.out.println(o.a);
		o.a = 9;
		System.out.println(One.a);
	}
}
