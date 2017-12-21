package io.cards.spring.simplejava;

import io.cards.spring.simplejava.service.MessagingService;
import io.cards.spring.simplejava.service.MessagingServiceFactory;

public class Bootstrap {
    public static void main(String[] args) {
        String messagingType = PropertiesHelper.getProperty("messaging", "messaging.type");
        MessagingService service = MessagingServiceFactory.createMessagingService(messagingType);
        service.sendMessage("simple java");
    }
}
