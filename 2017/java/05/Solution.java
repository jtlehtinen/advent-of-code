import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Problem statement: https://adventofcode.com/2017/day/5
 */
public class Solution {
  private static boolean outOfBounds(int[] arr, int index) {
    return index < 0 || index >= arr.length;
  }

  private static int solvePart1(int[] in) {
    in = Arrays.copyOf(in, in.length);

    int step = 0;
    int index = 0;

    while (!outOfBounds(in, index)) {
      ++step;

      var jump = in[index];
      in[index] += 1;

      index += jump;
    }

    return step;
  }

  private static int solvePart2(int[] in) {
    in = Arrays.copyOf(in, in.length);

    int step = 0;
    int index = 0;

    while (!outOfBounds(in, index)) {
      ++step;

      var jump = in[index];
      in[index] += (jump >= 3 ? -1 : 1);

      index += jump;
    }

    return step;
  }

  private static int[] readInput(String filename) throws IOException {
    var lines = Files.readAllLines(Path.of(filename));

    var nums = new ArrayList<Integer>();
    for (var line : lines) {
      nums.add(Integer.parseInt(line.trim()));
    }

    // yuck...
    return nums.stream().mapToInt(Integer::intValue).toArray();
  }

  public static void main(String[] args) throws Exception {
    var in = readInput("./../../input/input5.txt");

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}