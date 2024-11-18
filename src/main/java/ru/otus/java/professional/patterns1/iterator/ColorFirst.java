package ru.otus.java.professional.patterns1.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ColorFirst<T> implements Iterator<T> {
  private final Box box;
  private int countMatryoshkas;
  private final int itemsSize;
  private int countSize;

  public ColorFirst(Box box) {
    this.box = box;
    this.countMatryoshkas = 0;
    this.itemsSize = box.getRed().getItems().size();
    this.countSize = 0;
  }

  @Override
  public boolean hasNext() {
    return countMatryoshkas != 4;
  }

  @Override
  public T next() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }
    switch (countMatryoshkas) {
      case 0: {
        T item = (T) box.getRed().getItems().get(countSize++);
        if (countSize == itemsSize) {
          countSize = 0;
          countMatryoshkas++;
        }
        return item;
      }
      case 1: {
        T item = (T) box.getGreen().getItems().get(countSize++);
        if (countSize == itemsSize) {
          countSize = 0;
          countMatryoshkas++;
        }
        return item;
      }
      case 2: {
        T item = (T) box.getBlue().getItems().get(countSize++);
        if (countSize == itemsSize) {
          countSize = 0;
          countMatryoshkas++;
        }
        return item;
      }
      case 3: {
        T item = (T) box.getMagenta().getItems().get(countSize++);
        if (countSize == itemsSize) {
          countSize = 0;
          countMatryoshkas++;
        }
        return item;
      }
    }
    return null;
  }
}
