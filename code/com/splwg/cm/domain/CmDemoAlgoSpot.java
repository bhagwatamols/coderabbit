package com.splwg.cm.domain;

import com.splwg.base.api.algorithms.AlgorithmSpot;
import com.splwg.ccb.domain.customerinfo.account.Account;

/**
 * @author AmolBhagwat
 *
@AlgorithmSpot (algorithmEntityValues = { "cmDemoAlgoSpot"})
 */
public interface CmDemoAlgoSpot extends AlgorithmSpot {

	public void setData(Account acct);
}
