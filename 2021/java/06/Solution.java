import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Problem statement: https://adventofcode.com/2021/day/6
 */
public class Solution {
  private static long solve(long[] initialCounts, int days) {
    var counts = initialCounts.clone();

    for (var day = 0; day < days; ++day) {
      var t = counts[0];
      System.arraycopy(counts, 1, counts, 0, counts.length - 1);
      counts[6] += t;
      counts[8] = t;
    }

    return Arrays
      .stream(counts)
      .reduce(0L, (prev, curr) -> prev + curr);
  }

  private static long solvePart1(long[] counts) {
    final var days = 80;
    return solve(counts, days);
  }

  private static long solvePart2(long[] counts) {
    final var days = 256;
    return solve(counts, days);
  }

  private static long[] readInput(String filename) throws IOException {
    var nums = Files.readString(Path.of(filename)).split(",");

    var result = new long[9];
    for (var n : nums) {
      result[Integer.parseInt(n)] += 1;
    }
    return result;
  }

  public static void main(String[] args) throws Exception {
    var input = readInput("./../../input/input6.txt");

    var solution1 = solvePart1(input);
    var solution2 = solvePart2(input);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}
