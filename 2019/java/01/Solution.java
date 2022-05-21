import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Problem statement: https://adventofcode.com/2019/day/1
 */
public class Solution {
  private static int solvePart1(int[] values) {
    int result = 0;
    for (var value : values) {
      result += (value / 3) - 2;
    }
    return result;
  }

  private static int solvePart2(int[] values) {
    int result = 0;
    for (var value : values) {
      for (;;) {
        value = (value / 3) - 2;
        if (value <= 0) break;

        result += value;
      }
    }
    return result;
  }

  private static int[] readInput(String filename) throws IOException {
    return Files
      .readAllLines(Path.of(filename))
      .stream()
      .mapToInt(Integer::parseInt)
      .toArray();
  }

  public static void main(String[] args) throws Exception {
    var in = readInput("./../../input/input1.txt");

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}
