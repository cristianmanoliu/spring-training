package cma.basic;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.SessionAwareMessageListener;

@Configuration
@ComponentScan
public class BasicAppConfiguration {

  @Bean
  public ConnectionFactory connectionFactory() {
    ConnectionFactory targetConnectionFactory = new ActiveMQConnectionFactory(
        "tcp://localhost:61616");
    CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
    cachingConnectionFactory.setTargetConnectionFactory(targetConnectionFactory);
    return cachingConnectionFactory;
  }

  @Bean
  public JmsTemplate queueJmsTemplate() {
    JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
    jmsTemplate.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
    jmsTemplate.setPubSubDomain(false);
    return jmsTemplate;
  }

  @Bean
  public JmsTemplate topicJmsTemplate() {
    JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
    jmsTemplate.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
    jmsTemplate.setPubSubDomain(true);
    return jmsTemplate;
  }

  @Bean
  public DefaultMessageListenerContainer defaultMessageListenerContainer(
      MessageListener simpleMessageListener) {
    DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
    defaultMessageListenerContainer.setPubSubDomain(false);
    defaultMessageListenerContainer.setConnectionFactory(connectionFactory());
    defaultMessageListenerContainer.setConcurrency("4");
    defaultMessageListenerContainer.setDestinationName("queue1");
    defaultMessageListenerContainer.setMessageListener(simpleMessageListener);
    defaultMessageListenerContainer.setAutoStartup(true);
    return defaultMessageListenerContainer;
  }

}
