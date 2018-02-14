package cma;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicJmsQueueReceiverSampleApp {

  private static final Logger LOGGER = LoggerFactory.getLogger(BasicJmsQueueReceiverSampleApp.class);

  public static void main(String[] args) {
    BasicJmsQueueReceiverSampleApp basicJmsSenderSampleApp = new BasicJmsQueueReceiverSampleApp();
    basicJmsSenderSampleApp.run();
  }

  private void run() {
    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
        "tcp://localhost:61616");
    LOGGER.info("Got connectionFactory: {}", connectionFactory);

    Connection connection = null;
    try {
      connection = connectionFactory.createConnection();
      connection.start();
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

      MessageConsumer messageConsumer = session.createConsumer(session.createQueue("queue1"));

      // message.acknowledge();
      Message message = messageConsumer.receive();
      LOGGER.info("Got message: {}", message);

      // session.commit();
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
