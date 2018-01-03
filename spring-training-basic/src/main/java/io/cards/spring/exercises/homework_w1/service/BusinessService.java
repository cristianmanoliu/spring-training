package io.cards.spring.exercises.homework_w1.service;

import io.cards.spring.exercises.homework_w1.domain.Message;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {

  private MessageRepository messageRepository;

  public BusinessService(
      MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  public void process(Message message) {
    messageRepository.store(message);
  }
}
