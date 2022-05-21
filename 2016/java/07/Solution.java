import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.HashSet;

/**
 * Problem statement: https://adventofcode.com/2016/day/7
 */
public class Solution {

  private static boolean isBracket(char c) {
    return c == '[' || c == ']';
  }

  private static boolean isABBA(char c0, char c1, char c2, char c3) {
    if (isBracket(c0) || isBracket(c1)) return false;
    return (c0 != c1) && (c0 == c3) && (c1 == c2);
  }

  private static boolean isABA(char c0, char c1, char c2) {
    if (isBracket(c0) || isBracket(c1)) return false;
    return (c0 != c1) && (c0 == c2);
  }

  private static boolean supportsTLS(String ip) {
    if (ip.length() < 4) return false;

    char c0 = ip.charAt(0);
    char c1 = ip.charAt(1);
    char c2 = ip.charAt(2);

    boolean inBrackets =
      (c0 == '[' && c1 != ']' && c2 != ']') ||
      (c1 == '[' && c2 != ']') ||
      (c2 == '[');

    boolean hasABBA = false;

    for (int i = 3; i < ip.length(); ++i) {
      char c3 = ip.charAt(i);
      if (c3 == '[') inBrackets = true;
      else if (c3 == ']') inBrackets= false;
      else if (isABBA(c0, c1, c2, c3)) {
        if (inBrackets) return false;
        hasABBA = true;
      }

      c0 = c1;
      c1 = c2;
      c2 = c3;
    }

    return hasABBA;
  }

  private static boolean supportsSSL(String ip) {
    if (ip.length() < 8) return false;

    char c0 = ip.charAt(0);
    char c1 = ip.charAt(1);

    boolean inBrackets = (c0 == '[' && c1 != ']') || (c1 == '[');
    var wantedBabs = new HashSet<String>();
    var seenBabs = new HashSet<String>();

    for (int i = 2; i < ip.length(); ++i) {
      char c2 = ip.charAt(i);
      if (c2 == '[') inBrackets = true;
      else if (c2 == ']') inBrackets = false;
      else if (isABA(c0, c1, c2)) {
        if (inBrackets) seenBabs.add("" + c0 + c1 + c0);
        else wantedBabs.add("" + c1 + c0 + c1);
      }
      c0 = c1;
      c1 = c2;
    }

    for (var wantedBab : wantedBabs) {
      if (seenBabs.contains(wantedBab)) return true;
    }

    return false;
  }

  private static int solvePart1(List<String> lines) {
    int result = 0;
    for (var line : lines) {
      if (supportsTLS(line)) result++;
    }
    return result;
  }

  private static int solvePart2(List<String> lines) {
    int result = 0;
    for (var line : lines) {
      if (supportsSSL(line)) result++;
    }
    return result;
  }

  public static void main(String[] args) throws Exception {
    var lines = Files.readAllLines(Path.of("./../../input/input7.txt"));

    var solution1 = solvePart1(lines);
    var solution2 = solvePart2(lines);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}