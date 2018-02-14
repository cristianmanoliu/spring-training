package cma.basic;

import javax.jms.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jms.core.JmsTemplate;

public class BasicSpringJmsSampleApp {

  private static final Logger LOGGER = LoggerFactory.getLogger(BasicSpringJmsSampleApp.class);

  public static void main(String[] args) {
    BasicSpringJmsSampleApp basicSpringJmsSampleApp = new BasicSpringJmsSampleApp();
    basicSpringJmsSampleApp.run();
  }

  private void run() {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
        BasicAppConfiguration.class);
    LOGGER.info("Got applicationContext: {}", applicationContext);

    JmsTemplate queueJmsTemplate = applicationContext
        .getBean("queueJmsTemplate", JmsTemplate.class);
    LOGGER.info("Got queueJmsTemplate: {}", queueJmsTemplate);

    // Using SimpleMessageConverter
    queueJmsTemplate.convertAndSend("queue9", "Hi there queue!");

    Object readMessageAsObject = queueJmsTemplate.receiveAndConvert("queue9");
    if (readMessageAsObject.getClass().isAssignableFrom(String.class)) {
      String readMessageAsString = (String) readMessageAsObject;
      LOGGER.info("Got readMessageAsString: {}", readMessageAsString);
    }

    queueJmsTemplate.convertAndSend("queue9", "Hi there queue!");

    Message readMessageAsJmsMessage = queueJmsTemplate.receive("queue9");
    LOGGER.info("Got readMessageAsJmsMessage: {}", readMessageAsJmsMessage);

    JmsTemplate topicJmsTemplate = applicationContext
        .getBean("topicJmsTemplate", JmsTemplate.class);
    LOGGER.info("Got topicJmsTemplate: {}", topicJmsTemplate);

    topicJmsTemplate.convertAndSend("topic9", "Hi there topic! - for message listener");
  }
}
