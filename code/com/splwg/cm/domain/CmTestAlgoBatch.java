package com.splwg.cm.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.splwg.base.api.batch.JobWork;
import com.splwg.base.api.batch.RunAbortedException;
import com.splwg.base.api.batch.StandardCommitStrategy;
import com.splwg.base.api.batch.ThreadAbortedException;
import com.splwg.base.api.batch.ThreadExecutionStrategy;
import com.splwg.base.api.batch.ThreadWorkUnit;
import com.splwg.base.api.businessObject.BusinessObjectDispatcher;
import com.splwg.base.api.businessObject.BusinessObjectInstance;
import com.splwg.base.api.businessObject.COTSInstanceNode;
import com.splwg.base.api.lookup.BusinessObjectOptionLookup;
import com.splwg.base.api.program.Displayer;
import com.splwg.base.api.program.standard.b.cbl.RecordCICBCBLB;
import com.splwg.base.api.program.standard.b.cbl.RecordCICBCBLBFragment;
import com.splwg.base.api.program.types.NumberField;
import com.splwg.base.api.program.types.StringField;
import com.splwg.base.domain.common.businessObject.BusinessObjectOption_DTO;
import com.splwg.base.domain.common.businessObject.BusinessObjectOption_Id;
import com.splwg.base.domain.common.businessObject.BusinessObject_Id;
import com.splwg.base.domain.common.data.YesNoStringField;
import com.splwg.base.program.sample.b.cbl.ProgramCIPBCBLB;
import com.splwg.base.support.context.ApplicationContext;
import com.splwg.base.support.context.ContextHolder;
import com.splwg.ccb.domain.billing.billSegment.BillSegment;
import com.splwg.ccb.domain.billing.data.FieldCICBSTT8;

/**
 * @author AmolBhagwat
 *
@BatchJob (modules = { "demo"},
 *      softParameters = { @BatchJobSoftParameter (entityName = cisDivision, name = cisDivision, type = entity)})
 */
public class CmTestAlgoBatch extends CmTestAlgoBatch_Gen {

	public JobWork getJobWork() {
		List<ThreadWorkUnit> twLit = new ArrayList<ThreadWorkUnit>();
		ThreadWorkUnit u = new ThreadWorkUnit();
		twLit.add(u);
		return createJobWorkForThreadWorkUnitList(twLit);
		//getParameters().getProcessDate();
	}

	public Class<CmTestAlgoBatchWorker> getThreadWorkerClass() {
		return CmTestAlgoBatchWorker.class;
	}

	public static class CmTestAlgoBatchWorker extends CmTestAlgoBatchWorker_Gen {

		private String s = "aaa";
		
		public ThreadExecutionStrategy createExecutionStrategy() {
			// TODO Auto-generated method stub
			
			BillSegment h;
			//h.getServiceAgreement();
			return new StandardCommitStrategy(this);
		}

		public boolean executeWorkUnit(ThreadWorkUnit unit) throws ThreadAbortedException, RunAbortedException {
			
//			BusinessObjectOption_Id id = new BusinessObjectOption_Id(new BusinessObject_Id("CMTWILIO"),BusinessObjectOptionLookup.constants.INACTIVE_ALGORITHM, BigInteger.ONE,"CMTESTPRE");
//			BusinessObjectOption_DTO dto = new BusinessObjectOption_DTO();
//			dto.setId(id);
//			dto.newEntity();
			
			//createPreparedStatement("Insert into F1_BUS_OBJ_OPT (BUS_OBJ_CD,BUS_OBJ_OPT_FLG,SEQ_NUM,BUS_OBJ_OPT_VAL,VERSION,OWNER_FLG) values ('CMTWILIO','F1IA',10,'CMTESTPRE',1,'CM')","").execute();
			
			
//			ApplicationContext context = ContextHolder.getContext();
//            context.flushCache("AlgorithmComponentCache");
//			
//			BusinessObjectInstance instance = BusinessObjectInstance.create("CMTWILIO");
//			COTSInstanceNode group = instance.getGroup("smsMessage");
//			group.set("XMLMSG1", "+15735703471");
//			group.set("XMLMSG2", "+918275455619");
//			group.set("XMLMSG3", "twilio ");
//			BusinessObjectDispatcher.add(instance);
//			
			System.out.println(s);
			getBatchNumber();
			return true;
		}
		
		
		
		
		@Override
		public void finalizeJobWork() throws Exception {
			// TODO Auto-generated method stub
			super.finalizeJobWork();
		}

	}

}
