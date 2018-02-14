package cma.requestreply;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

@Configuration
@ComponentScan
public class RequestReplyConfiguration {

  @Bean
  public ConnectionFactory connectionFactory() {
    ConnectionFactory targetConnectionFactory = new ActiveMQConnectionFactory(
        "tcp://localhost:61616");
    CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
    cachingConnectionFactory.setTargetConnectionFactory(targetConnectionFactory);
    return cachingConnectionFactory;
  }

  @Bean
  public JmsTemplate jmsTemplate() {
    JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
    jmsTemplate.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
    jmsTemplate.setPubSubDomain(false);
    return jmsTemplate;
  }


  @Bean
  public DefaultMessageListenerContainer queueReq(
      @Qualifier("RequestMessageListener") MessageListener requestListenerService) {
    DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
    defaultMessageListenerContainer.setPubSubDomain(false);
    defaultMessageListenerContainer.setConnectionFactory(connectionFactory());
    defaultMessageListenerContainer.setConcurrency("1");
    defaultMessageListenerContainer.setDestinationName("queueReq");
    defaultMessageListenerContainer.setMessageListener(requestListenerService);
    defaultMessageListenerContainer.setAutoStartup(true);
    return defaultMessageListenerContainer;
  }

  @Bean
  public DefaultMessageListenerContainer queueResp(
      @Qualifier("ReplyMessageListener") MessageListener responseListenerService) {
    DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
    defaultMessageListenerContainer.setPubSubDomain(false);
    defaultMessageListenerContainer.setConnectionFactory(connectionFactory());
    defaultMessageListenerContainer.setConcurrency("1");
    defaultMessageListenerContainer.setDestinationName("queueResp");
    defaultMessageListenerContainer.setMessageListener(responseListenerService);
    defaultMessageListenerContainer.setAutoStartup(true);
    return defaultMessageListenerContainer;
  }

}
