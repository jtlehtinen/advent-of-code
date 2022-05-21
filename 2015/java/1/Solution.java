import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Problem statement: https://adventofcode.com/2015/day/1
 */
public class Solution {
  private static int solvePart1(String in) {
    return in.chars().reduce(0, (prev, curr) -> {
      if (curr == '(') return prev + 1;
      if (curr == ')') return prev - 1;
      return prev;
    });
  }

  private static int solvePart2(String in) {
    int floor = 0;
    for (int i = 0; i < in.length(); ++i) {
      switch (in.charAt(i)) {
        case '(': floor++; break;
        case ')': floor--; break;
      }
      if (floor == -1) return i + 1;
    }
    throw new IllegalArgumentException("invalid input");
  }

  public static void main(String[] args) throws Exception {
    var in = Files.readString(Path.of("./../../input/input1.txt")).trim();

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}