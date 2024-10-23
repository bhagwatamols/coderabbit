package com.splwg.cm.domain;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.splwg.base.api.batch.JobWork;
import com.splwg.base.api.batch.RunAbortedException;
import com.splwg.base.api.batch.StandardCommitStrategy;
import com.splwg.base.api.batch.ThreadAbortedException;
import com.splwg.base.api.batch.ThreadExecutionStrategy;
import com.splwg.base.api.batch.ThreadWorkUnit;
import com.splwg.base.api.businessObject.BusinessObjectDispatcher;
import com.splwg.base.api.businessObject.BusinessObjectInstance;
import com.splwg.base.api.sql.PreparedStatement;
import com.splwg.base.api.sql.SQLResultRow;
import com.splwg.base.domain.StandardMessages;
import com.splwg.ccb.domain.common.c1Request.C1Request;
import com.splwg.shared.common.ApplicationError;
import com.splwg.shared.logging.Logger;
import com.splwg.shared.logging.LoggerFactory;

/**
 * @author Prasoon
 *
@BatchJob (modules = { "demo"})
 */
public class CmProcessMsgDtls extends CmProcessMsgDtls_Gen {

	Logger logger = LoggerFactory.getLogger(CmProcessMsgDtls.class);

	public JobWork getJobWork() {

		logger.info("batch getjob work start");
		ThreadWorkUnit unit = new ThreadWorkUnit();
		List<ThreadWorkUnit> ls = new ArrayList<ThreadWorkUnit>();
		ls.add(unit);
		
		C1Request r;
		//r.getEffectiveCharacteristic(arg0)

		return createJobWorkForThreadWorkUnitList(ls);
	}

	public Class<CmProcessMsgDtlsWorker> getThreadWorkerClass() {
		return CmProcessMsgDtlsWorker.class;
	}

	public static class CmProcessMsgDtlsWorker extends CmProcessMsgDtlsWorker_Gen {

		Logger logger = LoggerFactory.getLogger(CmProcessMsgDtlsWorker.class);

		public ThreadExecutionStrategy createExecutionStrategy() {

			return new StandardCommitStrategy(this);
		}

		public boolean executeWorkUnit(ThreadWorkUnit unit) throws ThreadAbortedException, RunAbortedException {

			logger.info("batch execute work unit start");

			BusinessObjectInstance in = BusinessObjectInstance.create("CMSENDGRIDOMSG");
			in = BusinessObjectDispatcher.add(in);

			String data = in.getString("xmlResponse");
			String outboundMsgId = in.getString("outboundMessageId");
			
			logger.info("outbound message id "+outboundMsgId);
			logger.info("outbound message data "+data);
			processData(data,outboundMsgId);

			logger.info("batch execute work unit end");
			return true;
		}

		private void processData(String data,String outboundMsgId)
		{
			logger.info("batch processData start");

			if (isBlankOrNull(data))
			{
				logger.info("batch processData null condition");
				return;
			}
			try
			{

				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(new InputSource(new StringReader(data)));
				doc.getDocumentElement().normalize();
				NodeList nodeList = doc.getElementsByTagName("messages");

				for (int itr = 0; itr < nodeList.getLength(); itr++)
				{
					Node node = nodeList.item(itr);
					Element eElement = (Element) node;

					String messageId = eElement.getElementsByTagName("msg_id").item(0).getTextContent();

					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					factory.setNamespaceAware(true);
					DocumentBuilder builder = factory.newDocumentBuilder();
					Document d = builder.newDocument();
					Node importedNode = d.importNode(node, true);
					d.appendChild(importedNode);

					TransformerFactory tf = TransformerFactory.newInstance();
					Transformer trans = tf.newTransformer();
					StringWriter sw = new StringWriter();
					trans.transform(new DOMSource(d), new StreamResult(sw));

					String messageData = sw.toString();

					boolean isMesgPresent = checkMessagePresent(messageId);
					if (isMesgPresent)
					{
						// replace data
						addOrUpdateData(false, messageId, messageData, null,outboundMsgId);

					}
					else
					{
						// insert data
						addOrUpdateData(true, messageId, messageData, null,outboundMsgId);

					}

				}
			} catch (ApplicationError | TransformerException | ParserConfigurationException | SAXException | IOException e)
			{
				logger.info("Exception");
				addError(StandardMessages.systemError(e));
			}

			logger.info("batch processData end");

		}

		private void addOrUpdateData(boolean isAdd, String messageId, String data, String acctId,String outMsg)
		{

			logger.info("addOrUpdateData start");
			if (isAdd)
			{
				logger.info("add  start");
				PreparedStatement stmt = null;
				StringBuilder insertQuery = new StringBuilder();
				insertQuery.append(" insert into CM_MESSAGE_DTLS(MSG_ID,MSG_DATA,ACCT_ID,OUTMSG_ID) values (:mid,:mdt,:actid,:omsg) ");
				try {

					stmt = createPreparedStatement(insertQuery.toString(), "insert scenario");
					stmt.setAutoclose(false);
					stmt.bindString("mid", messageId, "");
					stmt.bindString("mdt", data, "");
					stmt.bindString("actid", acctId, "");
					stmt.bindString("omsg", outMsg, "");
					stmt.execute();

				} catch (ApplicationError ae)
				{
					addError(ae.getServerMessage());
				} finally {
					if (notNull(stmt))
						stmt.close();
				}
				logger.info("add  end");
			}
			else
			{
				logger.info("update  start");

				PreparedStatement stmt = null;
				StringBuilder insertQuery = new StringBuilder();
				insertQuery.append(" update CM_MESSAGE_DTLS set MSG_DATA=:msg,acct_id =:actid, OUTMSG_ID=:omsg where MSG_ID = :mid ");
				try {

					stmt = createPreparedStatement(insertQuery.toString(), "update scenario");
					stmt.setAutoclose(false);
					stmt.bindString("mid", messageId, "");
					stmt.bindString("msg", data, "");
					stmt.bindString("actid", acctId, "");
					stmt.bindString("omsg", outMsg, "");
					stmt.execute();

				} catch (ApplicationError ae)
				{
					addError(ae.getServerMessage());
				} finally {
					if (notNull(stmt))
						stmt.close();
				}
				logger.info("update  end");
			}

			logger.info("addOrUpdateData end");
		}

		/**
		 * 
		 * @param messageId
		 * @return
		 */
		private boolean checkMessagePresent(String messageId)
		{
			boolean result = false;
			SQLResultRow row = null;
			PreparedStatement stmt = null;
			StringBuilder queryString = new StringBuilder();
			queryString.append("select 1 from CM_MESSAGE_DTLS where MSG_ID = :msg");// add
																					// sql
																					// query
			try {

				stmt = createPreparedStatement(queryString.toString(), "checkMessagePresent");
				stmt.setAutoclose(false);
				stmt.bindString("msg", messageId, "");

				row = stmt.firstRow();
				if (notNull(row))
				{
					result = true;
				}

			} catch (ApplicationError ae)
			{
				addError(ae.getServerMessage());
			} finally {
				if (notNull(stmt))
					stmt.close();
			}
			return result;
		}

	}

}
