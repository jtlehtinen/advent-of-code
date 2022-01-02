import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

/**
 * Problem statement: https://adventofcode.com/2016/day/1
 */
public class Solution {
  public static record Location(int x, int y) { }

  private static int solvePart1(String in) {
    int x = 0;
    int y = 0;
    int dir = 0; // NORTH, EAST, SOUTH, WEST = 0, 1, 2, 3
    for (var command : in.split(",")) {
      command = command.trim();
      var rot = command.charAt(0) == 'R' ? 1 : -1;
      var count = Integer.parseInt(command.substring(1));
      dir = (dir + rot + 4) % 4;
      switch (dir) {
        case 0: y += count; break;
        case 1: x += count; break;
        case 2: y -= count; break;
        case 3: x -= count; break;
      }
    }
    return Math.abs(x) + Math.abs(y);
  }

  private static int solvePart2(String in) {
    int x = 0;
    int y = 0;
    int dir = 0; // NORTH, EAST, SOUTH, WEST = 0, 1, 2, 3
    var stepx = new int[]{1, 0, -1, 0};
    var stepy = new int[]{0, 1, 0, -1};

    var visited = new HashSet<Location>();
    visited.add(new Location(x, y));

    for (var command : in.split(",")) {
      command = command.trim();
      var rot = command.charAt(0) == 'R' ? 1 : -1;
      var count = Integer.parseInt(command.substring(1));
      dir = (dir + rot + 4) % 4;

      for (int i = 0; i < count; ++i) {
        x += stepx[dir];
        y += stepy[dir];
        var location = new Location(x, y);
        if (visited.contains(location)) return Math.abs(x) + Math.abs(y);
        visited.add(location);
      }
    }

    throw new IllegalArgumentException("invalid input");
  }

  public static void main(String[] args) throws Exception {
    var in = Files.readString(Path.of("./../../input/input1.txt"));

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}