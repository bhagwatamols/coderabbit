package com.splwg.cm.domain;

import com.ibm.icu.math.BigDecimal;

public class CmComp {

	public static void main(String[] args) {

		BigDecimal a = new BigDecimal("0");
		BigDecimal b = new BigDecimal("1");

		System.out.println(a.compareTo(b)==1);
		
	}

}
