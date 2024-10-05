package ru.otus.java.professional.reflectionandannotation;

import java.lang.reflect.InvocationTargetException;

public class Application {
  public static void main(String[] args) throws AnnotationsOfMethodsException, InvocationTargetException, IllegalAccessException, IllegalPriorityException {
    TestRunner.run(TestSuite.class);
  }
}
