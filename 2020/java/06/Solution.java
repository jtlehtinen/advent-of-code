import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Problem statement: https://adventofcode.com/2020/day/6
 */
public class Solution {
  private static record Group(List<String> answers) { }

  private static int countOfNonZero(int[] values) {
    int result = 0;
    for (var value : values) {
      if (value != 0) ++result;
    }
    return result;
  }

  private static int count(int[] values, int needle) {
    int result = 0;
    for (var value : values) {
      if (value == needle) ++result;
    }
    return result;
  }

  private static int solvePart1(List<Group> groups) {
    return groups
      .stream()
      .mapToInt(group -> {
        var counts = new int[26];
        for (var answers : group.answers) {
          for (var c : answers.toCharArray()) {
            counts[c - 'a'] += 1;
          }
        }
        return countOfNonZero(counts);
      })
      .reduce(0, (prev, curr) -> prev + curr);
  }

  private static int solvePart2(List<Group> groups) {
    return groups
      .stream()
      .mapToInt(group -> {
        var counts = new int[26];
        for (var answers : group.answers) {
          for (var c : answers.toCharArray()) {
            counts[c - 'a'] += 1;
          }
        }
        return count(counts, group.answers.size());
      })
      .reduce(0, (prev, curr) -> prev + curr);
  }

  private static List<Group> readInput(String filename) throws IOException {
    var lines = Files.readAllLines(Path.of(filename));

    var result = new ArrayList<Group>();

    var answers = new ArrayList<String>();
    for (var line : lines) {
      if (line.isBlank()) {
        result.add(new Group(answers));
        answers = new ArrayList<String>();
        continue;
      }
      answers.add(line);
    }
    if (!answers.isEmpty()) result.add(new Group(answers));

    return result;
  }

  public static void main(String[] args) throws Exception {
    var in = readInput("./../../input/input6.txt");

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}
