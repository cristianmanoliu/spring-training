package io.cards.spring;

import io.cards.spring.messaging.MessagingFacade;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Bootstrap {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MessagingConfiguration.class);
        MessagingFacade facade = context.getBean(MessagingFacade.class);
        facade.send("hi");
    }
}
