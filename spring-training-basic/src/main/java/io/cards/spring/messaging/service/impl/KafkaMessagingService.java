package io.cards.spring.messaging.service.impl;

import io.cards.spring.messaging.service.MessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class KafkaMessagingService implements MessagingService {
    public static final Logger LOGGER = LoggerFactory.getLogger(KafkaMessagingService.class);

    @Override
    public void sendMessage(String message) {
        Assert.notNull(message, "Message is null");
        LOGGER.info("Sending JMS message: {}", message);
    }
}
