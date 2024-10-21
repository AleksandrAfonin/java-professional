package ru.otus.java.professional.multithreading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadsPool {
  boolean isRun;
  private final BlockingQueue<Runnable> tasks;
  private final Performing[] threads;

  public ThreadsPool(int threadsNumber) {
    this.isRun = true;
    this.tasks = new LinkedBlockingQueue<>();
    this.threads = new Performing[threadsNumber];

    for (int i = 0; i < threads.length; i++) {
      threads[i] = new Performing();
      threads[i].start();
    }
  }

  public void execute(final Runnable task) {
    if (!isRun) {
      throw new IllegalStateException("Pool is shutdown");
    }
    if (task == null) {
      return;
    }
    tasks.add(task);
  }

  public void shutdown() {
    isRun = false;
  }

  private class Performing extends Thread {
    private Runnable task = null;

    @Override
    public void run() {
      while (isRun) {
        task = tasks.poll();
        if (task != null) {
          task.run();
        }
      }
    }
  }

}
