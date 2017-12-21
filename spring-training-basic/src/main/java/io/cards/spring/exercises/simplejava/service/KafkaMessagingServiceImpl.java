package io.cards.spring.exercises.simplejava.service;

public class KafkaMessagingServiceImpl implements MessagingService {
    private static final KafkaMessagingServiceImpl INSTANCE = new KafkaMessagingServiceImpl();

    public static KafkaMessagingServiceImpl getInstance() {
        return INSTANCE;
    }

    private KafkaMessagingServiceImpl() {

    }

    @Override
    public void sendMessage(String message) {
        System.out.println("Sending Kafka message: " + message);

    }

}
