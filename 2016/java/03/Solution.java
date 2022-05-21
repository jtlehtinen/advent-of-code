import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Problem statement: https://adventofcode.com/2016/day/3
 */
public class Solution {
  private static int solvePart1(List<String> lines) {
    int result = 0;
    for (var line : lines) {
      var tokens = line.trim().split("\\s+");
      var a = Integer.parseInt(tokens[0]);
      var b = Integer.parseInt(tokens[1]);
      var c = Integer.parseInt(tokens[2]);

      if (a + b > c && b + c > a && c + a > b) {
        result++;
      }
    }
    return result;
  }

  private static int solvePart2(List<String> lines) {
    var col1 = new ArrayList<Integer>(lines.size());
    var col2 = new ArrayList<Integer>(lines.size());
    var col3 = new ArrayList<Integer>(lines.size());

    for (var line : lines) {
      var tokens = line.trim().split("\\s+");
      col1.add(Integer.parseInt(tokens[0]));
      col2.add(Integer.parseInt(tokens[1]));
      col3.add(Integer.parseInt(tokens[2]));
    }

    var sides = new ArrayList<Integer>(lines.size() * 3);
    sides.addAll(col1);
    sides.addAll(col2);
    sides.addAll(col3);

    int result = 0;
    for (int i = 0; i < sides.size(); i += 3) {
      var a = sides.get(i);
      var b = sides.get(i + 1);
      var c = sides.get(i + 2);


      if (a + b > c && b + c > a && c + a > b) {
        result++;
      }
    }
    return result;
  }

  public static void main(String[] args) throws Exception {
    var lines = Files.readAllLines(Path.of("./../../input/input3.txt"));

    var solution1 = solvePart1(lines);
    var solution2 = solvePart2(lines);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}