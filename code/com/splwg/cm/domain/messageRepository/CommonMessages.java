/*
 ********************************************************************************************************************
 * Confidentiality Information:
 *
 * This module is the confidential and proprietary information of
 * APB; it is not to be copied, reproduced, or
 * transmitted in any form, by any means, in whole or in part,
 * nor is it to be used for any purpose other than that for which
 * it is expressly provided without the written permission of
 * APB.
 *******************************************************************************************************************    
 */

package com.splwg.cm.domain.messageRepository;

import com.splwg.base.domain.common.message.AbstractMessageRepository;

public class CommonMessages extends AbstractMessageRepository {

	// Static fields/initializers
	// ---------------------------------------------------------------------------
	public static final int MESSAGE_CATEGORY = 94000;

	protected CommonMessages() {
		super(MESSAGE_CATEGORY);
	}

	// These messages will be used for Bundle Import Batch Job - Start Add
	
	public static final int DIRECTORY_NOT_FOUND = 90001;
	public static final int NO_FILES_IN_DIRECTORY = 90002;
	public static final int ERROR_PROCESS_FILE = 90003;
	public static final int FILE_READ_ERROR = 90004;

	public static final int FILE_CNT_CREATE = 20000;
	public static final int INV_FILE = 20001;
	public static final int INV_DATA = 20002;
	public static final int CNT_WRITE = 20003;
	public static final int CNT_OPEN = 20004;
	public static final int CNT_CLOSE = 20005;
	
	
	
	public static final int PLEASE_CONTACT_IT_ADMIN = 20006;
	public static final int EMAIL_SENDER_CONFIG_NOT_FOUND = 20007;
	public static final int OVERDUE_PROCESS_INACTIVE = 20008;
	public static final int OVERDUE_PROCESS_LOG = 20009;
	public static final int EMAILID_NOT_FOUND = 20010;
	public static final int FILEPATH_SHOULD_START_WITH = 20011;
	public static final int CMPARENTID_NOT_FOUND=10007;	
	public static final int INVALID_ADDR=10008;
	public static final int EXCEPTION_CAUGHT=10009;
	
}

