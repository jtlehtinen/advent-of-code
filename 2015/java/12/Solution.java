import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Problem statement: https://adventofcode.com/2015/day/12
 */
public class Solution {

  private static int solvePart1(String in) {
    int total = 0;

    var pattern = Pattern.compile("-*[0-9]+");
    var matcher = pattern.matcher(in);
    while (matcher.find()) {
      total += Integer.parseInt(matcher.group());
    }

    return total;
  }

  private static int solvePart2(String in) {
    return 0;
  }

  public static void main(String[] args) throws Exception {
    var in = Files.readString(Path.of("./../../input/input12.txt"));

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}