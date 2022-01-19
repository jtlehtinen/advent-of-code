import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Problem statement: https://adventofcode.com/2015/day/10
 */
public class Solution {

  private static String lookAndSay(String s) {
    if (s.isEmpty()) return "";

    int start = 0;
    var sb = new StringBuilder();
    for (int i = 1; i < s.length(); ++i) {
      if (s.charAt(i) != s.charAt(start)) {
        sb.append(i - start);
        sb.append(s.charAt(start));
        start = i;
      }
    }
    sb.append(s.length() - start);
    sb.append(s.charAt(start));
    return sb.toString();
  }

  private static String lookAndSay(String s, int times) {
    for (int i = 0; i < times; ++i) {
      s = lookAndSay(s);
    }
    return s.length();
  }

  private static int solvePart1(String in) {
    return lookAndSay(in, 40);
  }

  private static int solvePart2(String in) {
    return lookAndSay(in, 50);
  }

  public static void main(String[] args) throws Exception {
    var in = Files.readString(Path.of("./../../input/input10.txt"));

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}