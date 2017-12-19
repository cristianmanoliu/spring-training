package io.cards.spring;

import io.cards.spring.messaging.MessagingFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MessagingConfiguration.class)
public class MessagingServiceIT {

    @Autowired
    private MessagingFacade messagingFacade;

    @Test
    public void testSendMessage() {
        messagingFacade.send("test");
    }
}
