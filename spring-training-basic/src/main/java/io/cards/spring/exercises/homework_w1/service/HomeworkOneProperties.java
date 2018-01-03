package io.cards.spring.exercises.homework_w1.service;

import io.cards.spring.exercises.homework_w1.RepositoryType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("homework_one.properties")
public class HomeworkOneProperties {

  @Value("${repositoryType}")
  private RepositoryType repositoryType;

  public RepositoryType getRepositoryType() {
    return repositoryType;
  }
}
