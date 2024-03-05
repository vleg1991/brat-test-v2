package org.example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {

  // Класс представляющий группу уникальных строк
  public static class Group {

    private final Set<String> result;
    private final Set<Pair> groupValues;

    public Group(Pair pair, String line) {
      result = new HashSet<>(Set.of(line));
      groupValues = new HashSet<>(Set.of(pair));
    }

    public Group(Set<Pair> pairsFromLine, String line) {
      result = new HashSet<>(Set.of(line));
      groupValues = pairsFromLine;
    }

    public Set<String> getResult() {
      return result;
    }

    public Set<Pair> getGroupValues() {
      return groupValues;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Group group = (Group) o;
      return Objects.equals(result, group.result) && Objects.equals(groupValues,
          group.groupValues);
    }

    @Override
    public int hashCode() {
      return Objects.hash(result, groupValues);
    }
  }


  // Класс представляющий пару (индекс столбца, значение)
  private static class Pair {

    private Integer column; // Индекс столбца
    private String value; // Значение

    public Pair(Integer column, String value) {
      this.column = column;
      this.value = value;
    }

    public Integer getColumn() {
      return column;
    }

    public void setColumn(Integer column) {
      this.column = column;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Pair pair = (Pair) o;
      return Objects.equals(column, pair.column) && Objects.equals(value,
          pair.value);
    }

    @Override
    public int hashCode() {
      return Objects.hash(column, value);
    }
  }


  public static void main(String[] args) {

    Set<Group> groups = new HashSet<>();

    Duration avg = Duration.ZERO;
    Duration all = Duration.ZERO;
    int counter = 0;
    int rowCounter = 0;

    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
        new FileInputStream("lng.txt")))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        System.out.println("Row = " + rowCounter);
        LocalDateTime startTime = LocalDateTime.now();
        List<String> splittedLine = Arrays.asList(line.split(";"));
        boolean isLineInCorrect = splittedLine.parallelStream()
            .anyMatch(it -> !it.matches("\"{1}\\d*\"{1};?"));
        if (isLineInCorrect) {
          continue;
        }
        findAndMergeGroup(line, splittedLine, groups);

        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);
        all = all.plus(duration);
        System.out.println(all);
        if (counter < 10) {
          avg = avg.plus(duration);
          counter++;
        } else {
          System.out.println(avg.dividedBy(10));
          counter = 0;
          avg = Duration.ZERO;
        }
        rowCounter++;
      }
    } catch (IOException e) {
      System.out.println("OIIINBKA");
    }

    outData(sortGroups(groups));

    System.out.println();

  }


  // Метод для поиска и объединения групп
  private static void findAndMergeGroup(String line, List<String> splittedLine, Set<Group> groups) {

    Set<Pair> pairsFromLine = new HashSet<>();

    for (int i = 0; i < splittedLine.size(); i++) {
      Pair pair = new Pair(i, splittedLine.get(i));
      if (pair.value.equals("\"\"")) {
        continue;
      }
      pairsFromLine.add(pair);
    }

    Group groupWithLine = new Group(pairsFromLine, line);
    Set<Group> groupsForLine = new HashSet<>();
//
//    Map<Pair, Group> map = new HashMap<>();
//    for (Pair pair : pairsFromLine) {
//      // List<Group> result
//      // HashMap<Pair, Set<Group>> map
//      // map.put(pair, groupWithLine) //  O(1)
//    }

    for (Pair pair : pairsFromLine) {
      if (groups.isEmpty()) {
        groups.add(groupWithLine);
      } else {
        groups.parallelStream()
            .filter(
                group -> group.groupValues.contains(pair)) // группу, которая содержит текущую пару O(1)
            .filter(group -> !group.equals(groupWithLine))
            .findFirst()
            .ifPresent(groupsForLine::add);
      }
    }

    if (!groupsForLine.isEmpty()) {
      groups.add(mergeGroups(groupsForLine, groupWithLine, groups)); // Объединяем найденные группы
    } else {
      groups.add(groupWithLine);
    }
  }

  private static Group mergeGroups(Set<Group> groupsForLine, Group groupWithLine, Set<Group> groups) {

    for (Group group : groupsForLine) { // Проходим по всем группам

      groupWithLine.result.addAll(
          group.result); // Добавляем строки из текущей группы в объединенную группу
      groupWithLine.groupValues.addAll(
          group.groupValues); // Добавляем пары из текущей группы в объединенную группу

      groups.remove(group);
    }

    return groupWithLine; // Возвращаем объединенную группу
  }

  public static Set<Group> sortGroups(Set<Group> groups) {
    return groups.stream()
        .sorted(Comparator.comparingInt(group -> group.result.size()))
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  private static void outData(Set<Group> sortedGroups) {
    int groupNumber = 0; // Номер группы
    for (Group group : sortedGroups) {
      if (group.result.size() > 1) {
        groupNumber++; // увеличиваем счетчик групп с более чем одной строкой
      }
    }
    System.out.println("Количество групп с более чем одной строкой: " + groupNumber);

    groupNumber = 1;

    ArrayList<Group> list = new ArrayList<>(sortedGroups);

    ListIterator<Group> iterator = list.listIterator(list.size());

    // Iterate in reverse order
    while (iterator.hasPrevious()) {
      Group group = iterator.previous();

      System.out.println("Группа " + groupNumber++); // Выводим надпись с номером группы
      group.result.forEach(System.out::println);     // Выводим строки из группы
    }
  }
}
