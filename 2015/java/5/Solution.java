import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.HashSet;

/**
 * Problem statement: https://adventofcode.com/2015/day/5
 */
public class Solution {
  private static boolean isVowel(char c) {
    return (c == 'a') || (c == 'e') || (c == 'i') || (c == 'o') || (c == 'u');
  }

  private static boolean isDenyPair(char c1, char c2) {
    if (c1 == 'a' && c2 == 'b') return true;
    if (c1 == 'c' && c2 == 'd') return true;
    if (c1 == 'p' && c2 == 'q') return true;
    if (c1 == 'x' && c2 == 'y') return true;
    return false;
  }

  private static boolean hasRepeat(String s, int step) {
    for (int i = step; i < s.length(); ++i) {
      if (s.charAt(i) == s.charAt(i - step)) return true;
    }
    return false;
  }

  private static int isNicePart1(String s) {
    var twice = false;
    var vowels = isVowel(s.charAt(0)) ? 1 : 0;
    for (int i = 1; i < s.length(); ++i) {
      var c1 = s.charAt(i - 1);
      var c2 = s.charAt(i);
      if (isDenyPair(c1, c2)) return 0;
      if (isVowel(c2)) vowels++;
      if (c1 == c2) twice = true;
    }
    return vowels >= 3 && twice ? 1 : 0;
  }

  private static int solvePart1(List<String> in) {
    int result = 0;
    for (var line : in) result += isNicePart1(line);
    return result;
  }

  private static int isNicePart2(String s) {
    if (!hasRepeat(s, 2)) return 0;

    var set = new HashSet<String>();
    set.add("" + s.charAt(0) + s.charAt(1));

    var prev = "" + s.charAt(1) + s.charAt(2);
    for (int i = 3; i < s.length(); ++i) {
      var next = "" + s.charAt(i - 1) + s.charAt(i);
      if (set.contains(next)) return 1;
      set.add(prev);
      prev = next;
    }
    return 0;
  }

  private static int solvePart2(List<String> in) {
    int result = 0;
    for (var line : in) result += isNicePart2(line);
    return result;
  }

  public static void main(String[] args) throws Exception {
    var in = Files.readAllLines(Path.of("../../input/input5.txt"));

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}