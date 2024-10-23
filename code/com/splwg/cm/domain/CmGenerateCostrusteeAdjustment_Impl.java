package com.splwg.cm.domain;

import java.util.List;

import com.ibm.icu.math.BigDecimal;
import com.splwg.base.api.datatypes.Date;
import com.splwg.ccb.domain.adjustment.adjustment.Adjustment;
import com.splwg.ccb.domain.admin.adjustmentType.AdjustmentType;
import com.splwg.ccb.domain.admin.adjustmentType.AdjustmentTypeGenerateAdjustmentAlgorithmSpot;
import com.splwg.ccb.domain.admin.adjustmentType.CustomCommonData;
import com.splwg.ccb.domain.admin.serviceAgreementType.ServiceAgreementType;
import com.splwg.ccb.domain.billing.billSegment.BillSegmentCalculationHeaderData;
import com.splwg.ccb.domain.common.characteristic.CharacteristicData;
import com.splwg.ccb.domain.customerinfo.account.Account;
import com.splwg.ccb.domain.customerinfo.serviceAgreement.SADistributionOverrideData;
import com.splwg.ccb.domain.customerinfo.serviceAgreement.ServiceAgreement;
import com.splwg.shared.logging.Logger;
import com.splwg.shared.logging.LoggerFactory;

/**
 * @author AmolBhagwat
 *
@AlgorithmComponent ()
 */
public class CmGenerateCostrusteeAdjustment_Impl extends CmGenerateCostrusteeAdjustment_Gen implements AdjustmentTypeGenerateAdjustmentAlgorithmSpot {

	Logger LOGGER = LoggerFactory.getLogger(CmGenerateCostrusteeAdjustment_Impl.class);
	
	@Override
	public void invoke() {
		long startTime = System.currentTimeMillis();

		// business logic 
		
		
		long endTime = System.currentTimeMillis();
		
		LOGGER.info("time taken "+ (endTime-startTime));
	}

	@Override
	public Account getAccount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getAccountingDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AdjustmentType getAdjustmentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBaseAmount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BillSegmentCalculationHeaderData> getBillSegmentCalculationHeaderData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getCalculationDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CharacteristicData> getCharacteristicData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceAgreement getServiceAgreement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceAgreementType getServiceAgreementType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAccount(Account arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAccountingDate(Date arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAdjustment(Adjustment arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAdjustmentType(AdjustmentType arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBaseAmount(BigDecimal arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBillSegmentCalculationHeaderData(List<BillSegmentCalculationHeaderData> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCalculationDate(Date arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCharacteristicData(List<CharacteristicData> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCustomCommonData(CustomCommonData arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSADistributionOverrideData(SADistributionOverrideData arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServiceAgreement(ServiceAgreement arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServiceAgreementType(ServiceAgreementType arg0) {
		// TODO Auto-generated method stub

	}

}
