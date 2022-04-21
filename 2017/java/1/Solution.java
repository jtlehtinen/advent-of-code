import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Problem statement: https://adventofcode.com/2017/day/1
 */
public class Solution {
  private static int solve(String in, int compareToStep) {
    var n = in.length();

    int sum = 0;
    for (var first = 0; first < n; ++first) {
      var second = (first + compareToStep) % n;
      if (in.charAt(first) == in.charAt(second)) {
        sum += in.charAt(first) - '0';
      }
    }
    return sum;
  }

  private static int solvePart1(String in) {
    return solve(in, 1);
  }

  private static int solvePart2(String in) {
    return solve(in, in.length() / 2);
  }

  public static void main(String[] args) throws Exception {
    var in = Files.readString(Path.of("./../../input/input1.txt")).trim();

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}