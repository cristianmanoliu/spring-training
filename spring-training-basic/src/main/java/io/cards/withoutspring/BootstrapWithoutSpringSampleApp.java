package io.cards.withoutspring;

import io.cards.withoutspring.messaging.ApplicationProperties;
import io.cards.withoutspring.messaging.MessagingFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootstrapWithoutSpringSampleApp {

  public static final Logger LOGGER = LoggerFactory.getLogger(
      BootstrapWithoutSpringSampleApp.class);

  public static void main(String[] args) {
    new BootstrapWithoutSpringSampleApp().start();
  }

  private void start() {
    ApplicationProperties applicationProperties = new ApplicationProperties("messaging.properties");
    LOGGER.info("Got applicationProperties: {}", applicationProperties);

    MessagingFacade messagingFacade = new MessagingFacade(applicationProperties);
    LOGGER.info("Got messagingFacade: {}", messagingFacade);
    messagingFacade.send("hi");
  }
}
