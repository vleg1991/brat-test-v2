package org.example;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

  @org.junit.jupiter.api.Test
  public void test() {

    List<String> correctInput = List.of(
        "\"856\";\"\";\"292\"",
        "\"\""
    );
    List<String> incorrectInput = List.of(
        "\"356\";\"012\";;\"336\"",
        "\"011\"357\";\"128\";\"599\"",
        "\"",
        "\"\"\";",
        "\"\";"
    );
    for (String input : correctInput) {
      System.out.println(input);
      System.out.println(
        Arrays.asList(input.split(";"))
            .stream()
            .anyMatch(it -> !it.matches("\"{1}\\d*\"{1};?"))
      );
      System.out.println("-------------");
    }
    for (String input : incorrectInput) {
      System.out.println(input);
      System.out.println(
        Arrays.asList(input.split(";"))
            .stream()
            .anyMatch(it -> !it.matches("\"{1}\\d*\"{1};?"))
      );
      System.out.println("-------------");
    }
  }

  @org.junit.jupiter.api.Test
  public void test1() {

    List<String> correctInput = List.of(
        "\"856\";\"\";\"292\"",
        "\"\""
    );
    List<String> incorrectInput = List.of(
        "\"356\";\"012\";;\"336\"",
        "\"011\"357\";\"128\";\"599\"",
        "\"",
        "\"\"\";",
        "\"\";"
    );
    for (String input : correctInput) {
      System.out.println(input);
      Arrays.asList(input.split(";"))
          .forEach(System.out::println);
      System.out.println("-------------");
    }
    for (String input : incorrectInput) {
      System.out.println(input);
      Arrays.asList(input.split(";"))
          .forEach(System.out::println);
      System.out.println("-------------");
    }
  }
}
