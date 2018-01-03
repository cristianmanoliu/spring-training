package io.cards.spring.exercises.homework_w1.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Message {

  private MessageId messageId;

  public Message(MessageId messageId) {
    this.messageId = messageId;
  }

  public MessageId getMessageId() {
    return messageId;
  }

  @Override
  public String toString() {
    return messageId.getId();
  }
}
