import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Problem statement: https://adventofcode.com/2020/day/2
 */
public class Solution {
  private static record PasswordPolicy(String password, char character, int min, int max) { }

  private static int charInStringCount(String in, char what) {
    var count = 0;
    for (char c : in.toCharArray()) {
      if (c == what) ++count;
    }
    return count;
  }

  private static int solvePart1(ArrayList<PasswordPolicy> in) {
    var validCount = 0;
    for (var policy : in) {
      var count = charInStringCount(policy.password(), policy.character());
      if (count >= policy.min() && count <= policy.max()) {
        ++validCount;
      }
    }
    return validCount;
  }

  private static int solvePart2(ArrayList<PasswordPolicy> in) {
    var validCount = 0;
    for (var policy : in) {
      var first = policy.password().charAt(policy.min() - 1) == policy.character();
      var second = policy.password().charAt(policy.max() - 1) == policy.character();
      if (first != second) ++validCount;
    }
    return validCount;
  }

  private static ArrayList<PasswordPolicy> readInput(String filename) throws IOException {
    var pattern = Pattern.compile("^(\\d+)-(\\d+) (\\S): (\\S+)");

    var lines = Files.readAllLines(Path.of(filename));
    var result = new ArrayList<PasswordPolicy>();

    for (var line : lines) {
      var matcher = pattern.matcher(line.trim());

      if (!matcher.matches())
        throw new IllegalArgumentException("invalid input '" + line + "'");

      var min = Integer.parseInt(matcher.group(1));
      var max = Integer.parseInt(matcher.group(2));
      var character = matcher.group(3).charAt(0);
      var password = matcher.group(4);

      result.add(new PasswordPolicy(password, character, min, max));
    }

    return result;
  }

  public static void main(String[] args) throws Exception {
    var in = readInput("./../../input/input2.txt");

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}
