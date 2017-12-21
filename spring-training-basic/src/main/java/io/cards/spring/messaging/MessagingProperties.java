package io.cards.spring.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("messaging.properties")
public class MessagingProperties {

  @Value("${messaging.type}")
  private MessagingType messagingType;

  public MessagingType getMessagingType() {
    return messagingType;
  }
}
