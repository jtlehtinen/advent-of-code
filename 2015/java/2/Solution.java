import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Problem statement: https://adventofcode.com/2015/day/2
 */
public class Solution {
  private static int min3(int a, int b, int c) {
    return Math.min(a, Math.min(b, c));
  }

  private static int solvePart1(List<String> lines) {
    int result = 0;
    for (var line : lines) {
      var dimensions = line.trim().split("x");
      var x = Integer.parseInt(dimensions[0]);
      var y = Integer.parseInt(dimensions[1]);
      var z = Integer.parseInt(dimensions[2]);
      result += 2 * (x * y + y * z + z * x) + min3(x * y, y * z, z * x);
    }
    return result;
  }

  private static int solvePart2(List<String> lines) {
    int result = 0;
    for (var line : lines) {
      var dimensions = line.trim().split("x");
      var x = Integer.parseInt(dimensions[0]);
      var y = Integer.parseInt(dimensions[1]);
      var z = Integer.parseInt(dimensions[2]);
      result += (x * y * z) + 2 * min3(x + y, y + z, z + x);
    }
    return result;
  }

  public static void main(String[] args) throws Exception {
    var lines = Files.readAllLines(Path.of("../../input/input2.txt"));

    var solution1 = solvePart1(lines);
    var solution2 = solvePart2(lines);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}