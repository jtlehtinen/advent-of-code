import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Problem statement: https://adventofcode.com/2021/day/4
 */
public class Solution {
  private static final int DIM = 5;
  private static final int MARK = -1;

  private static record Board(int[] cells) {
    public void mark(int number) {
      for (int i = 0; i < DIM*DIM; ++i) {
        if (cells[i] == number) cells[i] = MARK;
      }
    }

    private int sum(int first, int step) {
      return cells[first] + cells[first + 1*step] + cells[first + 2*step] + cells[first + 3*step] + cells[first + 4*step];
    }

    public boolean hasBingo() {
      for (int row = 0; row < DIM; ++row) {
        if (sum(row*DIM, 1) == DIM*MARK) return true;
      }
      for (int col = 0; col < DIM; ++col) {
        if (sum(col, DIM) == DIM*MARK) return true;
      }
      return false;
    }

    public int sumOfUnmarked() {
      return Arrays
        .stream(cells)
        .reduce(0, (prev, curr) -> prev + (curr == MARK ? 0 : curr));
    }
  }

  private static int solvePart1(List<Board> boards, int[] numbers) {
    for (var n : numbers) {
      boards.forEach(b -> b.mark(n));

      var bingo = boards.stream().filter(b -> b.hasBingo()).findFirst();
      if (bingo.isPresent()) return bingo.get().sumOfUnmarked() * n;
    }
    throw new IllegalArgumentException("input has no solution");
  }

  private static int solvePart2(List<Board> boards, int[] numbers) {
    for (var n : numbers) {
      boards.forEach(b -> b.mark(n));

      var newBoards = boards.stream().filter(b -> !b.hasBingo()).toList();
      if (newBoards.isEmpty()) return boards.get(0).sumOfUnmarked() * n;

      boards = newBoards;
    }
    throw new IllegalArgumentException("input has no solution");
  }

  private static int[] parseCSVLine(String line) {
    var tokens = line.trim().split(",");
    return Arrays
      .stream(tokens)
      .mapToInt(Integer::parseInt)
      .toArray();
  }

  private static int[] parseSpaceSeparatedLine(String line) {
    var tokens = line.trim().split("\\s+");
    return Arrays
      .stream(tokens)
      .mapToInt(Integer::parseInt)
      .toArray();
  }

  private static int[] flatten(int[][] arr) {
    var result = new int[DIM*DIM];
    for (var i = 0; i < DIM*DIM; ++i) result[i] = arr[i/5][i%5];
    return result;
  }

  public static void main(String[] args) throws Exception {
    var lines = Files.readAllLines(Path.of("./../../input/input4.txt"));

    var numbers = parseCSVLine(lines.get(0));
    var boards = new ArrayList<Board>();

    for (var i = 2; i + DIM <= lines.size(); i += DIM+1) {
      var cells = new int[DIM][DIM];
      cells[0] = parseSpaceSeparatedLine(lines.get(i+0));
      cells[1] = parseSpaceSeparatedLine(lines.get(i+1));
      cells[2] = parseSpaceSeparatedLine(lines.get(i+2));
      cells[3] = parseSpaceSeparatedLine(lines.get(i+3));
      cells[4] = parseSpaceSeparatedLine(lines.get(i+4));
      boards.add(new Board(flatten(cells)));
    }

    var solution1 = solvePart1(boards, numbers);
    var solution2 = solvePart2(boards, numbers);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}
