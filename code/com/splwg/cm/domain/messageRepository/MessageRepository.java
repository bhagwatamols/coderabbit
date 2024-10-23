/*
 ********************************************************************************************************************
 *                Confidentiality Information:
 *
 * This module is the confidential and proprietary information of
 * APB; it is not to be copied, reproduced, or
 * transmitted in any form, by any means, in whole or in part,
 * nor is it to be used for any purpose other than that for which
 * it is expressly provided without the written permission of
 * APB.
 *******************************************************************************************************************                                                               
 *                                                                
 * PROGRAM DESCRIPTION:                              
 * 
 *
 * Message Repository
 * 
 *                                                             
 **************************************************************************
 *                                                                
 * CHANGE HISTORY:                                                
 *                                                                
 * Date:           by:        Reason:                                     
 * YYYY-MM-DD      IN         Reason text.                                
 *           
 * 2023-02-08  NileshMarathe    Initial Version.
 * **************************************************************************/

package com.splwg.cm.domain.messageRepository;
import com.splwg.base.domain.common.message.MessageParameters;
import com.splwg.shared.common.ServerMessage;


public class MessageRepository extends CommonMessages {


	// ~ Static fields/initializers
	// ---------------------------------------------------------------------------
	private static MessageRepository instance;

	// ~ Methods
	// ----------------------------------------------------------------------------------------------
	static MessageRepository getInstance() {
		if (instance == null) {
			instance = new MessageRepository();
		}
		return instance;
	}

	// These messages will be used for Bundle Import Batch Job - Start Add
	public static ServerMessage directoryNotFound(String directoryPath) {
		MessageRepository repo = MessageRepository.getInstance();
		MessageParameters messageParameters = new MessageParameters();
		messageParameters.addRawString(directoryPath);
		return repo.getMessage(DIRECTORY_NOT_FOUND, messageParameters);
	}

	public static ServerMessage noFilesInDirectory(String fileDirectory) {
		MessageRepository repo = MessageRepository.getInstance();
		MessageParameters messageParameters = new MessageParameters();
		messageParameters.addRawString(fileDirectory);
		return repo.getMessage(NO_FILES_IN_DIRECTORY, messageParameters);
	}

	public static ServerMessage errorOccuredInFile(String fileName, String err) {
		MessageRepository repo = MessageRepository.getInstance();
		MessageParameters messageParameters = new MessageParameters();
		messageParameters.addRawString(fileName);
		messageParameters.addRawString(err);
		return repo.getMessage(ERROR_PROCESS_FILE, messageParameters);
	}

	public static ServerMessage notAbleToReadFile(String fileName) {
		MessageRepository repo = MessageRepository.getInstance();
		MessageParameters messageParameters = new MessageParameters();
		messageParameters.addRawString(fileName);
		return repo.getMessage(FILE_READ_ERROR, messageParameters);
	}

	/**
	 * Throw an error if not able to create file
	 *
	 * @param parameterName
	 *            - Parameter Name
	 * @return - ServerMessage
	 */
	public static ServerMessage cannotCreatFile(String parameterName) {
		MessageRepository repo = MessageRepository.getInstance();
		MessageParameters messageParameters = new MessageParameters();
		messageParameters.addRawString(parameterName);
		return repo.getMessage(FILE_CNT_CREATE, messageParameters);
	}

	/**
	 * Throw an error if file in invalid
	 *
	 * @param parameterName
	 *            - Parameter Name
	 * @return - ServerMessage
	 */
	public static ServerMessage invalidFile(String parameterName) {
		MessageRepository repo = MessageRepository.getInstance();
		MessageParameters messageParameters = new MessageParameters();
		messageParameters.addRawString(parameterName);
		return repo.getMessage(INV_FILE, messageParameters);
	}

