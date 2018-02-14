package cma;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicJmsTopicDurableSubscriberSampleApp {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(BasicJmsTopicDurableSubscriberSampleApp.class);

  public static void main(String[] args) {
    BasicJmsTopicDurableSubscriberSampleApp basicJmsSenderSampleApp = new BasicJmsTopicDurableSubscriberSampleApp();
    basicJmsSenderSampleApp.run();
  }

  private void run() {
    TopicConnectionFactory topicConnectionFactory = new ActiveMQConnectionFactory(
        "tcp://localhost:61616");
    LOGGER.info("Got topicConnectionFactory: {}", topicConnectionFactory);

    TopicConnection topicConnection = null;
    try {
      topicConnection = topicConnectionFactory.createTopicConnection();
      topicConnection.setClientID("clientId-1");
      topicConnection.start();

      TopicSession pubSession =
          topicConnection.createTopicSession(false,
              Session.AUTO_ACKNOWLEDGE);

      Topic topic = pubSession.createTopic("topic1");

      TopicSubscriber topicSubscriber = pubSession
          .createDurableSubscriber(topic, "createDurableSubscriber");
      topicSubscriber.setMessageListener(message -> {
        LOGGER.info("Got message: {}", message);
      });

      while (true) {
        try {
          Thread.sleep(10000L);
        } catch (InterruptedException e) {
          LOGGER.error(e.getMessage(), e);
        }
      }
    } catch (JMSException e) {
      LOGGER.error(e.getMessage(), e);
    } finally {
      try {
        if (topicConnection != null) {
          topicConnection.close();
        }
      } catch (JMSException e) {
        LOGGER.error(e.getMessage(), e);
      }
    }

  }
}
