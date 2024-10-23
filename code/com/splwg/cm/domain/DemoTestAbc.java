package com.splwg.cm.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.dom4j.Element;

import com.splwg.base.domain.common.dataArea.DataArea;
import com.splwg.base.support.scripting.XMLUtils;

public class DemoTestAbc {

	public static void main(String[] args) {
		List<Element> l = new ArrayList<Element>();
		//XMLUtils.formatXMLList(list)
		String u = l.toString();
		System.out.println(u);
		
		
		
		
		try {
		      File myObj = new File("D:\\xml.txt");
		      Scanner myReader = new Scanner(myObj);
		      String data =null;
		      while (myReader.hasNextLine()) {
		         data =data + myReader.nextLine();
		        
		      } 
		      
		      myReader.close();
		      
		      
		      StringBuilder output = new StringBuilder("to_clob('");
		        
		        for(int i=0;i<data.length();i++)
		        {
		        	
		        		if (i > 0 && (i % 31999 == 0)) {
		        			output.append("') || to_clob('");
		        	    }

		        		output.append(data.charAt(i));
		        	
		        }
		        
		        output.append("')");
		        System.out.println(output);
		      
		      
		      
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    } 
				
		
		
	}
	
}
