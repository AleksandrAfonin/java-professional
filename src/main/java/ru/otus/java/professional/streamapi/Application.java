package ru.otus.java.professional.streamapi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Application {
  static List<Task> tasks;

  public static void main(String[] args) {
    init();

    // получение списка задач по статусу 'в работе'
    System.out.println("List by status 'at work':");
    System.out.println(getListTasksByAtWorkStatus(tasks) + "\n");

    // Количество задач со статусом 'закрыта'
    System.out.println("The number of tasks with the 'closed' status:");
    System.out.println(getNumberOfTasksWithClosedStatus(tasks) + "\n");

    // Проверка наличия задачи с указанным id=2 и отсутствие с id=99
    System.out.println("Availability of the task by id=2:");
    System.out.println(isAvailableTask(tasks, 2L));
    System.out.println("Availability of the task by id=99:");
    System.out.println(isAvailableTask(tasks, 99L) + "\n");

    // Получение списка задач в отсортированном по статусу
    System.out.println("Getting a list of tasks sorted by status:");
    System.out.println(getListOfTasksSortedByStatus(tasks) + "\n");

    // Объединение сначала в группы по статусам, а потом (внутри каждой
    // группы) в подгруппы четных и нечетных по ID.
    System.out.println("Grouping first into groups by status, and then (within each group) into subgroups of even and odd by ID:");
    System.out.println(groupingByStatusAndSubgroupsOfEvenOddById(tasks, Status.OPEN, false));
    System.out.println(groupingByStatusAndSubgroupsOfEvenOddById(tasks, Status.OPEN, true) + "\n");

    // Разбивка на две группы: со статусом “Закрыто” и остальное
    System.out.println("Split into two groups: with the status 'Closed' and the rest:");
    System.out.println(splitIntoTwoGroupsWithStatusAndRest(tasks, Status.CLOSED, true));
    System.out.println(splitIntoTwoGroupsWithStatusAndRest(tasks, Status.CLOSED, false));

  }

  private static List<Task> getListTasksByAtWorkStatus(List<Task> tasks) {
    return tasks.stream().filter(task -> task.getStatus().equals(Status.AT_WORK)).toList();
  }

  private static long getNumberOfTasksWithClosedStatus(List<Task> tasks) {
    return tasks.stream().filter(task -> task.getStatus().equals(Status.CLOSED)).count();
  }

  private static boolean isAvailableTask(List<Task> tasks, long id) {
    return tasks.stream().anyMatch(task -> task.getId() == id);
  }

  private static List<Task> getListOfTasksSortedByStatus(List<Task> tasks) {
    return tasks.stream().sorted(Comparator.comparing(Task::getStatus)).toList();
  }

  private static List<Task> groupingByStatusAndSubgroupsOfEvenOddById(List<Task> tasks, Status status, boolean isEven) {
    Map<Status, Map<Boolean, List<Task>>> result = tasks.stream()
            .collect(Collectors.groupingBy(Task::getStatus, Collectors.partitioningBy(task -> task.getId() % 2 == 0)));
    return result.get(status).get(isEven);
  }

  private static List<Task> splitIntoTwoGroupsWithStatusAndRest(List<Task> tasks, Status status, boolean isByStatus) {
    Map<Boolean, List<Task>> result = tasks.stream()
            .collect(Collectors.partitioningBy(task -> task.getStatus().equals(status)));
    return result.get(isByStatus);
  }

  private static void init() {
    tasks = new ArrayList<>();
    tasks.add(new Task(0L, "verification", Status.OPEN));
    tasks.add(new Task(1L, "preparation", Status.AT_WORK));
    tasks.add(new Task(2L, "initialisation", Status.CLOSED));
    tasks.add(new Task(3L, "processing", Status.OPEN));
    tasks.add(new Task(4L, "loading", Status.AT_WORK));
    tasks.add(new Task(5L, "conservation", Status.CLOSED));
    tasks.add(new Task(6L, "broadcast", Status.OPEN));
  }
}
