import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Problem statement: https://adventofcode.com/2021/day/1
 */
public class Solution {
  private static int solve(int[] depths, int window) {
    int count = 0;
    for (int i = window; i < depths.length; ++i) {
      if (depths[i] > depths[i - window]) ++count;
    }
    return count;
  }

  private static int solvePart1(int[] depths) {
    return solve(depths, 1);
  }

  private static int solvePart2(int[] depths) {
    return solve(depths, 3);
  }

  private static int[] readInput(String filename) throws IOException {
    var lines = Files.readAllLines(Path.of(filename));
    return lines.stream().mapToInt(Integer::parseInt).toArray();
  }

  public static void main(String[] args) throws Exception {
    var input = readInput("./../../input/input1.txt");

    var solution1 = solvePart1(input);
    var solution2 = solvePart2(input);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}
