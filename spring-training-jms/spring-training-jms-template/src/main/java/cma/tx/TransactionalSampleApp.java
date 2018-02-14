package cma.tx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TransactionalSampleApp {

  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionalSampleApp.class);

  public static void main(String[] args) {
    TransactionalSampleApp transactionalSampleApp = new TransactionalSampleApp();
    transactionalSampleApp.run();
  }

  private void run() {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
        TransactionalConfiguration.class);
    LOGGER.info("Got applicationContext: {}", applicationContext);

  }
}
