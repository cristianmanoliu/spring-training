package io.cards.spring.messaging;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BasicSpringSampleApp {

  public static void main(String[] args) {
    new BasicSpringSampleApp().run();
  }

  private void run() {
    ApplicationContext context = new AnnotationConfigApplicationContext(
        MessagingConfiguration.class);

    MessagingFacade facade = context.getBean(MessagingFacade.class);
    facade.send("hi");
  }
}
