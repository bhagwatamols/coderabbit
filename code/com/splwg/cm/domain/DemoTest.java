package com.splwg.cm.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.splwg.base.api.sql.PreparedStatement;
import com.splwg.base.api.sql.SQLResultRow;
import com.splwg.shared.common.ApplicationError;

public class DemoTest {

	
	
	public static void main(String[] args) {
		
	 String s="2023-07-29";
	 String s1="2023-07-29-00.90.98";
	 System.out.println(s.replace("-", ""));
	 System.out.println(s1.replace("-", "").replace(".", ""));
		
	 
	 Date date = new Date();
     
     // Define the desired date format
     SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
     
     // Convert the date to the desired format
     String formattedDate = dateFormat.format(date);
     
     // Print the formatted date
     System.out.println("Formatted Date: " + formattedDate);
	 
	 
	}
}
