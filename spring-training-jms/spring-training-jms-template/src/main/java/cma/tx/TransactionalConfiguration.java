package cma.tx;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableTransactionManagement
public class TransactionalConfiguration {

  @Bean
  public JmsTransactionManager jmsTransactionManager() {
    return new JmsTransactionManager(connectionFactory());
  }

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
    jmsTemplate.setPubSubDomain(false);
    return jmsTemplate;
  }

  @Bean
  public DefaultMessageListenerContainer defaultMessageListenerContainer(
      MessageListener messageListener) {
    DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
    defaultMessageListenerContainer.setPubSubDomain(false);
    defaultMessageListenerContainer.setConnectionFactory(connectionFactory());
    defaultMessageListenerContainer.setConcurrency("1");
    defaultMessageListenerContainer.setDestinationName("queue1");
    defaultMessageListenerContainer.setMessageListener(messageListener);
    defaultMessageListenerContainer.setTransactionManager(jmsTransactionManager());
    defaultMessageListenerContainer.setAutoStartup(true);
    return defaultMessageListenerContainer;
  }
}
