package ru.otus.java.professional.patterns1.iterator;

import java.util.Iterator;

public final class Box {
  private final Matryoshka red;
  private final Matryoshka green;
  private final Matryoshka blue;
  private final Matryoshka magenta;

  public Matryoshka getRed() {
    return red;
  }

  public Matryoshka getGreen() {
    return green;
  }

  public Matryoshka getBlue() {
    return blue;
  }

  public Matryoshka getMagenta() {
    return magenta;
  }

  public Box(Matryoshka red, Matryoshka green, Matryoshka blue, Matryoshka magenta) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.magenta = magenta;
  }

  public Iterator<String> getSmallFirstIterator() {
    return new SmallFirst<>(this);
  }

  public Iterator<String> getColorFirstIterator() {
    return new ColorFirst<>(this);
  }
}
