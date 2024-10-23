package com.splwg.cm.domain.messagesender;


import com.splwg.base.messaging.impl.AbstractUrlBasedSenderDescriptor;
import com.splwg.base.messaging.impl.http.Behaviors;
import com.splwg.shared.common.Cryptography;
import com.splwg.shared.common.StringUtilities;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;


public class CmStandardBehaviors
  implements Behaviors
{
  private AbstractUrlBasedSenderDescriptor senderDescriptor;
  
  public CmStandardBehaviors(CmRealtimeHttpSenderDescriptor descriptor) { this.senderDescriptor = descriptor; }


  
  public Client createClient() {
    ClientBuilder clientBuilder = ClientBuilder.newBuilder();
    
    switch (this.senderDescriptor.getSecurityType()) {
      case NOTSET:
      case BASIC:
        if (StringUtilities.notEmptyOrBlank(this.senderDescriptor.getUserName())) {
          clientBuilder.register(HttpAuthenticationFeature.basic(this.senderDescriptor.getUserName(), 
                Cryptography.decryptIfNeeded(this.senderDescriptor.getPassword())));
        }
        break;
    } 


    
    return clientBuilder.build();
  }
}

