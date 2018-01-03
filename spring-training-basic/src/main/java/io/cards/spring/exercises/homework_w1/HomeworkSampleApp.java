package io.cards.spring.exercises.homework_w1;

import io.cards.spring.exercises.homework_w1.domain.Message;
import io.cards.spring.exercises.homework_w1.service.BusinessService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HomeworkSampleApp {

  public static void main(String[] args) {
    new HomeworkSampleApp().run(args);
  }

  private void run(String[] args) {
    int count = Integer.valueOf(args[0]);

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
        HomeworkConfiguration.class);

    BusinessService businessService = applicationContext.getBean(BusinessService.class);

    for (int i = 0; i < count; i++) {
      businessService.process(applicationContext.getBean(Message.class));
    }
  }

}
