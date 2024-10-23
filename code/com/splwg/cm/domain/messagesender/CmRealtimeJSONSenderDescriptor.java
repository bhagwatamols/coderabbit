package com.splwg.cm.domain.messagesender;

import com.splwg.base.messaging.impl.IRealtimeSender;
import com.splwg.base.messaging.impl.http.RealtimeHttpSenderDescriptor;
import java.util.List;

public class CmRealtimeJSONSenderDescriptor
		extends CmRealtimeHttpSenderDescriptor
{
	public IRealtimeSender createSender() {
		IRealtimeSender sender = new CmRealtimeJSONSender();
		sender.intialize(this);
		return sender;
	}

	private String getContentTypeFromHeaders() {
		List<String> headers = (List) getHttpHeaders().get("Content-Type");
		if (headers != null && !headers.isEmpty()) {
			return (String) headers.get(0);
		}
		return "application/json;charset=UTF-8";
	}

	protected void initializeContentType() {
		if (null == getContentType()) {
			String header = getContentTypeFromHeaders();
			String[] values = header.split(";", 2);
			switch (values.length) {
			case 1:
				this.contentType = values[0];
				this.outboundCharEncoding = "UTF-8";
				break;
			case 2:
				this.contentType = values[0];
				this.outboundCharEncoding = values[1].substring("charset=".length());
				break;
			}
		}
	}
}
