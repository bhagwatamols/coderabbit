package com.splwg.cm.domain;

import com.splwg.base.api.datatypes.Bool;
import com.splwg.base.api.datatypes.Date;
import com.splwg.base.api.sql.PreparedStatement;
import com.splwg.base.domain.common.currency.Currency;
import com.splwg.ccb.domain.admin.customerClass.CustomerClassBillCompletionAlgorithmSpot;
import com.splwg.ccb.domain.billing.bill.BillData;

/**
 *
@AlgorithmComponent (softParameters = { @AlgorithmSoftParameter (name = demoID, required = true, type = string)})
 */
public class CmDemoBillComp_Impl extends CmDemoBillComp_Gen implements CustomerClassBillCompletionAlgorithmSpot {

	@Override
	public void invoke() {
		// TODO Auto-generated method stub
		PreparedStatement s= null;
		s =createPreparedStatement("", "");
		s.e
	}

	@Override
	public Bool getAllowBillReopen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BillData getBillData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAccountingDate(Date arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBillData(BillData arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBillDate(Date arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCurrency(Currency arg0) {
		// TODO Auto-generated method stub

	}

}
