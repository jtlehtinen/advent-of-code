import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Problem statement: https://adventofcode.com/2018/day/2
 */
public class Solution {
  private static int solvePart1(List<String> lines) {
    int twos = 0;
    int threes = 0;

    var counts = new int[26];
    for (var line : lines) {
      for (var c : line.toCharArray()) {
        counts[c - 'a']++;
      }

      int two = 0;
      int three = 0;

      for (var count : counts) {
        if (count == 2) two |= 1;
        if (count == 3) three |= 1;
      }

      twos += two;
      threes += three;
      Arrays.fill(counts, 0);
    }

    return twos * threes;
  }

  private static int hammingDistance(String a, String b) {
    if (a.length() != b.length())
      throw new IllegalArgumentException("a and b must have equal length");

    var result = 0;
    for (var i = 0; i < a.length(); ++i) {
      if (a.charAt(i) != b.charAt(i)) ++result;
    }
    return result;
  }

  private static String getCommon(String a, String b) {
    var result = new StringBuilder();
    for (var i = 0; i < a.length(); ++i) {
      if (a.charAt(i) == b.charAt(i)) result.append(a.charAt(i));
    }
    return result.toString();
  }

  private static String solvePart2(List<String> lines) {
    int N = lines.size();

    for (var i = 0; i < N; ++i) {
      for (var j = i + 1; j < N; ++j) {
        if (hammingDistance(lines.get(i), lines.get(j)) == 1) {
          return getCommon(lines.get(i), lines.get(j));
        }
      }
    }

    throw new IllegalArgumentException("given input has no solution");
  }

  private static List<String> readInput(String filename) throws IOException {
    return Files.readAllLines(Path.of(filename));
  }

  public static void main(String[] args) throws Exception {
    var in = readInput("./../../input/input2.txt");

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}
