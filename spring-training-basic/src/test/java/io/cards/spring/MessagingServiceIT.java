package io.cards.spring;

import io.cards.spring.messaging.MessagingConfiguration;
import io.cards.spring.messaging.MessagingFacade;
import io.cards.spring.messaging.service.MessagingService;
import io.cards.spring.messaging.service.impl.JmsMessagingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MessagingConfiguration.class)
public class MessagingServiceIT {

    @Autowired
    private MessagingFacade messagingFacade;
    @Autowired
    private MessagingService service;

    @Test
    public void testSendMessage() {
        assertTrue("Unexpected MessagingService implementation", service instanceof JmsMessagingService);
        messagingFacade.send("test");
    }
}
