package io.cards.spring.messaging;

import io.cards.spring.messaging.service.MessagingService;
import io.cards.spring.messaging.service.impl.JmsMessagingService;
import io.cards.spring.messaging.service.impl.KafkaMessagingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class MessagingConfiguration {

  @Bean
  public MessagingService defaultMessagingService(MessagingProperties properties) {
    MessagingType type = properties.getMessagingType();
    switch (type) {
      case JMS:
        return new JmsMessagingService();
      case KAFKA:
        return new KafkaMessagingService();
      default:
        throw new UnsupportedOperationException("Unsupported messaging type " + type);
    }
  }
}
