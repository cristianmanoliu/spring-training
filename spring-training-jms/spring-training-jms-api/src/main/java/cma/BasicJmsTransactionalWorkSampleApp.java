package cma;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicJmsTransactionalWorkSampleApp {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(BasicJmsQueueReceiverSampleApp.class);

  public static void main(String[] args) {
    BasicJmsTransactionalWorkSampleApp basicJmsTransactionalWorkSampleApp = new BasicJmsTransactionalWorkSampleApp();
    basicJmsTransactionalWorkSampleApp.run();
  }

  public void run() {
    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
        "tcp://localhost:61616");
    LOGGER.info("Got connectionFactory: {}", connectionFactory);

    Connection connection = null;
    try {
      connection = connectionFactory.createConnection();
      connection.start();
      Session session = connection.createSession(true, -1);

      MessageConsumer messageConsumer = session.createConsumer(session.createQueue("queue1"));

      Message message = messageConsumer.receive();
      LOGGER.info("Got message: {}", message);
      /*
      if (1 == 1) {
        throw new RuntimeException("SIMULATE ROLLBACK!");
      }
      */

      MessageProducer messageProducer = session.createProducer(session.createQueue("queue2"));
      messageProducer.send(message);

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
