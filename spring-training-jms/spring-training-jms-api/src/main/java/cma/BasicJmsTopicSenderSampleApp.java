package cma;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicJmsTopicSenderSampleApp {

  private static final Logger LOGGER = LoggerFactory.getLogger(BasicJmsTopicSenderSampleApp.class);

  public static void main(String[] args) {
    BasicJmsTopicSenderSampleApp basicJmsQueueSenderSampleApp = new BasicJmsTopicSenderSampleApp();
    basicJmsQueueSenderSampleApp.run();
  }

  private void run() {
    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
        "tcp://localhost:61616");
    LOGGER.info("Got connectionFactory: {}", connectionFactory);

    Connection connection = null;
    try {
      connection = connectionFactory.createConnection();
      connection.start();
      Session session = connection.createSession(true, -1);

      MessageProducer messageProducer = session.createProducer(session.createTopic("topic1"));

      TextMessage textMessage = session.createTextMessage();
      textMessage.setText("This message is for a topic!");

      messageProducer.send(textMessage);

      session.commit();
    } catch (JMSException e) {
      LOGGER.error(e.getMessage(), e);
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (JMSException e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
  }
}
