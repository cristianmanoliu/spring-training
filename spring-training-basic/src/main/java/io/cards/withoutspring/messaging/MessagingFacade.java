package io.cards.withoutspring.messaging;

import io.cards.withoutspring.MessagingServiceFactory;
import io.cards.withoutspring.messaging.service.MessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessagingFacade {

  public static final Logger LOGGER = LoggerFactory.getLogger(
      MessagingFacade.class);

  private ApplicationProperties applicationProperties;
  private MessagingService messagingService;

  public MessagingFacade(ApplicationProperties applicationProperties) {
    if (applicationProperties == null) {
      throw new IllegalArgumentException("Invalid applicationProperties provided");
    }
    this.applicationProperties = applicationProperties;
    this.messagingService = getMessagingService();
  }

  private MessagingService getMessagingService() {
    MessagingServiceFactory messagingServiceFactory = new MessagingServiceFactory();
    LOGGER.info("Got messagingServiceFactory: {}", messagingServiceFactory);
    return messagingServiceFactory
        .getMessagingService(applicationProperties.getMessagingType());
  }

  public void send(String message) {
    messagingService.sendMessage(message);
  }
}
