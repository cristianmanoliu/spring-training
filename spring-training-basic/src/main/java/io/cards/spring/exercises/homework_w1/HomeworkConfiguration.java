package io.cards.spring.exercises.homework_w1;

import io.cards.spring.exercises.homework_w1.service.FileStoreRepository;
import io.cards.spring.exercises.homework_w1.service.HomeworkOneProperties;
import io.cards.spring.exercises.homework_w1.service.InMemoryStoreRepository;
import io.cards.spring.exercises.homework_w1.service.MessageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class HomeworkConfiguration {

  @Bean
  public MessageRepository messageRepository(HomeworkOneProperties homeworkOneProperties) {
    switch (homeworkOneProperties.getRepositoryType()) {
      case FILE: {
        return new FileStoreRepository();
      }
      case IN_MEMORY: {
        return new InMemoryStoreRepository();
      }
      default: {
        throw new IllegalArgumentException("Invalid repository type provided!");
      }
    }
  }
}
