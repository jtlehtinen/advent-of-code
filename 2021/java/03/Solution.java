import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Problem statement: https://adventofcode.com/2021/day/3
 */
public class Solution {

  private static long gamma(List<String> numbers, int N) {
    var ones = new int[N];
    numbers.forEach(n -> {
      var i = N - 1;
      for (var c : n.toCharArray()) {
        ones[i--] += c - '0';
      }
    });

    var result = 0L;
    for (var i = 0; i < N; ++i) {
      if (2 * ones[i] > numbers.size())
        result = result | (1L << i);
    }
    return result;
  }

  private static int countOnes(List<String> numbers, int characterPosition) {
    return numbers
      .stream()
      .mapToInt(n -> n.charAt(characterPosition) - '0')
      .reduce(0, Integer::sum);
  }

  private static char mostCommonBit(List<String> numbers, int characterPosition) {
    var ones = countOnes(numbers, characterPosition);
    return 2 * ones >= numbers.size() ? '1' : '0'; // even => '1'
  }

  private static char leastCommonBit(List<String> numbers, int characterPosition) {
    var ones = countOnes(numbers, characterPosition);
    return 2 * ones >= numbers.size() ? '0' : '1'; // even => '0'
  }

  private static long oxygen(List<String> numbers, int N) {
    for (var i = 0; i < N && numbers.size() > 1; ++i) {
      final var fi = i;
      var bit = mostCommonBit(numbers, i);
      numbers = numbers.stream().filter(n -> n.charAt(fi) == bit).toList();
    }
    return Long.parseLong(numbers.get(0), 2);
  }

  private static long scrubber(List<String> numbers, int N) {
    for (var i = 0; i < N && numbers.size() > 1; ++i) {
      final var fi = i;
      var bit = leastCommonBit(numbers, i);
      numbers = numbers.stream().filter(n -> n.charAt(fi) == bit).toList();
    }
    return Long.parseLong(numbers.get(0), 2);
  }

  private static long solvePart1(List<String> numbers, int N) {
    var g = gamma(numbers, N);
    var e = g ^ ((1L << N) - 1L);
    return g * e;
  }

  private static long solvePart2(List<String> numbers, int N) {
    var o = oxygen(numbers, N);
    var s = scrubber(numbers, N);
    return o * s;
  }

  private static List<String> readInput(String filename) throws IOException {
    return Files.readAllLines(Path.of(filename));
  }

  public static void main(String[] args) throws Exception {
    var input = readInput("./../../input/input3.txt");
    var N = input.get(0).length();

    var solution1 = solvePart1(input, N);
    var solution2 = solvePart2(input, N);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}
