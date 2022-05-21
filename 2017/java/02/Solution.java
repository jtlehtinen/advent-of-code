import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.ToIntFunction;

/**
 * Problem statement: https://adventofcode.com/2017/day/2
 */
public class Solution {
  private static record Row(List<Integer> numbers) {
    public int min() {
      return Collections.min(numbers());
    }

    public int max() {
      return Collections.max(numbers());
    }
  }

  private static int solvePart1(List<Row> rows) {
    return rows
      .stream()
      .mapToInt(row -> row.max() - row.min())
      .reduce(0, (prev, curr) -> prev + curr);
  }

  private static int solvePart2(List<Row> rows) {
    ToIntFunction<Row> op = (row) -> {
      var n = row.numbers().size();

      for (var i = 0; i < n; ++i) {
        for (var j = i + 1; j < n; ++j) {
          var ith = row.numbers().get(i);
          var jth = row.numbers().get(j);

          var smaller = Math.min(ith, jth);
          var bigger = Math.max(ith, jth);

          if (smaller != 0 && (bigger % smaller) == 0) {
            return bigger / smaller;
          }
        }
      }

      return 0;
    };

    return rows
      .stream()
      .mapToInt(op)
      .reduce(0, (prev, curr) -> prev + curr);
  }

  private static List<Row> parse(String filename) throws IOException {
    var rows = new ArrayList<Row>();

    var lines = Files.readAllLines(Path.of(filename));

    for (var line : lines) {
      var nums = new ArrayList<Integer>();
      for (var token : line.split("\\s")) {
        nums.add(Integer.parseInt(token));
      }

      rows.add(new Row(nums));
    }

    return rows;
  }

  public static void main(String[] args) throws Exception {
    var rows = parse("./../../input/input2.txt");

    var solution1 = solvePart1(rows);
    var solution2 = solvePart2(rows);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}