package io.cards.spring;


import io.cards.spring.messaging.MessagingFacade;
import io.cards.spring.messaging.service.MessagingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MessagingServiceTest {

    @Mock
    private MessagingService messagingService;

    @InjectMocks
    private MessagingFacade facade;

    @Before
    public void before() {
        facade = new MessagingFacade(messagingService);
    }

    @Test
    public void testSendMessage() {
        String message = "testMessage";

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        doNothing().when(messagingService).sendMessage(any());

        facade.send(message);

        verify(messagingService).sendMessage(captor.capture());
        assertEquals("Unexpected message", message, captor.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendMessageWithException() {
        doThrow(new IllegalArgumentException("error")).when(messagingService).sendMessage(any());
        facade.send("test");
    }
}
