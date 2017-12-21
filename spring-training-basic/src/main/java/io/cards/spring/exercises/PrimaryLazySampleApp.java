package io.cards.spring.exercises;

import io.cards.spring.exercises.model.CustomBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

public class PrimaryLazySampleApp {

  public static final Logger LOGGER = LoggerFactory.getLogger(
      PrimaryLazySampleApp.class);

  public static void main(String[] args) {
    new PrimaryLazySampleApp().run();
  }

  private void run() {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
        PrimaryLazySampleConfiguration.class);
    CustomBean customBean = applicationContext.getBean(CustomBean.class);
    LOGGER.info("Got customBean: {}", customBean);
  }

}

@Configuration
class PrimaryLazySampleConfiguration {

  @Bean
  @Lazy
  public CustomBean prototypeCustomBean() {
    return new CustomBean(1);
  }

  @Primary
  @Bean
  @Lazy
  public CustomBean singletonCustomBean() {
    return new CustomBean(2);
  }
}
