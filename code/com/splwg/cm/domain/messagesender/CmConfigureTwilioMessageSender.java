package com.splwg.cm.domain.messagesender;

import java.util.ArrayList;
import java.util.List;

import com.splwg.base.api.batch.JobWork;
import com.splwg.base.api.batch.RunAbortedException;
import com.splwg.base.api.batch.StandardCommitStrategy;
import com.splwg.base.api.batch.ThreadAbortedException;
import com.splwg.base.api.batch.ThreadExecutionStrategy;
import com.splwg.base.api.batch.ThreadWorkUnit;
import com.splwg.base.api.sql.PreparedStatement;
import com.splwg.shared.common.ApplicationError;
import com.splwg.shared.logging.Logger;
import com.splwg.shared.logging.LoggerFactory;

/**
 * @author pm
 *
@BatchJob (modules = { "demo"},
 *      softParameters = { @BatchJobSoftParameter (name = senderName, required = true, type = string)
 *            , @BatchJobSoftParameter (name = password, required = true, type = string)
 *            , @BatchJobSoftParameter (name = userName, required = true, type = string)
 *            , @BatchJobSoftParameter (name = httpUrl, required = true, type = string)})
 */
public class CmConfigureTwilioMessageSender extends CmConfigureTwilioMessageSender_Gen {

	public JobWork getJobWork() {
		List<ThreadWorkUnit> unitList = new ArrayList<ThreadWorkUnit>();
		ThreadWorkUnit unit = new ThreadWorkUnit();
		unitList.add(unit);
		return createJobWorkForThreadWorkUnitList(unitList);

	}

	public Class<CmConfigureTwilioMessageSenderWorker> getThreadWorkerClass() {
		return CmConfigureTwilioMessageSenderWorker.class;
	}

	public static class CmConfigureTwilioMessageSenderWorker extends CmConfigureTwilioMessageSenderWorker_Gen {

		Logger logger = LoggerFactory.getLogger(CmConfigureTwilioMessageSenderWorker.class);
		
		public ThreadExecutionStrategy createExecutionStrategy() {
			
			return new StandardCommitStrategy(this);
		}

		public boolean executeWorkUnit(ThreadWorkUnit unit) throws ThreadAbortedException, RunAbortedException {
			
			
			String sendName = getParameters().getSenderName();
			String password = getParameters().getPassword();
			String userName = getParameters().getUserName();
			String url = getParameters().getHttpUrl();
			
			logger.info("sender name"+sendName);
			logger.info("password"+password);
			logger.info("userName"+userName);
			logger.info("url"+url);
			
			logger.debug("sender name"+sendName);
			logger.debug("password"+password);
			logger.debug("userName"+userName);
			logger.debug("url"+url);
			
			
			updateDetails();
			
			
			
			return true;
		}
		
		private void updateDetails()
		{
			logger.info("update start");
			logger.debug("update start");
			PreparedStatement stmt = null;
			StringBuilder queryString = new StringBuilder();
			queryString.append(" begin ")
			.append(" update ci_xai_sndr_ctx set F1_CTXT_VAL_LONG=:hturl where  XAI_SENDER_ID=:tw and SENDER_CTXT_FLG='HTU1'; ")
			.append(" update ci_xai_sndr_ctx set F1_CTXT_VAL_LONG=:usr where  XAI_SENDER_ID=:tw and SENDER_CTXT_FLG='HTLU'; ")
			.append(" update ci_xai_sndr_ctx set F1_CTXT_VAL_LONG=:pswd where  XAI_SENDER_ID=:tw and SENDER_CTXT_FLG='HTLP'; ")
			.append(" end; ");
			try {
				stmt = createPreparedStatement(queryString.toString(), "updateDetails");
				stmt.setAutoclose(false);
				stmt.bindString("tw", getParameters().getSenderName(), "XAI_SENDER_ID");
				stmt.bindString("hturl", getParameters().getHttpUrl(), "F1_CTXT_VAL_LONG");
				stmt.bindString("usr", getParameters().getUserName(), "F1_CTXT_VAL_LONG");
				stmt.bindString("pswd", getParameters().getPassword(), "F1_CTXT_VAL_LONG");
				stmt.execute();
				
			} catch (ApplicationError ae)
			{
				addError(ae.getServerMessage());
			} finally {
				if (notNull(stmt))
					stmt.close();
			}
			
			logger.info("update end");
			logger.debug("update end");

		}

	}

}