	/**
	 * Throw an error if data is not valid
	 *
	 * @return - ServerMessage
	 */
	public static ServerMessage invalidData() {
		MessageRepository repo = MessageRepository.getInstance();
		return repo.getMessage(INV_DATA, null);
	}
	/**
	 * Throw an error if file write error
	 *
	 * @param parameterName
	 *            - Parameter Name
	 * @return - ServerMessage
	 */
	public static ServerMessage errorWritingFile(String parameterName) {
		MessageRepository repo = MessageRepository.getInstance();
		MessageParameters messageParameters = new MessageParameters();
		messageParameters.addRawString(parameterName);
		return repo.getMessage(CNT_WRITE, messageParameters);
	}
	/**
	 * Throw an error if not able to open file
	 *
	 * @param parameterName
	 *            - Parameter Name
	 * @return - ServerMessage
	 */
	public static ServerMessage cannotOpenFile(String parameterName) {
		MessageRepository repo = MessageRepository.getInstance();
		MessageParameters messageParameters = new MessageParameters();
		messageParameters.addRawString(parameterName);
		return repo.getMessage(CNT_OPEN, messageParameters);
	}
	/**
	 * Throw an error if not able to close file
	 *
	 * @param parameterName
	 *            - Parameter Name
	 * @return - ServerMessage
	 */
	public static ServerMessage cannotCloseFile(String parameterName) {
		MessageRepository repo = MessageRepository.getInstance();
		MessageParameters messageParameters = new MessageParameters();
		messageParameters.addRawString(parameterName);
		return repo.getMessage(CNT_CLOSE, messageParameters);
	}

	/**
	 * Throw an error if not able to close file
	 *
	 * @param parameterName
	 *            - Parameter Name
	 * @return - ServerMessage
	 */
	public static ServerMessage pleaseContactAdmin(String parameterName) {
		MessageRepository repo = MessageRepository.getInstance();
		MessageParameters messageParameters = new MessageParameters();
		messageParameters.addRawString(parameterName);
		return repo.getMessage(PLEASE_CONTACT_IT_ADMIN, messageParameters);
	}


	/**
	 * Throw an error if not able to close file
	 *
	 * @param parameterName
	 *            - Parameter Name
	 * @return - ServerMessage
	 */
	public static ServerMessage emailSenderConfigNotFound(String parameterName) {
		MessageRepository repo = MessageRepository.getInstance();
		MessageParameters messageParameters = new MessageParameters();
		messageParameters.addRawString(parameterName);
		return repo.getMessage(EMAIL_SENDER_CONFIG_NOT_FOUND, messageParameters);
	}

	/**
	 * Throw an error if not able to close file
	 *
	 * @param parameterName
	 *            - Parameter Name
	 * @return - ServerMessage
	 */
	public static ServerMessage overdueProcessIsInactive(String parameterName) {
		MessageRepository repo = MessageRepository.getInstance();
		MessageParameters messageParameters = new MessageParameters();
		messageParameters.addRawString(parameterName);
		return repo.getMessage(OVERDUE_PROCESS_INACTIVE, messageParameters);
	}

	/**
	 * Throw an error if not able to close file
	 *
	 * @param parameterName
	 *            - Parameter Name
	 * @return - ServerMessage
	 */
	public static ServerMessage createOverdueProcessLog() {
		MessageRepository repo = MessageRepository.getInstance();
		return repo.getMessage(OVERDUE_PROCESS_LOG);
	}

	/**
	 * Throw an error if CMPARENTID is null
	 *
	 * @param cmParentId
	 *        
	 * @return - ServerMessage
	 */
	public static ServerMessage cmParentIdNotFound(String cmParentId) {
		MessageRepository repo = MessageRepository.getInstance();
		MessageParameters messageParameters = new MessageParameters();
		messageParameters.addRawString(cmParentId);
		return repo.getMessage(CMPARENTID_NOT_FOUND, messageParameters);
	}

	public static ServerMessage cmAddressInvalid()
	{
		MessageRepository repo = MessageRepository.getInstance();
		return repo.getMessage(CMPARENTID_NOT_FOUND);
	}
	public static ServerMessage cmExceptionCaught()
	{
		MessageRepository repo = MessageRepository.getInstance();
		return repo.getMessage(EXCEPTION_CAUGHT);
	}

	public static ServerMessage emailIdNotFound(String parameterName) {
		MessageRepository repo = MessageRepository.getInstance();
		MessageParameters messageParameters = new MessageParameters();
		messageParameters.addRawString(parameterName);
		return repo.getMessage(EMAILID_NOT_FOUND,messageParameters);
	}

	public static ServerMessage filePathShouldStartWith(String parameterName) {
		MessageRepository repo = MessageRepository.getInstance();
		MessageParameters messageParameters = new MessageParameters();
		messageParameters.addRawString(parameterName);
		return repo.getMessage(FILEPATH_SHOULD_START_WITH);

	}
}