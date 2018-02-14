package cma.requestreply;

import cma.requestreply.client.RequestSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RequestReplySampleApp {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestReplySampleApp.class);

  public static void main(String[] args) {
    RequestReplySampleApp requestReplySampleApp = new RequestReplySampleApp();
    requestReplySampleApp.run();
  }

  private void run() {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
        RequestReplyConfiguration.class);
    LOGGER.info("Got applicationContext: {}", applicationContext);

    RequestSender requestSender = applicationContext.getBean(RequestSender.class);
    requestSender.doRequestReply();

  }
}
