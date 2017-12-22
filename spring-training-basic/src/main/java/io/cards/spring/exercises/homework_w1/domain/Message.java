package io.cards.spring.exercises.homework_w1.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SimplePojo {

  private PrimaryKey primaryKey;

  @Autowired
  public SimplePojo(PrimaryKey primaryKey) {
    this.primaryKey = primaryKey;
  }

  public PrimaryKey getPrimaryKey() {
    return primaryKey;
  }

  @Override
  public String toString() {
    return primaryKey.getId();
  }
}
