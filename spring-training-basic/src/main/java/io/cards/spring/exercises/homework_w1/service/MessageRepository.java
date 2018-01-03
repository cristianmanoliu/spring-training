package io.cards.spring.exercises.homework_w1.service;

import io.cards.spring.exercises.homework_w1.domain.Message;

public interface MessageRepository {

  void store(Message message);
}
