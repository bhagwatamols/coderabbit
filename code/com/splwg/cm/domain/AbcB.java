package com.splwg.cm.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AbcB {

	public static void main(String[] args) {
		
		
//		String x= "A,B";
//		
//		String[] a = x.split(",");
//		List<String> io= new ArrayList<String>();
//		io =Arrays.asList(a);
//		System.out.println(io.size());
//		if(io.size()>9)
//		{
//		System.out.println(""+ io.get(9));
//		}
		
		
		Object o = new String("u3");
		String value = (String)o;
		if(value.startsWith("03") || value.startsWith("04"))
		{
			System.out.println("true");
		}
		else 
		{
			System.out.println("false");
		}
	}
	
}
