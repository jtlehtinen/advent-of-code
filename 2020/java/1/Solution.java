import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Problem statement: https://adventofcode.com/2020/day/1
 */
public class Solution {
  private static int solvePart1(int[] values) {
    final int BIG_ENOUGH = 10000;
    var seen = new boolean[BIG_ENOUGH];

    for (var value : values) {
      var want = 2020 - value;
      if (seen[want]) return value * want;
      seen[value] = true;
    }

    throw new IllegalArgumentException("given input has no solution");
  }

  private static int solvePart2(int[] values) {
    int N = values.length;

    for (int i = 0; i < N; ++i) {
      for (int j = i + 1; j < N; ++j) {
        for (int k = j + 1; k < N; ++k) {
          if (values[i] + values[j] + values[k] == 2020) {
            return values[i] * values[j] * values[k];
          }
        }
      }
    }

    throw new IllegalArgumentException("given input has no solution");
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
