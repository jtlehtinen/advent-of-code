import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Problem statement: https://adventofcode.com/2017/day/6
 */
public class Solution {
  private static int indexOfMax(int[] in) {
    if (in.length == 0) {
      return -1;
    }

    int max = 0;
    for (int i = 1; i < in.length; ++i) {
      if (in[i] > in[max]) {
        max = i;
      }
    }

    return max;
  }

  private static void redistribute(int[] in) {
    int max = indexOfMax(in);
    if (max < 0) return;

    int blocks = in[max];
    in[max] = 0;

    for (int i = (max + 1) % in.length; blocks > 0; i = (i + 1) % in.length) {
      in[i]++;
      blocks--;
    }
  }

  private static int solvePart1(int[] in) {
    in = Arrays.copyOf(in, in.length);

    // Collisions, heh heh...
    var seen = new HashSet<Integer>();

    int cycles = 0;

    for (;;) {
      ++cycles;
      var hc = Arrays.hashCode(in);
      if (seen.contains(hc)) {
        return cycles;
      }

      seen.add(hc);
      redistribute(in);
    }
  }

  private static int solvePart2(int[] in) {
    in = Arrays.copyOf(in, in.length);

    // Collisions, heh heh...
    var seen = new HashMap<Integer, Integer>();

    int cycles = 0;

    for (;;) {
      ++cycles;
      var hc = Arrays.hashCode(in);
      if (seen.containsKey(hc)) {
        return cycles - seen.get(hc);
      }

      seen.put(hc, cycles);
      redistribute(in);
    }
  }

  private static int[] readInput(String filename) throws IOException {
    var line = Files.readString(Path.of(filename)).trim();
    var tokens = line.split("\\s");

    return Arrays.stream(tokens).mapToInt(Integer::parseInt).toArray();
  }

  public static void main(String[] args) throws Exception {
    var in = readInput("./../../input/input6.txt");

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}