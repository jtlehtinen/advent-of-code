import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Problem statement: https://adventofcode.com/2016/day/6
 */
public class Solution {
  private static String solvePart1(List<String> lines) {
    var sb = new StringBuilder();
    for (int i = 0; i < lines.get(0).length(); ++i) {
      var max = 0;
      var histo = new int[26];
      for (var line : lines) {
        var idx = line.charAt(i) - 'a';
        histo[idx]++;
        if (histo[idx] > histo[max]) max = idx;
      }
      sb.append((char)(max + 'a'));
    }
    return sb.toString();
  }

  private static String solvePart2(List<String> lines) {
    var sb = new StringBuilder();
    for (int i = 0; i < lines.get(0).length(); ++i) {
      var histo = new int[26];
      for (var line : lines) histo[line.charAt(i) - 'a']++;

      int minIndex = -1;
      int minValue = Integer.MAX_VALUE;
      for (int j = 0; j < histo.length; ++j) {
        if (histo[j] > 0 && histo[j] < minValue) {
          minIndex = j;
          minValue = histo[j];
        }
      }

      sb.append((char)(minIndex + 'a'));
    }
    return sb.toString();
  }

  public static void main(String[] args) throws Exception {
    var lines = Files.readAllLines(Path.of("./../../input/input6.txt"));

    var solution1 = solvePart1(lines);
    var solution2 = solvePart2(lines);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}