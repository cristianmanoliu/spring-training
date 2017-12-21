package io.cards.spring.exercises.scope;

import io.cards.spring.exercises.model.CustomBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

public class ScopeSampleApp {

  public static final Logger LOGGER = LoggerFactory.getLogger(
      ScopeSampleApp.class);

  public static void main(String[] args) {
    new ScopeSampleApp().run();
  }

  private void run() {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
        ScopeSampleConfiguration.class);

    CustomBean prototypeCustomBean;
    prototypeCustomBean = applicationContext.getBean("prototypeCustomBean", CustomBean.class);
    LOGGER.info("Got prototypeCustomBean: {}", prototypeCustomBean);

    prototypeCustomBean = applicationContext.getBean("prototypeCustomBean", CustomBean.class);
    LOGGER.info("Got prototypeCustomBean: {}", prototypeCustomBean);

    CustomBean singletonCustomBean;
    singletonCustomBean = applicationContext.getBean("singletonCustomBean", CustomBean.class);
    LOGGER.info("Got singletonCustomBean: {}", singletonCustomBean);

    singletonCustomBean = applicationContext.getBean("singletonCustomBean", CustomBean.class);
    LOGGER.info("Got singletonCustomBean: {}", singletonCustomBean);
  }
}

@Configuration
class ScopeSampleConfiguration {

  @Bean
  @Scope(BeanDefinition.SCOPE_PROTOTYPE)
  public CustomBean prototypeCustomBean() {
    return new CustomBean(1);
  }

  @Bean
  //@Scope(BeanDefinition.SCOPE_SINGLETON)
  public CustomBean singletonCustomBean() {
    return new CustomBean(2);
  }
}
