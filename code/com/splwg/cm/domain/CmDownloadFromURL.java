package com.splwg.cm.domain;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;

import com.splwg.base.api.businessObject.COTSInstanceList;
import com.splwg.base.api.businessObject.COTSInstanceListNode;
import com.splwg.base.api.businessObject.COTSInstanceNode;
import com.splwg.base.api.businessService.BusinessServiceDispatcher;
import com.splwg.base.api.businessService.BusinessServiceInstance;
import com.splwg.base.api.service.DataElement;
import com.splwg.base.domain.StandardMessages;
import com.splwg.base.domain.common.attachment.Attachment;
import com.splwg.base.domain.common.attachment.Attachment_DTO;
import com.splwg.base.domain.common.attachment.Attachment_Id;
import com.splwg.base.domain.common.businessObject.BusinessObject_Id;
import com.splwg.shared.common.ApplicationError;
import com.splwg.shared.common.Base64Encoder;
import com.splwg.shared.environ.ApplicationProperties;
import com.splwg.shared.logging.Logger;
import com.splwg.shared.logging.LoggerFactory;

/**
 * @author AmolBhagwat
 *
@QueryPage (program = CMATTACH, service = CMATTACH,
 *      body = @DataElement (contents = { @DataField (name = PK_VAL1)
 *                  , @DataField (name = ATTACHMENT_ID)
 *                  , @DataField (name = BO_DATA_AREA)
 *                  , @DataField (name = ATTACHMENT_FILE_NAME)}),
 *      actions = { "read"
 *            , "change"
 *            , "delete"
 *            , "add"},
 *      modules = { "demo"})
 */
public class CmDownloadFromURL extends CmDownloadFromURL_Gen {

	Logger logger = LoggerFactory.getLogger(CmDownloadFromURL.class);

	@Override
	protected void change(DataElement item) throws ApplicationError {

		String pkVal1 = item.get(STRUCTURE.PK_VAL1);
		String boArea = item.get(STRUCTURE.BO_DATA_AREA);
		String pkVfileName = item.get(STRUCTURE.ATTACHMENT_FILE_NAME);
		String splEbase = ApplicationProperties.getProperty("spl.runtime.environ.SPLEBASE");
		String attchmentId = item.get(STRUCTURE.ATTACHMENT_ID);
		logger.info("pkVal1" + pkVal1);
		logger.info("boArea" + boArea);
		logger.info("pkVfileName" + pkVfileName);
		logger.info("splEbase" + splEbase);
		logger.info("ATTACHMENT_ID" + attchmentId);
		String fileNameWithPath = splEbase + "\\" + pkVfileName;

		String boName = null;

		if (!isBlankOrNull(pkVfileName))
		{
			String ar[] = pkVfileName.split("\\.");
			boName = getBo(ar[1]);
		}

		try {
			logger.info("download start");
			downloadUsingNIO(boArea, ApplicationProperties.getProperty("spl.runtime.environ.SPLEBASE") + "\\"+pkVfileName);
			logger.info("download end");

			File d = new File(fileNameWithPath);
			if (notNull(d) && d.exists())
			{
				InputStream targetStream = new FileInputStream(d);
				byte b[] = getAttachmentData(targetStream);
				String attachmentData = toString(b);

				if (isBlankOrNull(attchmentId))
				{
					logger.info("add");
					Attachment_DTO dto = new Attachment_DTO();
					dto.setAttachmentFileName(pkVfileName);
					dto.setUserId(getActiveContextUser().getId());
					dto.setBusinessObjectId(new BusinessObject_Id(boName));
					dto.setPkVal1(pkVal1);
					dto.setAttachmentData(attachmentData);
					dto.newEntity();
					item.put(STRUCTURE.ATTACHMENT_ID, dto.getId().getTrimmedValue());
				}
				else
				{
					logger.info("update");
					Attachment_Id id = new Attachment_Id(attchmentId);
					Attachment attachment = id.getEntity();
					if (notNull(attachment))
					{
						Attachment_DTO dto = attachment.getDTO();
						if(!isBlankOrNull(pkVfileName))
						dto.setAttachmentFileName(pkVfileName);
						if(!isBlankOrNull(pkVal1))
						dto.setPkVal1(pkVal1);
						dto.setUserId(getActiveContextUser().getId());
						if(!isBlankOrNull(boName))
						dto.setBusinessObjectId(new BusinessObject_Id(boName));
						if(!isBlankOrNull(attachmentData))
						dto.setAttachmentData(attachmentData);
						attachment.setDTO(dto);
					}
				}
				
				logger.info("file delete start");
				d.deleteOnExit();
				logger.info("file delete end");
				
			}
			else {

				logger.info("File not found at path " + fileNameWithPath);
			}

		} catch (IOException e) {
			addError(StandardMessages.systemError(e));
		}

		logger.info("End of BS");
		setOverrideResultForChange(item);
	}

	private String getBo(String extension)
	{
		logger.info("get bo start" + extension);
		String boName = null;
		BusinessServiceInstance in = BusinessServiceInstance.create("F1-RetrieveAtchmtBOsForFileExt");
		in.set("attachmentFileExtension", extension);
		in = BusinessServiceDispatcher.execute(in);

		COTSInstanceList list = in.getList("results");
		if (notNull(list))
		{
			Iterator<COTSInstanceListNode> itr = list.iterator();
			while (itr.hasNext())
			{
				COTSInstanceNode n = itr.next();
				boName = n.getString("bo");
			}
		}
		logger.info("get bo end" + boName);
		return boName;
	}

	private void downloadUsingNIO(String urlStr, String file) throws IOException {
		URL url = new URL(urlStr);
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		rbc.close();
	}

	private String toString(byte[] attachmentDataBytes) {
		return Base64Encoder.encode(attachmentDataBytes);
	}

	private byte[] getAttachmentData(InputStream attachmentInputStream) throws IOException {

		long attachmentMaxByteSizeToAllow = 10000L;
		/* 325 */int totalBytesRead = 0;
		/* 326 */byte[] buffer = new byte[10240];
		/* 327 */int bytesRead = -1;
		/* 328 */ByteArrayOutputStream attachmentContents = new ByteArrayOutputStream();
		/* 329 */BufferedInputStream inputFileStream = new BufferedInputStream(attachmentInputStream);
		/*     */
		/* 331 */while ((bytesRead = inputFileStream.read(buffer)) > 0) {
			/* 332 */if (-1L != attachmentMaxByteSizeToAllow) {
				/* 333 */totalBytesRead += bytesRead;
				/*     */
				/* 335 */if (totalBytesRead > attachmentMaxByteSizeToAllow) {
					/* 336 */// logger.error("Attachment exceeds the max size configured "
								// + attachmentMaxByteSizeToAllow);
					/* 337 */// throw new
								// AttachmentPayloadTooLargeException(null);
					/*     */}
				/*     */}
			/*     */
			/* 341 */attachmentContents.write(buffer, 0, bytesRead);
			/*     */}
		/* 343 */return attachmentContents.toByteArray();

	}

}
