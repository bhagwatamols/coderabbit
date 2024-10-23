package com.splwg.cm.domain.messagesender;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyInvocation;
import org.glassfish.jersey.client.JerseyWebTarget;
import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;

import com.splwg.base.api.NetworkReporterUtil;
import com.splwg.base.messaging.impl.AbstractUrlBasedSender;
import com.splwg.base.messaging.impl.AbstractUrlBasedSenderDescriptor;
import com.splwg.base.messaging.impl.IAbstractUrlBasedSenderDescriptor;
import com.splwg.base.messaging.impl.IRealtimeSenderDescriptor;
import com.splwg.base.messaging.impl.http.Behaviors;
import com.splwg.base.messaging.impl.http.OAuthBehaviors;
import com.splwg.shared.logging.AggregatingLogger;
import com.splwg.shared.logging.Logger;
import com.splwg.shared.logging.LoggerFactory;









public class CmRealtimeHttpSender
  extends AbstractUrlBasedSender
{
  private CmRealtimeHttpSenderDescriptor senderDescriptor;
  private static Logger logger = LoggerFactory.getLogger(CmRealtimeHttpSender.class);
  
  private Behaviors behaviors;
  
  static  {
    dynamicUrlManager.defineParameter(AbstractUrlBasedSender.DynamicParameterType.XMLMSG.parameterName());
    dynamicUrlManager.defineParameter(AbstractUrlBasedSender.DynamicParameterType.PATH_PARAMS.parameterName());
    dynamicUrlManager.defineParameter(AbstractUrlBasedSender.DynamicParameterType.QUERY_PARAMS.parameterName());
  }


  
  public IRealtimeSenderDescriptor getNewDescriptor() { return new CmRealtimeHttpSenderDescriptor(); }



  
  public void intialize(IRealtimeSenderDescriptor descriptor) { this.senderDescriptor = (CmRealtimeHttpSenderDescriptor)descriptor; }



  
  protected IAbstractUrlBasedSenderDescriptor getSenderDescriptor() { return this.senderDescriptor; }



  
  public String send(String payload, Map<String, String> extraParameters, Map<String, List<String>> dynamicHttpHeaders) throws Exception {
    String method = this.senderDescriptor.getHttpMethod();
    Map<String, String> params = extraParameters;
    if (params == null) {
      params = new HashMap<String, String>();
    }
    if ("GET".equals(method)) {
      params.put("XMLMSG", payload);
    }
    
    URI uri = buildURI(params);
    String accessToken = null;
    AbstractUrlBasedSenderDescriptor.SecurityType securityType = this.senderDescriptor.getSecurityType();
    if (AbstractUrlBasedSenderDescriptor.SecurityType.OWSM == securityType) {
    	      this.behaviors = new CmOwsmBehaviors(this.senderDescriptor);
    } else if (AbstractUrlBasedSenderDescriptor.SecurityType.OAUTH == securityType) {
      if (this.senderDescriptor.isClientCredentialsGrantType()) {
        Behaviors behaviors = new CmOAuthClientCredentialsBehaviors(this.senderDescriptor);
        accessToken = getAccessTokenByClientCredentials(behaviors, params);
      } else {
        
        throw new IllegalArgumentException("GrantType: " + this.senderDescriptor
            .getOAuthClientGrantType() + " is not implemented");
      } 
      this.behaviors = new OAuthBehaviors(accessToken);
    } else {
      this.behaviors = new CmStandardBehaviors(this.senderDescriptor);
    } 

    
    JerseyClient client = (JerseyClient)this.behaviors.createClient();
    client.property("jersey.config.client.readTimeout", Integer.valueOf(this.senderDescriptor.getResponseTimeout()));
    client.property("jersey.config.client.connectTimeout", Integer.valueOf(this.senderDescriptor.getHttpTimeout()));
    
    JerseyWebTarget resource = client.target(uri);
    
    Invocation.Builder builder = setHeaders(resource, dynamicHttpHeaders);
    
    String clientResponse = null;
    if ("PUT".equals(method)) {
      clientResponse = sendUsingPut(builder, payload, uri);
    } else if ("POST".equals(method)) {
      clientResponse = sendUsingPost(builder, payload, uri);
    } else if ("DELETE".equals(method)) {
      clientResponse = sendUsingDelete(builder, uri);
    } else if ("GET".equals(method)) {
      clientResponse = sendUsingGet(builder, uri);
    } else if ("OPTIONS".equals(method) || "HEAD".equals(method)) {
      throw new IllegalArgumentException("Method: " + method + " is not implemented");
    } 
    return clientResponse;
  }
  
  private String getAccessTokenByClientCredentials(Behaviors behaviors, Map<String, String> params) throws Exception {
    JerseyClient client = (JerseyClient)behaviors.createClient();
    client.property("jersey.config.client.readTimeout", Integer.valueOf(this.senderDescriptor.getResponseTimeout()));
    client.property("jersey.config.client.connectTimeout", Integer.valueOf(this.senderDescriptor.getHttpTimeout()));
    
    String sUrl = this.senderDescriptor.getOAuthAccessTokenUrl();
    URI uri = buildURI(sUrl, params);
    
    JerseyWebTarget resource = client.target(uri);
    JerseyInvocation.Builder builder1 = resource.request();
    
    MultivaluedStringMap multivaluedStringMap = new MultivaluedStringMap();
    multivaluedStringMap.add("grant_type", "client_credentials");
    
    multivaluedStringMap.add("scope", this.senderDescriptor
        .getOAuthRestApiScope());
    
    String clientResponse = sendUsingPost(builder1, multivaluedStringMap, "", uri);
    JSONObject jsonObject = new JSONObject(clientResponse);
    return jsonObject.getString("access_token");
  }

  
  private Invocation.Builder setHeaders(JerseyWebTarget resource, Map<String, List<String>> dynamicHttpHeaders) {
    Invocation.Builder builder1 = resource.request();
    Map<String, List<String>> headersMap = allHttpHeaders(dynamicHttpHeaders);


    
    for (Iterator<String> keys = headersMap.keySet().iterator(); keys.hasNext(); ) {
      String key = (String)keys.next();
      List<String> headers = (List)headersMap.get(key);
      for (Iterator<String> values = headers.iterator(); values.hasNext();) {
        builder1 = builder1.header(key, values.next());
      }
    } 
    
    if (!headersMap.containsKey("Accept")) {
      builder1.accept(new String[] { "*/*" });
    }
    
    return builder1;
  }
  
  private String sendUsingGet(Invocation.Builder builder, URI uri) throws Exception {
    return (String)NetworkReporterUtil.executeNetworkCall("realTimeHttpSender:GET", uri.toASCIIString(), info -> {
          
          Invocation requestInvocation = builder.buildGet();
          Response clientResponse = invoke(requestInvocation);
          
          if (clientResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            logError(clientResponse);
          }
          
          String result = (String)clientResponse.readEntity(String.class);
          info.incrementBytesReceived(result.getBytes().length);
          return result;
        });
  }

  
  private String sendUsingPost(Invocation.Builder builder, MultivaluedMap<String, String> httpFormDataMap, String payload, URI uri) throws Exception {
    return (String)NetworkReporterUtil.executeNetworkCall("realTimeHttpSender:POST", uri.toASCIIString(), info -> {

          
          Invocation requestInvocation = builder.buildPost(Entity.form(buildForm(httpFormDataMap, payload)));
          
          info.incrementBytesSent(payload.getBytes().length);
          Response clientResponse = invoke(requestInvocation);
          
          if (clientResponse.getStatus() != Response.Status.OK.getStatusCode()) {
            logError(clientResponse);
          }
          String result = (String)clientResponse.readEntity(String.class);
          info.incrementBytesReceived(result.getBytes().length);
          return result;
        });
  }

  
  private String sendUsingPost(Invocation.Builder builder, String payload, URI uri) throws Exception {
    return (String)NetworkReporterUtil.executeNetworkCall("realTimeHttpSender:POST", uri.toASCIIString(), info -> {
          Invocation requestInvocation;
          
          if (!this.senderDescriptor.getHttpFormDataMap().isEmpty()) {
            requestInvocation = builder.buildPost(Entity.form(buildForm(payload)));
          } else {
            
            requestInvocation = builder.buildPost(Entity.entity(payload, MediaType.valueOf(this.senderDescriptor.getContentType())
                  .withCharset(this.senderDescriptor.getOutboundCharEncoding())));
          } 
          
          info.incrementBytesSent(payload.getBytes().length);
          Response clientResponse = invoke(requestInvocation);
          
          if (this.senderDescriptor.isOneWay() && clientResponse.getStatus() == Response.Status.ACCEPTED.getStatusCode()) {
            return (String)clientResponse.readEntity(String.class);
          }
          
          if (clientResponse.getStatus() != Response.Status.OK.getStatusCode() && clientResponse
            .getStatus() != Response.Status.CREATED.getStatusCode()) {
            logError(clientResponse);
          }
          String result = (String)clientResponse.readEntity(String.class);
          info.incrementBytesReceived(result.getBytes().length);
          return result;
        });
  }
  
  private String sendUsingPut(Invocation.Builder builder, String payload, URI uri) throws Exception {
    return (String)NetworkReporterUtil.executeNetworkCall("realTimeHttpSender:PUT", uri.toASCIIString(), info -> {
          
          Invocation requestInvocation = builder.buildPut(Entity.entity(payload, MediaType.valueOf(this.senderDescriptor.getContentType())
                .withCharset(this.senderDescriptor.getOutboundCharEncoding())));
          info.incrementBytesSent(payload.getBytes().length);
          Response clientResponse = invoke(requestInvocation);
          String responseText = null;
          
          if (clientResponse.getStatus() == Response.Status.OK.getStatusCode() || clientResponse
            .getStatus() == Response.Status.CREATED.getStatusCode()) {
            responseText = (String)clientResponse.readEntity(String.class);
          } else if (clientResponse.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            responseText = (String)clientResponse.readEntity(String.class);
          } else {
            logError(clientResponse);
          } 
          if (responseText != null) {
            info.incrementBytesReceived(responseText.getBytes().length);
          }
          return responseText;
        });
  }
  
  private String sendUsingDelete(Invocation.Builder builder, URI uri) throws Exception {
    Invocation requestInvocation = builder.buildDelete();
    
    return (String)NetworkReporterUtil.executeNetworkCall("realTimeHttpSender:DELETE", uri.toASCIIString(), info -> {
          
          Response clientResponse = invoke(requestInvocation);
          
          if (this.senderDescriptor.isOneWay() && clientResponse.getStatus() == Response.Status.ACCEPTED.getStatusCode()) {
            return (String)clientResponse.readEntity(String.class);
          }
          
          String responseText = null;
          if (clientResponse.getStatus() == Response.Status.OK.getStatusCode()) {
            responseText = (String)clientResponse.readEntity(String.class);
          } else if (clientResponse.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            responseText = (String)clientResponse.readEntity(String.class);
          } else {
            logError(clientResponse);
          } 
          if (responseText != null) {
            info.incrementBytesReceived(responseText.getBytes().length);
          }
          return responseText;
        });
  }
  
  private Response invoke(Invocation requestInvocation) {
    long startTime = System.nanoTime();
    try {
      AggregatingLogger.logCallStart(null, this.senderDescriptor.getSenderId());
      Response clientResponse = requestInvocation.invoke();
      AggregatingLogger.logCallCompletion(null, this.senderDescriptor.getSenderId(), startTime);
      return clientResponse;
    } catch (Exception e) {
      AggregatingLogger.logCallCompletion(null, this.senderDescriptor.getSenderId(), startTime, e);
      throw e;
    } 
  }

  
  private MultivaluedMap<String, String> buildForm(String payload) { return buildForm(this.senderDescriptor.getHttpFormDataMap(), payload); }

  
  private MultivaluedMap<String, String> buildForm(MultivaluedMap<String, String> httpFormDataMap, String payload) {
    MultivaluedStringMap multivaluedStringMap = new MultivaluedStringMap();

    
    Iterator<Map.Entry<String, List<String>>> multiValueIterator = httpFormDataMap.entrySet().iterator();
    while (multiValueIterator.hasNext()) {
      Map.Entry<String, List<String>> multiValueEntry = (Map.Entry)multiValueIterator.next();
      String key = (String)multiValueEntry.getKey();
      for (String singleValue : multiValueEntry.getValue()) {
        String value = singleValue;
//        int pos = singleValue.indexOf("@XMLMSG@");
//        if (pos >= 0) {
//          String preXml = singleValue.substring(0, pos);
//          String postXml = singleValue.substring(pos + "@XMLMSG@".length());
//          value = preXml + payload + postXml;
//        }
        
        
        if (null!=singleValue && singleValue.startsWith("@XMLMSG")) {
         
        	JSONObject payloadStr=null;
    		
    			try {
					payloadStr = new JSONObject(payload);
					JSONObject msgPay = payloadStr.getJSONObject("smsMessage");
	    			String k = singleValue.substring(1, singleValue.length());
	    			k = k.substring(0, k.length()-1);
	    			value= msgPay.getString(k);
	    			
				} catch (JSONException e) {
					
					e.printStackTrace();
				}
    			
    			
        	
          
        }
        
        multivaluedStringMap.add(key, value);
      } 
    } 
    return multivaluedStringMap;
  }

  
  private void logError(Response clientResponse) {
    String messageText = String.valueOf(clientResponse.getStatusInfo().getReasonPhrase()) + ", status code (" + clientResponse.getStatus() + "). Error message: " + getErrorMessage(clientResponse);
    if (logger.isDebugEnabled())
      logger.error(clientResponse); 
    throw new ResponseProcessingException(clientResponse, messageText);
  }
  
  private String getErrorMessage(Response clientResponse) {
    try {
      return Objects.toString(clientResponse.readEntity(String.class), "");
    } catch (Exception e) {
      logger.error("Unable to read error details from client response " + clientResponse, e);
      return "";
    } 
  }
}

