package io.cards.spring.messaging;

import io.cards.spring.messaging.service.MessagingService;
import org.springframework.stereotype.Service;

@Service
public class MessagingFacade {

    private MessagingService service;

    public MessagingFacade(MessagingService service) {
        this.service = service;
    }

    public void send(String message) {
        service.sendMessage(message);
    }
}
