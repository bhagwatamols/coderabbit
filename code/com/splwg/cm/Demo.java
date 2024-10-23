package com.splwg.cm;

import java.util.HashSet;

public class Demo {
	
	
	
	 String name;
	    int age;
	    Demo(String name,int age)
	    {
	        this.name=name;
	        this.age=age;
	    }
	    public static void main(String[] args) {
	    	Demo c1= new Demo("John",20);
	    	Demo c2= new Demo("John",20);
	        HashSet	<Demo> customerSet=new HashSet<>();
	        customerSet.add(c1);
	        customerSet.add(c2);
	        System.out.println(customerSet.size());
	    }
}
