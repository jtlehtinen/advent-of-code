import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Problem statement: https://adventofcode.com/2020/day/3
 */
public class Solution {

  private static long solve(List<String> lines, int stepRight, int stepDown) {
    long result = 0;

    int row = 0;
    int col = 0;
    while  (row < lines.size()) {
      var line = lines.get(row);
      if (line.charAt(col) == '#') ++result;

      row += stepDown;
      col = (col + stepRight) % line.length();
    }

    return result;
  }

  private static long solvePart1(List<String> lines) {
    return solve(lines, 3, 1);
  }

  private static long solvePart2(List<String> lines) {
    return
      solve(lines, 1, 1) *
      solve(lines, 3, 1) *
      solve(lines, 5, 1) *
      solve(lines, 7, 1) *
      solve(lines, 1, 2);
  }

  private static List<String> readInput(String filename) throws IOException {
    return Files
      .readAllLines(Path.of(filename));
  }

  public static void main(String[] args) throws Exception {
    var in = readInput("./../../input/input3.txt");

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}
