import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Problem statement: https://adventofcode.com/2017/day/4
 */
public class Solution {
  private static record Row(List<String> words) { }

  private static boolean allUnique(List<String> words) {
    var seen = new HashSet<String>();
    for (var word : words) {
      if (seen.contains(word)) {
        return false;
      }
      seen.add(word);
    }
    return true;
  }

  private static boolean containsAnagrams(List<String> words) {
    // @TODO: Inefficient
    var sorted = new ArrayList<String>();

    for (var word : words) {
      var chars = word.toCharArray();
      Arrays.sort(chars);
      sorted.add(String.valueOf(chars));
    }

    Collections.sort(sorted);

    for (var i = 1; i < sorted.size(); ++i) {
      if (sorted.get(i - 1).equals(sorted.get(i))) {
        return true;
      }
    }

    return false;
  }

  private static long solvePart1(List<Row> rows) {
    return rows
      .stream()
      .map(Row::words)
      .filter(Solution::allUnique)
      .count();
  }

  private static long solvePart2(List<Row> rows) {
    return rows
      .stream()
      .map(Row::words)
      .filter(words -> allUnique(words) && !containsAnagrams(words))
      .count();
  }

  private static List<Row> readInput(String filename) throws IOException {
    var lines = Files.readAllLines(Path.of(filename));

    var result = new ArrayList<Row>();
    for (var line : lines) {
      var words = line.trim().split("\\s");
      result.add(new Row(Arrays.asList(words)));
    }
    return result;
  }

  public static void main(String[] args) throws Exception {
    var in = readInput("./../../input/input4.txt");

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}