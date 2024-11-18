package ru.otus.java.professional.patterns1.iterator;

import java.util.Iterator;
import java.util.List;

public class Application {
  public static void main(String[] args) {
    Matryoshka red = new Matryoshka(List.of("red0", "red1", "red2", "red3", "red4", "red5", "red6", "red7", "red8", "red9"));
    Matryoshka green = new Matryoshka(List.of("green0", "green1", "green2", "green3", "green4", "green5", "green6", "green7", "green8", "green9"));
    Matryoshka blue = new Matryoshka(List.of("blue0", "blue1", "blue2", "blue3", "blue4", "blue5", "blue6", "blue7", "blue8", "blue9"));
    Matryoshka magenta = new Matryoshka(List.of("magenta0", "magenta1", "magenta2", "magenta3", "magenta4", "magenta5", "magenta6", "magenta7", "magenta8", "magenta9"));

    Box box = new Box(red, green, blue, magenta);

    Iterator<String> it = box.getSmallFirstIterator();
    while (it.hasNext()) {
      System.out.println(it.next());
    }
    System.out.println();
    it = box.getColorFirstIterator();
    while (it.hasNext()) {
      System.out.println(it.next());
    }
  }
}
