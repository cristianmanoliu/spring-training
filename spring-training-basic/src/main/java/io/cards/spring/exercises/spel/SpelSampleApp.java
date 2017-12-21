package io.cards.spring.exercises.spel;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

public class SpelSampleApp {

  public static void main(String[] args) {
    new SpelSampleApp().run();
  }

  private void run() {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
        SpelSampleConfiguration.class);
    SpelSampleConfiguration spelSampleConfiguration = applicationContext
        .getBean(SpelSampleConfiguration.class);
    for (Integer aValue : spelSampleConfiguration.getSomeList()) {
      System.out.println("aValue = " + aValue);
    }
  }
}


@Configuration
@ComponentScan
class SpelSampleConfiguration {

  @Value("#{'${someList}'.split(',')}")
  private List<Integer> someList;

  public List<Integer> getSomeList() {
    return someList;
  }
}
