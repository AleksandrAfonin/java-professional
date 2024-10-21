package ru.otus.java.professional.multithreading;

public class Application {
  public static void main(String[] args) throws InterruptedException {
    ThreadsPool threadsPool = new ThreadsPool(3);

    Runnable task1 = () -> {
      for (int i = 0; i < 10; i++) {
        try {
          Thread.sleep(300);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + "  Iteration: " + i);
      }
    };

    Runnable task2 = () -> {
      for (int i = 0; i < 10; i++) {
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + "  Iteration: " + i);
      }
    };

    Runnable task3 = () -> {
      for (int i = 0; i < 10; i++) {
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + "  Iteration: " + i);
      }
    };

    threadsPool.execute(task1);
    threadsPool.execute(task2);
    threadsPool.execute(task3);

    Thread.sleep(7000);
    threadsPool.shutdown();
    //threadsPool.execute(task1); // IllegalStateException
  }
}
