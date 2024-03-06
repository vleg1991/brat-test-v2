package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {

  public static final Map<Pair, Group> groups = new HashMap<>();

  // Класс представляющий группу уникальных строк
  public static class Group {

    private Set<Pair> pairs = new HashSet<>();

    private Set<String> lines = new HashSet<>();

    public Group(String line, Set<Pair> pair) {
      this.lines.add(line);
      this.pairs.addAll(pair);
    }

    public Group(Set<String> lines) {
      this.lines = lines;
    }

    public Set<String> getLines() {
      return lines;
    }

    public void setLines(Set<String> lines) {
      this.lines = lines;
    }

    public Set<Pair> getPairs() {
      return pairs;
    }

    public void setPairs(Set<Pair> pairs) {
      this.pairs = pairs;
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
      return Objects.equals(pairs, group.pairs) && Objects.equals(lines,
          group.lines);
    }

    @Override
    public int hashCode() {
      return Objects.hash(pairs, lines);
    }
  }


  // Класс представляющий пару (индекс столбца, значение)
  public static class Pair {

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

    Duration avg = Duration.ZERO;
    Duration all = Duration.ZERO;
    int counter = 0;
    int rowCounter = 0;

    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
        new FileInputStream("lng.txt")))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
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

    outData(sortGroups(new HashSet<>(groups.values())));

    System.out.println();

  }


  // Метод для поиска и объединения групп
  private static void findAndMergeGroup(String line, List<String> splittedLine, Map<Pair, Group> groups) {

    Set<Pair> pairsFromLine = new HashSet<>();

    for (int i = 0; i < splittedLine.size(); i++) {
      Pair pair = new Pair(i, splittedLine.get(i));
      if (pair.value.equals("\"\"")) {
        continue;
      }
      pairsFromLine.add(pair);
    }

    Group resultGroup = new Group(line, pairsFromLine);
    Set<Group> mergingGroups = new HashSet<>();
    for (Pair pair : pairsFromLine) {
      if (groups.containsKey(pair)) {
        mergingGroups.add(groups.get(pair));
      }
      groups.put(pair, resultGroup);
    }
    mergingGroups.forEach(it -> {
      resultGroup.lines.addAll(it.lines);
      resultGroup.pairs.addAll(it.pairs);
      resultGroup.pairs.forEach(pair -> {
        groups.put(pair, resultGroup);
      });
    });
  }

  public static Set<Group> sortGroups(Set<Group> groups) {
    return groups.stream()
        .sorted(Comparator.comparingInt(group -> group.lines.size()))
        .collect(Collectors.toCollection(LinkedHashSet::new));
  }

  private static void outData(Set<Group> sortedGroups) {
    int groupNumber = 0; // Номер группы
    for (Group group : sortedGroups) {
      if (group.lines.size() > 1) {
        groupNumber++; // увеличиваем счетчик групп с более чем одной строкой
      }
    }

    try(Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("out.txt"))))) {
      writer.append("Количество групп с более чем одной строкой: " + groupNumber + "\n");
      groupNumber = 1;

      ArrayList<Group> list = new ArrayList<>(sortedGroups);

      ListIterator<Group> iterator = list.listIterator(list.size());
    // Iterate in reverse order
      while (iterator.hasPrevious()) {
        Group group = iterator.previous();
          writer.append("Группа ").append(String.valueOf(groupNumber++)).append( "\n");
          for (String line : group.lines) {
            writer.append(line).append( "\n");
          }
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
