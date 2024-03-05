package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.stream.Collectors;
import org.example.Main.Group;
import org.junit.jupiter.api.Test;

class MainTest {

//  @Test
//  public void testGorupsNotEmptyAfterMethod() {
//    assertNotNull(Main.groups);
//    assertTrue(Main.groups.isEmpty());
//    Main.main(new String[] {});
//    assertTrue(Main.groups.isEmpty());
//  }
//
//  @Test
//  public void test() {
//    assertNotNull(Main.groups);
//    assertTrue(Main.groups.isEmpty());
//    Main.main(new String[] {});
//
//    // group 1
//    Set<Group> groups1 = Main.groups.stream()
//        .filter(it -> it.getResult().contains("\"\";\"012\";\"445\";\"111\""))
//        .collect(Collectors.toSet());
//    System.out.println(groups1);
//    assertTrue(groups1.size() == 1);
//    assertTrue(groups1.stream()
//            .findFirst()
//            .get()
//            .getResult()
//            .size() == 6
//    );
//
//    // group 2
//    Set<Group> groups2 = Main.groups.stream()
//        .filter(it -> it.getResult().contains("\"704\";\"698\";\"594\""))
//        .collect(Collectors.toSet());
//    System.out.println(groups2);
//    assertTrue(groups2.size() == 1);
//    assertTrue(groups2.stream()
//            .findFirst()
//            .get()
//            .getResult()
//            .size() == 1
//    );
//
//    // group 3
//    Set<Group> groups3 = Main.groups.stream()
//        .filter(it -> it.getResult().contains("\"856\";\"\";\"292\""))
//        .collect(Collectors.toSet());
//    System.out.println(groups3);
//    assertTrue(groups3.size() == 1);
//    assertTrue(groups3.stream()
//            .findFirst()
//            .get()
//            .getResult()
//            .size() == 1
//    );
//
//    // group 3
//    Set<Group> groups4 = Main.groups.stream()
//        .filter(it -> it.getResult().contains("\"511\";\"\";\"365\""))
//        .collect(Collectors.toSet());
//    System.out.println(groups4);
//    assertTrue(groups4.size() == 1);
//    assertTrue(groups4.stream()
//            .findFirst()
//            .get()
//            .getResult()
//            .size() == 1
//    );
//
//    // group 3
//    Set<Group> groups5 = Main.groups.stream()
//        .filter(it -> it.getResult().contains("\"466\";\"\";\"599\";\"411\""))
//        .collect(Collectors.toSet());
//    System.out.println(groups5);
//    assertTrue(groups5.size() == 1);
//    assertTrue(groups5.stream()
//            .findFirst()
//            .get()
//            .getResult()
//            .size() == 1
//    );
//
//    // group 3
//    Set<Group> groups6 = Main.groups.stream()
//        .filter(it -> it.getResult().contains("\"630\";\"494\";\"130\";\"766\""))
//        .collect(Collectors.toSet());
//    System.out.println(groups6);
//    assertTrue(groups6.size() == 1);
//    assertTrue(groups6.stream()
//            .findFirst()
//            .get()
//            .getResult()
//            .size() == 2
//    );
//
//    // group 3
//    Set<Group> groups7 = Main.groups.stream()
//        .filter(it -> it.getResult().contains("\"316\";\"704\";\"076\""))
//        .collect(Collectors.toSet());
//    System.out.println(groups7);
//    assertTrue(groups7.size() == 1);
//    assertTrue(groups7.stream()
//            .findFirst()
//            .get()
//            .getResult()
//            .size() == 1
//    );
//
//    // group 3
//    Set<Group> groups8 = Main.groups.stream()
//        .filter(it -> it.getResult().contains("\"\";\"486\";\"929\""))
//        .collect(Collectors.toSet());
//    System.out.println(groups8);
//    assertTrue(groups8.size() == 1);
//    assertTrue(groups8.stream()
//            .findFirst()
//            .get()
//            .getResult()
//            .size() == 1
//    );
//
//    // group 3
//    Set<Group> groups9 = Main.groups.stream()
//        .filter(it -> it.getResult().contains("\"366\";\"\""))
//        .collect(Collectors.toSet());
//    System.out.println(groups9);
//    assertTrue(groups9.size() == 1);
//    assertTrue(groups9.stream()
//            .findFirst()
//            .get()
//            .getResult()
//            .size() == 1
//    );
//
//    // group 3
//    assertTrue(Main.groups.stream()
//        .filter(it -> it.getResult().contains("\"356\";\"012\";;\"336\""))
//        .count() == 0
//    );
//
//    // group 3
//    assertTrue(Main.groups.stream()
//        .filter(it -> it.getResult().contains("\"011\"357\";\"128\";\"599\""))
//        .count() == 0
//    );
//  }
}