import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Problem statement: https://adventofcode.com/2017/day/3
 */
public class Solution {
  private static record Coords(int x, int y) { }

  private static class Walker {
    private int x = 0;
    private int y = 0;

    private int facing = 0; // right=0, up=1, left=2, down=3
    private int stepsLeft = 1;
    private int stepCount = 1;
    private int stepCountTimes = 2;

    public Walker(int cx, int cy) {
      this.x = cx;
      this.y = cy;
    }

    private void step() {
      // Steps into given direction seems to have the
      // following pattern:
      // 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, ...
      // That is, each step count is performed twice.

      x += dxForFacing(facing);
      y += dyForFacing(facing);
      --stepsLeft;

      if (stepsLeft == 0) {
        --stepCountTimes;
        if (stepCountTimes == 0) {
          stepCountTimes = 2;
          ++stepCount;
        }

        stepsLeft = stepCount;
        facing = rotate(facing);
      }
    }

    public Coords stepOnce() {
      step();
      return new Coords(x, y);
    }

    public Coords stepMany(int count) {
      for (int i = 0; i < count; ++i) {
        step();
      }
      return new Coords(x, y);
    }
  }

  /**
   * sequence produces the sequence 1, 0, -1, 0, 1, ...
   * for n = {0, 1, 2, 3, ...}.
   */
  private static int sequence(int n) {
    return Math.abs((n % 4) - 2) - 1;
  }

  private static int dxForFacing(int facing) {
    return sequence(facing);
  }

  private static int dyForFacing(int facing) {
    return sequence(facing + 1);
  }

  private static int rotate(int facing) {
    return (facing + 1) % 4;
  }

  private static int solvePart1(int in) {
    var walker = new Walker(0, 0);
    var coords = walker.stepMany(in - 1);
    return Math.abs(coords.x()) + Math.abs(coords.y());
  }

  private static int solvePart2(int in) {
    int dim = 1024;
    var grid = new int[dim][dim];
    for (int i = 0; i < dim; ++i) {
      grid[i] = new int[1024];
    }

    int cx = dim/2;
    int cy = dim/2;
    grid[cy][cx] = 1;

    var walker = new Walker(cx, cy);

    for (;;) {
      var coords = walker.stepOnce();
      if (coords.x < 0 || coords.x >= dim || coords.y < 0 || coords.y >= dim) {
        throw new IllegalArgumentException("input is too big");
      }

      var sum = 0;
      sum += grid[coords.y-1][coords.x-1];
      sum += grid[coords.y-1][coords.x];
      sum += grid[coords.y-1][coords.x+1];
      sum += grid[coords.y][coords.x-1];
      sum += grid[coords.y][coords.x+1];
      sum += grid[coords.y+1][coords.x-1];
      sum += grid[coords.y+1][coords.x];
      sum += grid[coords.y+1][coords.x+1];
      grid[coords.y][coords.x] = sum;

      if (sum > in) {
        return sum;
      }
    }
  }

  private static int parse(String filename) throws IOException {
    var in = Files.readString(Path.of(filename)).trim();
    return Integer.parseInt(in);
  }

  public static void main(String[] args) throws Exception {
    var in = parse("./../../input/input3.txt");

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}
