package io.cards.withoutspring;

import io.cards.withoutspring.messaging.MessagingType;
import io.cards.withoutspring.messaging.service.MessagingService;
import io.cards.withoutspring.messaging.service.impl.JmsMessagingService;
import io.cards.withoutspring.messaging.service.impl.KafkaMessagingService;

public class MessagingServiceFactory {

  public MessagingService getMessagingService(MessagingType messagingType) {
    switch (messagingType) {
      case JMS:
        return new JmsMessagingService();
      case KAFKA:
        return new KafkaMessagingService();
      default:
        throw new UnsupportedOperationException("Unsupported messaging type " + messagingType);
    }
  }
}
