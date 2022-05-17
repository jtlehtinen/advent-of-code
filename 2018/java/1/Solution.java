import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

/**
 * Problem statement: https://adventofcode.com/2018/day/1
 */
public class Solution {
  private static int solvePart1(int[] values) {
    int result = 0;
    for (var value : values) result += value;
    return result;
  }

  private static int solvePart2(int[] values) {
    var seen = new HashSet<Integer>();

    int current = 0;
    seen.add(current);

    for (;;) {
      for (var value : values) {
        current += value;
        if (seen.contains(current)) return current;

        seen.add(current);
      }
    }
  }

  private static int[] readInput(String filename) throws IOException {
    var lines = Files.readAllLines(Path.of(filename));
    return lines.stream().mapToInt(Integer::parseInt).toArray();
  }

  public static void main(String[] args) throws Exception {
    var in = readInput("./../../input/input1.txt");

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}
