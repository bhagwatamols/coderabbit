package com.splwg.cm.domain;

import com.splwg.base.api.batch.JobWork;
import com.splwg.base.api.batch.RunAbortedException;
import com.splwg.base.api.batch.StandardCommitStrategy;
import com.splwg.base.api.batch.ThreadAbortedException;
import com.splwg.base.api.batch.ThreadExecutionStrategy;
import com.splwg.base.api.batch.ThreadWorkUnit;

/**
 *
@BatchJob (modules = { "demo"},
 *      softParameters = { @BatchJobSoftParameter (name = billid, required = true, type = string)})
 */
public class CmDemoBatchJobTest extends CmDemoBatchJobTest_Gen {

	public JobWork getJobWork() {
		// TODO Auto-generated method stub
		
		
		return createJobWorkForThreadWorkUnitList(null);

	}

	public Class<CmDemoBatchJobTestWorker> getThreadWorkerClass() {
		return CmDemoBatchJobTestWorker.class;
	}

	public static class CmDemoBatchJobTestWorker extends CmDemoBatchJobTestWorker_Gen {

		public ThreadExecutionStrategy createExecutionStrategy() {
			// TODO Auto-generated method stub
			return new StandardCommitStrategy(this);
		}

		public boolean executeWorkUnit(ThreadWorkUnit unit) throws ThreadAbortedException, RunAbortedException {
			// TODO Auto-generated method stub
			return false;
		}

	}

}
