package io.cards.spring.messaging;

import io.cards.spring.messaging.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessagingFacade {

    @Autowired
    private MessagingService messagingService;

    public void send(String message) {
        messagingService.sendMessage(message);
    }
}
