package cma.requestreply.server;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service("RequestMessageListener")
public class RequestMessageListener implements MessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestMessageListener.class);

  private JmsTemplate jmsTemplate;

  public RequestMessageListener(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  @Override
  public void onMessage(Message requestMessage) {
    LOGGER.info("Got requestMessage: {}", requestMessage);

    try {
      jmsTemplate.send(requestMessage.getJMSReplyTo(), new MessageCreator() {
        @Override
        public Message createMessage(Session session) throws JMSException {
          TextMessage response = session.createTextMessage();
          response.setJMSCorrelationID(requestMessage.getJMSCorrelationID());
          response.setText("Reply for: " + ((TextMessage) requestMessage).getText());
          return response;
        }
      });
    } catch (JMSException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
