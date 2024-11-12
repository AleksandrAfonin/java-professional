package ru.otus.java.professional.patterns1.iterator;

import java.util.List;

public final class Matryoshka {
  private final List<String> items;

  public List<String> getItems() {
    return items;
  }

  public Matryoshka(List<String> items) {
    this.items = items;
  }
}
