import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Problem statement: https://adventofcode.com/2016/day/2
 */
public class Solution {
  private static String solvePart1(List<String> in) {
    var board = new char[][]{
      {'0', '0', '0', '0', '0'},
      {'0', '1', '2', '3', '0'},
      {'0', '4', '5', '6', '0'},
      {'0', '7', '8', '9', '0'},
      {'0', '0', '0', '0', '0'},
    };

    int x = 2;
    int y = 2;

    var sb = new StringBuilder();
    for (var line : in) {
      for (var c : line.trim().toCharArray()) {
        int newX = x;
        int newY = y;
        switch (c) {
          case 'U': newY -= 1; break;
          case 'L': newX -= 1; break;
          case 'D': newY += 1; break;
          case 'R': newX += 1; break;
        }
        if (board[newY][newX] != '0') {
          x = newX;
          y = newY;
        }
      }
      sb.append(board[y][x]);
    }

    return sb.toString();
  }

  private static String solvePart2(List<String> in) {
    var board = new char[][]{
      {'0', '0', '0', '0', '0', '0', '0'},
      {'0', '0', '0', '1', '0', '0', '0'},
      {'0', '0', '2', '3', '4', '0', '0'},
      {'0', '5', '6', '7', '8', '9', '0'},
      {'0', '0', 'A', 'B', 'C', '0', '0'},
      {'0', '0', '0', 'D', '0', '0', '0'},
      {'0', '0', '0', '0', '0', '0', '0'},
    };

    int x = 1;
    int y = 3;

    var sb = new StringBuilder();
    for (var line : in) {
      for (var c : line.trim().toCharArray()) {
        int newX = x;
        int newY = y;
        switch (c) {
          case 'U': newY -= 1; break;
          case 'L': newX -= 1; break;
          case 'D': newY += 1; break;
          case 'R': newX += 1; break;
        }
        if (board[newY][newX] != '0') {
          x = newX;
          y = newY;
        }
      }
      sb.append(board[y][x]);
    }

    return sb.toString();
  }

  public static void main(String[] args) throws Exception {
    var in = Files.readAllLines(Path.of("./../../input/input2.txt"));

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}