package io.cards.spring.exercises.model;

public class CustomBean {

  private int id;

  public CustomBean(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  @Override
  public String toString() {
    return "[" + this.hashCode() + "] - hey, my id is: " + String.valueOf(id);
  }
}
