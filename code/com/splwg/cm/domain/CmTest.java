package com.splwg.cm.domain;

import com.splwg.base.api.batch.CommitEveryUnitStrategy;
import com.splwg.base.api.batch.JobWork;
import com.splwg.base.api.batch.RunAbortedException;
import com.splwg.base.api.batch.ThreadAbortedException;
import com.splwg.base.api.batch.ThreadExecutionStrategy;
import com.splwg.base.api.batch.ThreadWorkUnit;
import com.splwg.base.domain.batch.batchJobQueue.BatchJobQueue;
import com.splwg.base.domain.common.attachment.Attachment_DTO;
import com.splwg.base.domain.common.businessObject.BusinessObject_Id;

/**
 * @author AmolBhagwat
 *
@BatchJob (modules = { "demo"})
 */
public class CmTest extends CmTest_Gen {

	public JobWork getJobWork() {
		
		return null;
	}

	void createtttahcment(String fileName,String attachmentData)
	{
		Attachment_DTO dto = new Attachment_DTO();
		dto.setAttachmentFileName(fileName);
		dto.setUserId(getActiveContextUser().getId());
		dto.setBusinessObjectId(new BusinessObject_Id("F1-WordDocument"));
		dto.setAttachmentData(attachmentData);
	//	dto.newEntity()
	//	BatchJobQueue
	}
	
	
	public Class<CmTestWorker> getThreadWorkerClass() {
		return CmTestWorker.class;
	}

	public static class CmTestWorker extends CmTestWorker_Gen {

		public ThreadExecutionStrategy createExecutionStrategy() {
			// TODO Auto-generated method stub
			return new CommitEveryUnitStrategy(this);
		}

		public boolean executeWorkUnit(ThreadWorkUnit unit)
				throws ThreadAbortedException, RunAbortedException {
			// TODO Auto-generated method stub
			return false;
		}

	}

}
