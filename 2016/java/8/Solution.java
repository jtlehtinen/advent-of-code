import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Problem statement: https://adventofcode.com/2016/day/8
 */
public class Solution {
  private static final int WIDTH = 50;
  private static final int HEIGHT = 6;

  private static final int[] TEMP_ROW = new int[WIDTH];
  private static final int[] TEMP_COL = new int[HEIGHT];

  private static int countNonzero(int[][] screen) {
    int result = 0;
    for (int y = 0; y < HEIGHT; ++y) {
      for (int x = 0; x < WIDTH; ++x) {
        result += screen[y][x];
      }
    }
    return result;
  }

  private static void set(int[][] screen, int width, int height) {
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < width; ++x) {
        screen[y][x] = 1;
      }
    }
  }

  private static void rotateRow(int[][] screen, int row, int amount) {
    for (int x = 0; x < WIDTH; ++x) {
      TEMP_ROW[(x + amount) % WIDTH] = screen[row][x];
    }

    for (int x = 0; x < WIDTH; ++x) {
      screen[row][x] = TEMP_ROW[x];
    }
  }

  private static void rotateColumn(int[][] screen, int col, int amount) {
    for (int y = 0; y < HEIGHT; ++y) {
      TEMP_COL[(y + amount) % HEIGHT] = screen[y][col];
    }

    for (int y = 0; y < HEIGHT; ++y) {
      screen[y][col] = TEMP_COL[y];
    }
  }

  private static void applyInstructions(int[][] screen, List<String> lines) {
    for (var line : lines) {
      if (line.startsWith("rect")) {
        var tokens = line.substring("rect ".length()).split("x");
        var width = Integer.parseInt(tokens[0]);
        var height = Integer.parseInt(tokens[1]);
        set(screen, width, height);
      } else if (line.startsWith("rotate row")) {
        var tokens = line.substring("rotate row y=".length()).split(" by ");
        var row = Integer.parseInt(tokens[0]);
        var amount = Integer.parseInt(tokens[1]);
        rotateRow(screen, row, amount);
      } else if (line.startsWith("rotate column")) {
        var tokens = line.substring("rotate column x=".length()).split(" by ");
        var column = Integer.parseInt(tokens[0]);
        var amount = Integer.parseInt(tokens[1]);
        rotateColumn(screen, column, amount);
      }
    }
  }

  private static int solvePart1(List<String> lines) {
    var screen = new int[HEIGHT][WIDTH];
    applyInstructions(screen, lines);
    return countNonzero(screen);
  }

  private static int[][] solvePart2(List<String> lines) {
    var screen = new int[HEIGHT][WIDTH];
    applyInstructions(screen, lines);
    return screen;
  }

  public static void main(String[] args) throws Exception {
    var lines = Files.readAllLines(Path.of("./../../input/input8.txt"));

    var solution1 = solvePart1(lines);
    var solution2 = solvePart2(lines);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: ");

    for (int y = 0; y < HEIGHT; ++y) {
      for (int x = 0; x < WIDTH; ++x) {
        System.out.print(solution2[y][x] == 0 ? ' ' : '#');
      }
      System.out.println();
    }
  }
}