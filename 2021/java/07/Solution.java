import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntUnaryOperator;

/**
 * Problem statement: https://adventofcode.com/2021/day/7
 */
public class Solution {

  private static int max(int[] arr) {
    var result = Integer.MIN_VALUE;
    for (var x : arr) {
      if (x > result) result = x;
    }
    return result;
  }

  private static int min(int[] arr) {
    var result = Integer.MAX_VALUE;
    for (var x : arr) {
      if (x < result) result = x;
    }
    return result;
  }

  private static int partialSum(int from, int to) {
    return (to - from + 1) * (to + from) / 2;
  }

  private static int solve(int[] positions, IntUnaryOperator calcRequiredFuel) {
    var minPosition = min(positions);
    var maxPosition = max(positions);

    var result = Integer.MAX_VALUE;
    for (var p = minPosition; p <= maxPosition; ++p) {
      result = Math.min(result, calcRequiredFuel.applyAsInt(p));
    }
    return result;
  }

  private static int solvePart1(int[] positions) {
    return solve(positions, position ->
      Arrays
        .stream(positions)
        .reduce(0, (prev, curr) -> prev + Math.abs(curr - position)));
  }

  private static int solvePart2(int[] positions) {
    return solve(positions, position ->
      Arrays
      .stream(positions)
      .reduce(0, (prev, curr) -> prev + partialSum(0, Math.abs(curr - position))));
  }

  private static int[] readInput(String filename) throws IOException {
    var in = Files.readString(Path.of(filename));
    return Arrays
      .stream(in.split(","))
      .mapToInt(Integer::parseInt)
      .toArray();
  }

  public static void main(String[] args) throws Exception {
    var input = readInput("./../../input/input7.txt");

    var solution1 = solvePart1(input);
    var solution2 = solvePart2(input);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}
