package io.cards.withoutspring.messaging.service.impl;

import io.cards.withoutspring.messaging.service.MessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class JmsMessagingService implements MessagingService {

  public static final Logger LOGGER = LoggerFactory.getLogger(
      JmsMessagingService.class);

  @Override
  public void sendMessage(String message) {
    Assert.notNull(message, "Message is null");
    LOGGER.info("Sending JMS message: {}", message);
  }
}
