package com.splwg.cm.domain.messagesender;


import com.splwg.base.messaging.impl.http.Behaviors;
import com.splwg.shared.common.Cryptography;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;



public class CmOAuthClientCredentialsBehaviors
  implements Behaviors
{
  private CmRealtimeHttpSenderDescriptor senderDescriptor;
  
  public CmOAuthClientCredentialsBehaviors(CmRealtimeHttpSenderDescriptor descriptor) { this.senderDescriptor = descriptor; }


  
  public Client createClient() {
    this.senderDescriptor.validateOAuthParams();
    HttpAuthenticationFeature httpAuthenticationFeature = HttpAuthenticationFeature.basic(this.senderDescriptor.getOAuthClientId(), 
        Cryptography.decryptIfNeeded(this.senderDescriptor.getOAuthClientSecret()));
    return ((ClientBuilder)ClientBuilder.newBuilder().register(httpAuthenticationFeature)).build();
  }
}

