package ru.otus.java.professional.streamapi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Application {
  public static void main(String[] args) {
    List<Task> tasks = new ArrayList<>();
    tasks.add(new Task(0L, "verification", "open"));
    tasks.add(new Task(1L, "preparation", "at work"));
    tasks.add(new Task(2L, "initialisation", "closed"));
    tasks.add(new Task(3L, "processing", "open"));
    tasks.add(new Task(4L, "loading", "at work"));
    tasks.add(new Task(5L, "conservation", "closed"));
    tasks.add(new Task(6L, "broadcast", "open"));

    // получение списка задач по статусу 'в работе'
    List<Task> result = tasks.stream()
            .filter(task -> task
                    .getStatus()
                    .equals("at work"))
            .toList();
    System.out.println("List by status 'at work':");
    System.out.println(result);
    // Количество задач со статусом 'закрыта'
    System.out.println("The number of tasks with the 'closed' status:");
    System.out.println(tasks.stream()
            .filter(task -> task.getStatus().equals("closed"))
            .count());
    // Проверка наличия задачи с указанным id=2 и отсутствие с id=99
    System.out.println("Availability of the task by id=2:");
    System.out.println(tasks.stream()
            .anyMatch(task -> task.getId() == 2L));
    System.out.println("Availability of the task by id=99:");
    System.out.println(tasks.stream()
            .anyMatch(task -> task.getId() == 99L));
    // Получение списка задач в отсортированном по статусу
    System.out.println("Getting a list of tasks sorted by status:");
    System.out.println(tasks.stream()
            .sorted(Comparator.comparing(Task::getStatus))
            .toList());
    // Объединение сначала в группы по статусам, а потом (внутри каждой
    // группы) в подгруппы четных и нечетных по ID.
    System.out.println("Grouping first into groups by status, and then (within each group) into subgroups of even and odd by ID:");
    Map<String, Map<Boolean, List<Task>>> result3 = tasks.stream()
            .collect(Collectors.groupingBy(Task::getStatus,
                    Collectors.partitioningBy(task -> task.getId() % 2 == 0)));
    List<Task> taOdd = result3.get("open").get(false);
    List<Task> taEven = result3.get("open").get(true);
    System.out.println(taOdd);
    System.out.println(taEven);
    // Разбивка на две группы: со статусом “Закрыто” и остальное
    System.out.println("Split into two groups: with the status 'Closed' and the rest:");
    Map<Boolean, List<Task>> result4 = tasks.stream()
            .collect(Collectors.partitioningBy(task -> task.getStatus().equals("closed")));
    List<Task> closed = result4.get(true);
    List<Task> rest = result4.get(false);
    System.out.println(closed);
    System.out.println(rest);

  }
}
