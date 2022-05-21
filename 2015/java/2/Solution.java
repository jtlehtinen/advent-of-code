import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Problem statement: https://adventofcode.com/2015/day/2
 */
public class Solution {
  private static record Vec3(int x, int y, int z) { }

  private static int min3(int a, int b, int c) {
    return Math.min(a, Math.min(b, c));
  }

  private static int solvePart1(List<Vec3> dimensions) {
    int result = 0;
    for (var v : dimensions) {
      result += 2 * (v.x * v.y + v.y * v.z + v.z * v.x) + min3(v.x * v.y, v.y * v.z, v.z * v.x);
    }
    return result;
  }

  private static int solvePart2(List<Vec3> dimensions) {
    int result = 0;
    for (var v : dimensions) {
      result += (v.x * v.y * v.z) + 2 * min3(v.x + v.y, v.y + v.z, v.z + v.x);
    }
    return result;
  }

  private static List<Vec3> readInput(String filename) throws IOException {
    var lines = Files.readAllLines(Path.of(filename));

    return lines.stream().map(line -> {
      var tokens = line.trim().split("x");
      var x = Integer.parseInt(tokens[0]);
      var y = Integer.parseInt(tokens[1]);
      var z = Integer.parseInt(tokens[2]);
      return new Vec3(x, y, z);
    }).toList();
  }

  public static void main(String[] args) throws Exception {
    var lines = readInput("../../input/input2.txt");

    var solution1 = solvePart1(lines);
    var solution2 = solvePart2(lines);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}