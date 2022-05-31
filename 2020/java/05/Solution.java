import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Problem statement: https://adventofcode.com/2020/day/5
 */
public class Solution {
  private static int binaryPartition(String chars, char lowerChar, char higherChar, int min, int max) {
    for (var c : chars.toCharArray()) {
      if (c == lowerChar) max = min + (max - min) / 2;
      else if (c == higherChar) min = min + (max - min + 1) / 2;
    }
    return min;
  }

  private static int[] solve(List<String> lines) {
    var ids = lines
      .stream()
      .mapToInt(line -> {
        var row = binaryPartition(line.substring(0, 7), 'F', 'B', 0, 127);
        var col = binaryPartition(line.substring(7), 'L', 'R', 0, 7);
        return row * 8 + col;
      })
      .toArray();

    Arrays.sort(ids);
    return ids;
  }

  private static int solvePart1(List<String> lines) {
    var ids = solve(lines);
    if (ids.length == 0)
      throw new IllegalArgumentException("input has no solution");

    return ids[ids.length - 1];
  }

  private static int solvePart2(List<String> lines) {
    var ids = solve(lines);

    for (var i = 1; i < ids.length; ++i) {
      var prev = ids[i-1];
      var curr = ids[i];
      if (curr - prev > 1) return prev + 1;
    }

    throw new IllegalArgumentException("input has no solution");
  }

  private static List<String> readInput(String filename) throws IOException {
    return Files.readAllLines(Path.of(filename));
  }

  public static void main(String[] args) throws Exception {
    var in = readInput("./../../input/input5.txt");

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}
