package com.splwg.cm.domain;

import java.util.HashMap;
import java.util.Map;

import com.splwg.base.api.ApplicationCache;
import com.splwg.base.api.GenericBusinessObject;
import com.splwg.base.api.datatypes.Date;
import com.splwg.base.domain.batch.batchRun.BatchRun;
import com.splwg.base.support.context.ContextHolder;
import com.splwg.base.web.context.WebApplicationContext;

public class CmDemoCache extends GenericBusinessObject implements ApplicationCache {

	
	private static CmDemoCache cmDemoCache= null;
	private static Map<String,String> cacheMap = null;
	
	@Override
	public void flush() {

		BatchRun r;
		
		
		cacheMap= null;
	}
	
	public CmDemoCache()
	{
		WebApplicationContext.getContext().registerCache(this);
		
	}
	

	public static void init()
	{
		synchronized (CmDemoCache.class) {
			if(null==cmDemoCache)
			{
				cmDemoCache= new CmDemoCache();
			}
			if(null==cacheMap)
			{
				cacheMap = new HashMap<String, String>();
				cacheMap.put("hi", "hi");
				//Date.fromIso(isoDateString);
			}
		}
	}
	
	
	@Override
	public String getName() {
		
		return "CmDemoCache";
	}

}
