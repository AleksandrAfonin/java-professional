package ru.otus.java.professional.streamapi;

public class Task {
  private long id;
  private String title;
  private String status;

  public String getStatus() {
    return status;
  }

  public long getId() {
    return id;
  }

  public Task(long id, String title, String status) {
    this.id = id;
    this.title = title;
    this.status = status;
  }

  @Override
  public String toString() {
    return "Task{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", status='" + status + '\'' +
            '}';
  }
}
