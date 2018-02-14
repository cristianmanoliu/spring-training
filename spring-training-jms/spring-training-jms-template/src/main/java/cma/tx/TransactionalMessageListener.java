package cma.tx;

import javax.jms.Message;
import javax.jms.MessageListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalMessageListener implements MessageListener {

  private JmsTemplate jmsTemplate;

  public TransactionalMessageListener(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  @Transactional(transactionManager = "jmsTransactionManager")
  @Override
  public void onMessage(Message message) {
    jmsTemplate.convertAndSend("queue_4", "message for queue");
    jmsTemplate.convertAndSend("queue_5", "message for queue");
    jmsTemplate.convertAndSend("queue_6", "message for queue");
    jmsTemplate.convertAndSend("queue_7", "message for queue");

   /* if(1==1) {
      throw new RuntimeException("oups!");
    }*/
  }
}
