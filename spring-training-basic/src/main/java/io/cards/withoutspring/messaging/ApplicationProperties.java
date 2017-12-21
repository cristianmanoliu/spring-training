package io.cards.withoutspring.messaging;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationProperties {

  public static final Logger LOGGER = LoggerFactory.getLogger(
      ApplicationProperties.class);

  private MessagingType messagingType;

  public ApplicationProperties(String... classpathResources) {
    Properties properties = new Properties();
    for (String classpathResource : classpathResources) {
      InputStream propertyInputStream = this.getClass().getClassLoader()
          .getResourceAsStream(classpathResource);
      try {
        properties.load(propertyInputStream);
      } catch (IOException e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    init(properties);
  }

  private void init(Properties properties) {
    messagingType = MessagingType.valueOf(properties.getProperty("messaging.type"));
    LOGGER.trace("Got messagingType: {}", messagingType);
  }

  public MessagingType getMessagingType() {
    return messagingType;
  }
}
