package io.cards.spring.simplejava.service;

public class MessagingServiceFactory {

    public static MessagingService createMessagingService(String type) {
        switch (type) {
            case "JMS":
                return JmsMessagingServiceImpl.getInstance();
            case "KAFKA":
                return KafkaMessagingServiceImpl.getInstance();
            default:
                throw new UnsupportedOperationException("Invalid messaging type property");
        }
    }
}
