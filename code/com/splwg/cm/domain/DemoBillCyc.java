package com.splwg.cm.domain;

import com.splwg.base.api.datatypes.Date;

public class DemoBillCyc {

	public static void main(String[] args) {
		
		Date startDt = new Date (2022,1,1);
		String billCyc = "BKEM";
		
		
		for(int i=0;i<=10;i++)
		{
			
		Date endDate = startDt.addMonths(3).addDays(-1);	
		String insertStmt =
		"Insert into CI_BILL_CYC_SCH (BILL_CYC_CD,WIN_START_DT,WIN_END_DT,ACCOUNTING_DT,EST_DT,FREEZE_COMPLETE_SW,VERSION) values ('"+billCyc+"',to_date('"+startDt.toString()+"','YYYY-MM-DD'),to_date('"+endDate.toString()+"','YYYY-MM-DD'),to_date('"+endDate.toString()+"','YYYY-MM-DD'),null,'N',1);";
			
		System.out.println(insertStmt);
		startDt=endDate.addDays(1);
		}	
			
		
		
	}
	
}
