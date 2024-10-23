package com.splwg.cm.domain.messagesender;


import com.splwg.base.messaging.impl.IRealtimeSenderDescriptor;
import com.splwg.base.messaging.impl.http.RealtimeHttpSender;
import com.splwg.base.messaging.impl.json.JsonFormatter;
import com.splwg.base.messaging.impl.json.JsonFormatterFactory;
import com.splwg.base.messaging.impl.json.RealtimeJSONSenderDescriptor;

import java.util.List;
import java.util.Map;


public class CmRealtimeJSONSender
  extends CmRealtimeHttpSender
{
	public IRealtimeSenderDescriptor getNewDescriptor() {
		return new CmRealtimeJSONSenderDescriptor();
	}

	public String send(String payload, Map<String, String> extraParameters, Map<String, List<String>> dynamicHttpHeaders) throws Exception {
		JsonFormatter formatter = JsonFormatterFactory.getInstance(extraParameters);
		String requestJson = formatter.xmlToJson(payload);
		String responseJSON = super.send(requestJson, extraParameters, dynamicHttpHeaders);
		return formatter.jsonToXml(responseJSON);
	}
}


