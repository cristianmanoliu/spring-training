package io.cards.spring.exercises.jsr330;

import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

public class JSR330SampleApp {

  public static final Logger LOGGER = LoggerFactory.getLogger(
      JSR330SampleApp.class);

  public static void main(String[] args) {
    new JSR330SampleApp().run();
  }

  private void run() {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
        JSR330SampleConfiguration.class);
    NamedService namedService = applicationContext.getBean(NamedService.class);
    LOGGER.info("Got namedService: {}", namedService);
  }

}

@Configuration
@ComponentScan()
class JSR330SampleConfiguration {

}

@Named
class NamedService {

  private NamedDependencyService namedDependencyService;

  @Inject
  public NamedService(NamedDependencyService namedDependencyService) {
    this.namedDependencyService = namedDependencyService;
  }

  public NamedDependencyService getNamedDependencyService() {
    return namedDependencyService;
  }
}

@Named
class NamedDependencyService {

}
