package io.cards.spring.exercises.simplejava;

import io.cards.spring.exercises.simplejava.service.MessagingService;
import io.cards.spring.exercises.simplejava.service.MessagingServiceFactory;

import java.io.IOException;
import java.util.Properties;

public class SimpleJava {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(SimpleJava.class.getResourceAsStream("/messaging.properties"));
        String messagingType = properties.getProperty("messaging.type");

        for (int i = 0; i < 5; i++) {
            MessagingService service = MessagingServiceFactory.createMessagingService(messagingType);
            System.out.println(service);
            service.sendMessage("simple java");
        }
    }
}
