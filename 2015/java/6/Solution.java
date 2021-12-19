import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.IntUnaryOperator;

/**
 * Problem statement: https://adventofcode.com/2015/day/6
 */
public class Solution {
  private static record Point(int x, int y) { }

  private static int sum(int[] array) {
    int result = 0;
    for (int i : array) result += i;
    return result;
  }

  private static void applyOperator(int[] array, Point from, Point to, IntUnaryOperator op) {
    for (int y = from.y; y <= to.y; ++y) {
      for (int x = from.x; x <= to.x; ++x) {
        array[y * 1000 + x] = op.applyAsInt(array[y * 1000 + x]);
      }
    }
  }

  private static Point parsePoint(String s) {
    var parts = s.trim().split(",");
    return new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
  }

  private static boolean accept(String command, String line, int[] array, IntUnaryOperator op) {
    if (!line.startsWith(command)) return false;

    var parts = line.substring(command.length()).split("through");
    var from = parsePoint(parts[0]);
    var to = parsePoint(parts[1]);
    applyOperator(array, from, to, op);
    return true;
  }

  private static int solvePart1(List<String> in) {
    var lights = new int[1000 * 1000];
    for (String line : in) {
      if (accept("turn on", line, lights, n -> 1)) continue;
      if (accept("toggle", line, lights, n -> n ^ 1)) continue;
      accept("turn off", line, lights, n -> 0);
    }
    return sum(lights);
  }

  private static int nonnegative(int value) {
    return value < 0 ? 0 : value;
  }

  private static int solvePart2(List<String> in) {
    var lights = new int[1000 * 1000];
    for (String line : in) {
      if (accept("turn on", line, lights, n -> n + 1)) continue;
      if (accept("toggle", line, lights, n -> n + 2)) continue;
      accept("turn off", line, lights, n -> nonnegative(n - 1));
    }
    return sum(lights);
  }

  public static void main(String[] args) throws Exception {
    var in = Files.readAllLines(Path.of("../../input/input6.txt"));

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}