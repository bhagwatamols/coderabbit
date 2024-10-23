package com.splwg.cm.domain.messagesender;

import com.splwg.base.messaging.impl.AbstractUrlBasedSenderDescriptor;
import com.splwg.base.messaging.impl.IRealtimeSender;
import com.splwg.shared.common.StringUtilities;
import java.util.HashMap;
import java.util.Map;

public class CmRealtimeHttpSenderDescriptor
		extends AbstractUrlBasedSenderDescriptor
{
	public static final String CLIENT_CREDENTIALS_GRANT_TYPE = "client_credentials";
	public static final String OAUTH_GRANTY_TYPE = "grant_type";
	public static final String OAUTH_SCOPE = "scope";
	public static final String OAUTH_ACCESS_TOKEN = "access_token";
	protected String contentType;
	protected String outboundCharEncoding;
	private Map<String, String> httpFormDataMap = new HashMap();

	private String httpMethod;
	private String oAuthClientId;
	private String encryptedOAuthClientSecret;
	private String oAuthRestApiScope;
	private String oAuthGrantType;
	private String oAuthAccessTokenUrl;

	public IRealtimeSender createSender() {
		IRealtimeSender sender = new CmRealtimeHttpSender();
		sender.intialize(this);
		return sender;
	}

	public void initialize(Map<String, String> contextMap) {
		this.httpMethod = (String) contextMap.get("HTMT");
		this.outboundCharEncoding = (String) contextMap.get("ENCO");
		this.contentType = (String) contextMap.get("CNTY");

		String formData = (String) contextMap.get("HTFR");
		if (formData != null &&
				formData.indexOf("=") >= 1) {
			String[] data = formData.split("=");
			this.httpFormDataMap.put(data[0], data[1]);
		}

		initializeContentType();
		initializeOAuthParams(contextMap);
	}

	public String getOAuthClientId() {
		return this.oAuthClientId;
	}

	public String getOAuthClientSecret() {
		return this.encryptedOAuthClientSecret;
	}

	public String getOAuthClientGrantType() {
		return this.oAuthGrantType;
	}

	public String getOAuthRestApiScope() {
		return this.oAuthRestApiScope;
	}

	public String getOAuthAccessTokenUrl() {
		return this.oAuthAccessTokenUrl;
	}

	public String getHttpMethod() {
		return this.httpMethod;
	}

	public String getContentType() {
		return this.contentType;
	}

	public String getOutboundCharEncoding() {
		return this.outboundCharEncoding;
	}

	public void validateOAuthParams() {
		if (StringUtilities.isEmptyOrNull(this.oAuthClientId) ||
				StringUtilities.isEmptyOrNull(this.oAuthGrantType) ||
				StringUtilities.isEmptyOrNull(this.oAuthRestApiScope) ||
				StringUtilities.isEmptyOrNull(this.oAuthAccessTokenUrl)) {
			throw new IllegalArgumentException("Invalid OAuth Configuration: (  ClientId: " + this.oAuthClientId + "\n GrantType: " + this.oAuthGrantType + "\n RestApiScope: " + this.oAuthRestApiScope + "\n AccessTokenUrl: " + this.oAuthAccessTokenUrl);
		}
	}

	public boolean isClientCredentialsGrantType() {
		return "client_credentials".equals(this.oAuthGrantType);
	}

	protected void initializeContentType() {
		if (this.outboundCharEncoding == null) {
			this.outboundCharEncoding = "UTF-8";
		}

		if (this.contentType == null) {
			this.contentType = "text/xml";
		}
	}

	private void initializeOAuthParams(Map<String, String> contextMap) {
		this.oAuthClientId = (String) contextMap.get("OACI");

		this.encryptedOAuthClientSecret = (String) contextMap.get("OACS");
		if (this.encryptedOAuthClientSecret == null) {
			this.encryptedOAuthClientSecret = "null";
		}

		this.oAuthRestApiScope = (String) contextMap.get("OARS");
		this.oAuthGrantType = (String) contextMap.get("OAGT");
		this.oAuthAccessTokenUrl = (String) contextMap.get("OATL");
	}
}
