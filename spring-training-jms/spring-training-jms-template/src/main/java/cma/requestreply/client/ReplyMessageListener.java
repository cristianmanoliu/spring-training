package cma.requestreply.client;

import cma.requestreply.server.RequestMessageListener;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("ReplyMessageListener")
public class ReplyMessageListener implements MessageListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestMessageListener.class);

  @Override
  public void onMessage(Message message) {
    LOGGER.info("Got reply message: {}", message);
  }
}