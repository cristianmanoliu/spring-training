package io.cards.spring.exercises.homework_w1.domain;

import java.util.UUID;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MessageId {

  private String id = UUID.randomUUID().toString();

  public String getId() {
    return id;
  }
}
