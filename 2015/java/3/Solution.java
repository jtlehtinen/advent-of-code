import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.HashMap;

/**
 * Problem statement: https://adventofcode.com/2015/day/3
 */
public class Solution {
  private static record Point(int x, int y) { }

  private static int solvePart1(String in) {
    int x = 0, y = 0;
    var visited = new HashSet<Point>();
    visited.add(new Point(x, y));

    for (char c : in.toCharArray()) {
      switch (c) {
        case '^': y += 1; break;
        case 'v': y -= 1; break;
        case '<': x -= 1; break;
        case '>': x += 1; break;
      }
      visited.add(new Point(x, y));
    }

    return visited.size();
  }

  private static int solvePart2(String in) {
    var x = new int[2];
    var y = new int[2];
    var visited = new HashSet<Point>();
    visited.add(new Point(x[0], y[0]));

    for (int i = 0; i < in.length(); ++i) {
      var bit = i & 1;
      switch (in.charAt(i)) {
        case '^': y[bit] += 1; break;
        case 'v': y[bit] -= 1; break;
        case '<': x[bit] -= 1; break;
        case '>': x[bit] += 1; break;
      }
      visited.add(new Point(x[bit], y[bit]));
    }

    return visited.size();
  }

  public static void main(String[] args) throws Exception {
    var in = Files.readString(Path.of("../../input/input3.txt"));

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}