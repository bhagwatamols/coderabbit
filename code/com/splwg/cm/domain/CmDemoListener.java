package com.splwg.cm.domain;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.splwg.base.api.GenericBusinessObject;
import com.splwg.shared.logging.Logger;
import com.splwg.shared.logging.LoggerFactory;

public class CmDemoListener extends GenericBusinessObject implements ServletContextListener {
	Logger LOGGER = LoggerFactory.getLogger(CmDemoListener.class);

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		LOGGER.info("*********************start of new listener");
		CmDemoCache.init();
		LOGGER.info("*********************end of new listener");
	}
}
