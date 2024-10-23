package com.splwg.cm.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.splwg.base.api.businessObject.BusinessObjectInstance;
import com.splwg.base.api.lookup.BusinessObjectActionLookup;
import com.splwg.base.domain.common.businessObject.BusinessObject;
import com.splwg.base.domain.common.businessObject.PreprocessBusinessObjectRequestAlgorithmSpot;
import com.splwg.ccb.domain.banking.transactionFeed.transactionFeedAgg.TFMDBUtil;

/**
 * @author AmolBhagwat
 *
@AlgorithmComponent ()
 */
public class CmDemoTest_Impl extends CmDemoTest_Gen implements PreprocessBusinessObjectRequestAlgorithmSpot {

	@Override
	public void invoke() {
		//System.get
		
		
		
		CmDemoCache.init();
		
		
		
		java.sql.Connection con= com.splwg.ccb.domain.banking.transactionFeed.transactionFeedAgg.TFMDBUtil.getConnection();
		try {
			java.sql.PreparedStatement stmt = con.prepareStatement("insert into ab values('1')");
			stmt.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				

	}

	@Override
	public BusinessObjectInstance getRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAction(BusinessObjectActionLookup arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBusinessObject(BusinessObject arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRequest(BusinessObjectInstance arg0) {
		// TODO Auto-generated method stub

	}

}
