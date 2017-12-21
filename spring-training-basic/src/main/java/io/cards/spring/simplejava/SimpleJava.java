package io.cards.spring.simplejava;

import io.cards.spring.simplejava.service.MessagingService;
import io.cards.spring.simplejava.service.MessagingServiceFactory;

public class SimpleJava {
    public static void main(String[] args) {
        String messagingType = PropertiesHelper.getProperty("messaging", "messaging.type");
        MessagingService service = MessagingServiceFactory.createMessagingService(messagingType);

        for (int i = 0; i < 5; i++) {
            System.out.println(service);
            service.sendMessage("simple java");
        }
    }
}
