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
    for(Performing thread : threads){
      thread.interrupt();
    }
  }

  private class Performing extends Thread {
    @Override
    public void run() {
      while (isRun) {
        try {
          Runnable task = tasks.take();
          task.run();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    }
  }

}
