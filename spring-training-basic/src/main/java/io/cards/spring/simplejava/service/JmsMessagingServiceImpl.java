package io.cards.spring.simplejava.service;

public class JmsMessagingServiceImpl implements MessagingService {
    private static final JmsMessagingServiceImpl INSTANCE = new JmsMessagingServiceImpl();

    public static JmsMessagingServiceImpl getInstance() {
        return INSTANCE;
    }

    private JmsMessagingServiceImpl() {
    }

    @Override
    public void sendMessage(String message) {
        System.out.println("Sending JMS message: " + message);
    }

}
