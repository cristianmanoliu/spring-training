package io.cards.spring.exercises.simplejava;

import io.cards.spring.exercises.simplejava.service.MessagingService;
import io.cards.spring.exercises.simplejava.service.MessagingServiceFactory;

import java.util.ResourceBundle;

public class SimpleJava {
    public static void main(String[] args) {
        String messagingType = ResourceBundle.getBundle("messaging").getString("messaging.type");

        for (int i = 0; i < 5; i++) {
            MessagingService service = MessagingServiceFactory.createMessagingService(messagingType);
            System.out.println(service);
            service.sendMessage("simple java");
        }
    }
}
