package ru.otus.java.professional.reflectionandannotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TestRunner {
  public static void run(Class testSuiteClass) throws AnnotationsOfMethodsException, InvocationTargetException, IllegalAccessException, IllegalPriorityException {
    if (isClassDisabled(testSuiteClass)) {
      return;
    }

    Method[] methods = testSuiteClass.getDeclaredMethods();
    checkingAnnotationMarkup(methods);

    Map<Method, Integer> methodsMap = new HashMap<>();
    Method[] beforeAfterMethods = new Method[2];
    preparingForTesting(methods, beforeAfterMethods, methodsMap);

    runningTests(beforeAfterMethods, methodsMap);
  }

  private static void checkingAnnotationMarkup(Method[] methods) throws AnnotationsOfMethodsException, IllegalPriorityException {
    int countBeforeSuite = 0;
    int countAfterSuite = 0;
    for (Method m : methods) {
      if (m.isAnnotationPresent(BeforeSuite.class)) {
        countBeforeSuite++;
        if (m.isAnnotationPresent(Test.class)) {
          throw new AnnotationsOfMethodsException("Test and BeforeSuite annotations on the same method '" + m.getName() + "'");
        }
        if (m.isAnnotationPresent(AfterSuite.class)) {
          throw new AnnotationsOfMethodsException("BeforeSuite and AfterSuite annotations on the same method '" + m.getName() + "'");
        }
      }
      if (m.isAnnotationPresent(AfterSuite.class)) {
        countAfterSuite++;
        if (m.isAnnotationPresent(Test.class)) {
          throw new AnnotationsOfMethodsException("Test and AfterSuite annotations on the same method '" + m.getName() + "'");
        }
      }
      if (m.isAnnotationPresent(Test.class)) {
        int priority = m.getAnnotation(Test.class).priority();
        if (priority < 1 || priority > 10) {
          throw new IllegalPriorityException("The priority value of the method '" + m.getName() + "' is beyond acceptable values (1 - 10)");
        }
      }
    }
    if (countBeforeSuite > 1) {
      throw new AnnotationsOfMethodsException("The number of methods with a BeforeSuite annotation is more than one");
    }
    if (countAfterSuite > 1) {
      throw new AnnotationsOfMethodsException("The number of methods with an AfterSuite annotation is more than one");
    }
  }

  private static void preparingForTesting(
          Method[] methods,
          Method[] beforeAfterMethods,
          Map<Method, Integer> methodsMap
  ) {
    System.out.println("=== Preparing for testing ===");
    for (Method m : methods) {
      if (m.isAnnotationPresent(Disabled.class)) {
        System.out.println("The method '" + m.getName() + "': " + m.getAnnotation(Disabled.class).message());
        continue;
      }
      if (m.isAnnotationPresent(Test.class)) {
        methodsMap.put(m, m.getAnnotation(Test.class).priority());
        continue;
      }
      if (m.isAnnotationPresent(BeforeSuite.class)) {
        beforeAfterMethods[0] = m;
      }
      if (m.isAnnotationPresent(AfterSuite.class)) {
        beforeAfterMethods[1] = m;
      }
    }
  }

  private static void runningTests(
          Method[] beforeAfterMethods,
          Map<Method, Integer> methodsMap
  ) throws InvocationTargetException, IllegalAccessException {
    System.out.println("=== Running tests ===");
    if (beforeAfterMethods[0] != null) {
      beforeAfterMethods[0].invoke(null);
      System.out.println("Ok");
    }
    AtomicInteger successfulTests = new AtomicInteger();
    AtomicInteger filedTests = new AtomicInteger();

    methodsMap.entrySet().stream().sorted(Map.Entry.<Method, Integer>comparingByValue()
            .reversed()).forEach(m -> {
      try {
        System.out.print(m.getKey().getName() + ": ");
        m.getKey().invoke(null);
        System.out.println("Ok");
        successfulTests.getAndIncrement();
      } catch (IllegalAccessException | InvocationTargetException e) {
        System.out.println("Filed");
        filedTests.getAndIncrement();
      }
    });
    if (beforeAfterMethods[1] != null) {
      beforeAfterMethods[1].invoke(null);
      System.out.println("Ok");
    }
    System.out.println("=== Results ===");
    System.out.println("Successful tests: " + successfulTests);
    System.out.println("Filed tests: " + filedTests);
    System.out.println("Total tests: " + (successfulTests.intValue() + filedTests.intValue()));
  }

  private static boolean isClassDisabled(Class<?> testSuiteClass) {
    if (testSuiteClass.isAnnotationPresent(Disabled.class)) {
      System.out.println("Test class '" + testSuiteClass.getSimpleName() + "': " +
              testSuiteClass.getAnnotation(Disabled.class).message());
      return true;
    }
    return false;
  }
}
