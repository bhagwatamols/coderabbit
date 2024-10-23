package com.splwg.cm.domain;

import java.util.ArrayList;
import java.util.List;

import com.splwg.base.api.batch.JobWork;
import com.splwg.base.api.batch.RunAbortedException;
import com.splwg.base.api.batch.StandardCommitStrategy;
import com.splwg.base.api.batch.ThreadAbortedException;
import com.splwg.base.api.batch.ThreadExecutionStrategy;
import com.splwg.base.api.batch.ThreadWork;
import com.splwg.base.api.batch.ThreadWorkUnit;
import com.splwg.base.api.businessObject.BusinessObjectDispatcher;
import com.splwg.base.api.businessObject.BusinessObjectInstance;
import com.splwg.base.api.businessObject.COTSInstanceList;
import com.splwg.base.api.businessObject.COTSInstanceNode;
import com.splwg.base.api.lookup.OutboundMessageProcessingMethodLookup;

/**
 * @author AmolBhagwat
 *
@BatchJob (modules = { "demo"})
 */
public class CmTestOutBound extends CmTestOutBound_Gen {

	public JobWork getJobWork() {
		List<ThreadWorkUnit> threadWorkUnits = new ArrayList<ThreadWorkUnit>();

		ThreadWorkUnit unit = new ThreadWorkUnit();
		threadWorkUnits.add(unit);
		
		return createJobWorkForThreadWorkUnitList(threadWorkUnits);

	}

	public Class<CmTestOutBoundWorker> getThreadWorkerClass() {
		return CmTestOutBoundWorker.class;
	}

	public static class CmTestOutBoundWorker extends CmTestOutBoundWorker_Gen {

		public ThreadExecutionStrategy createExecutionStrategy() {
			// TODO Auto-generated method stub
			return new StandardCommitStrategy(this);
		}

		public boolean executeWorkUnit(ThreadWorkUnit unit) throws ThreadAbortedException, RunAbortedException {

			BusinessObjectInstance ints = BusinessObjectInstance.create("CM-ERSCorrespondenceEmail");
			
			ints.set("externalSystem", "CM-EMAIL");
			ints.set("outboundMessageType", "CM-ERSCOR");
			ints.set("processingMethod",OutboundMessageProcessingMethodLookup.constants.REAL_TIME);
			
			COTSInstanceNode edoc = ints.getGroup("emailDocument");
			COTSInstanceNode from = edoc.getGroup("from");
			COTSInstanceNode internetAddress = from.getGroup("internetAddress");
			internetAddress.set("address", "amol.bhagwat@riaadvisory.com");
			
			COTSInstanceList to= edoc.getList("to");
			COTSInstanceNode toCh = to.newChild();
			COTSInstanceNode toChGr = toCh.getGroup("internetAddress");
			
			toChGr.set("address", "amol.bhagwat@riaadvisory.com");
			
			
			
			COTSInstanceNode subject = edoc.getGroup("subject");
			subject.set("text", "Hi ");
			
			COTSInstanceNode msgText = edoc.getGroup("messageText");
			msgText.set("text", "Hi ");
			
			ints = BusinessObjectDispatcher.add(ints);
			
			
			
			return true;
		}

	}

}
