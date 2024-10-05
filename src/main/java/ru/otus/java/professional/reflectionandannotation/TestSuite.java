package ru.otus.java.professional.reflectionandannotation;

public class TestSuite {
  @BeforeSuite
  public static void init() {
    System.out.print("Execution init:");
  }

  @Disabled
  @Test(priority = 1)
  public static void test1() {
    System.out.print("Execution test 1:");
  }

  @Test(priority = 6)
  public static void test2() {
    System.out.print("Execution test 2:");
  }

  @Test(priority = 4)
  public static void test3() {
    System.out.print("Execution test 3:");
  }

  @Test
  public static void test4() {
    System.out.print("Execution test 4:");
    throw new RuntimeException();
  }

  @AfterSuite
  public static void deinit() {
    System.out.print("Execution deinit:");
  }
}
