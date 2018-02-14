package cma;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicJmsListenerSampleApp {

  private static final Logger LOGGER = LoggerFactory.getLogger(BasicJmsListenerSampleApp.class);

  public static void main(String[] args) {
    BasicJmsListenerSampleApp basicJmsSenderSampleApp = new BasicJmsListenerSampleApp();
    basicJmsSenderSampleApp.run();
  }

  private void run() {
    init();
  }

  private void init() {
    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
        "tcp://localhost:61616");
    LOGGER.info("Got connectionFactory: {}", connectionFactory);

    Connection connection = null;
    try {
      connection = connectionFactory.createConnection();
     // connection.start();
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

      MessageConsumer messageConsumer = session.createConsumer(session.createQueue("queue1"));
      MessageListener messageListener = message -> {
        LOGGER.info("Got message: {}", message);
      };
      messageConsumer.setMessageListener(messageListener);

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
        if (connection != null) {
          connection.close();
        }
      } catch (JMSException e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
  }

}
