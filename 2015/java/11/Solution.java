import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

/**
 * Problem statement: https://adventofcode.com/2015/day/11
 */
public class Solution {

  private static void increment(char[] s) {
    for (int i = s.length - 1; i >= 0; --i) {
      if (s[i] == 'z') {
        s[i] = 'a';
      } else {
        s[i] += 1;
        break;
      }
    }
  }

  private static boolean threeSuccessiveLetters(char[] s) {
    for (int i = 2; i < s.length; ++i) {
      if (s[i] == (s[i - 2] + 2) && s[i] == (s[i - 1] + 1)) return true;
    }
    return false;
  }

  private static boolean containsBannedLetter(char[] s) {
    for (char c : s) {
      if (c == 'i' || c == 'o' || c == 'l') return true;
    }
    return false;
  }

  private static boolean hasTwoPairs(char[] s) {
    var pairs = new HashSet<Character>();
    for (int i = 1; i < s.length; ++i) {
      if (s[i - 1] == s[i]) {
        if (!pairs.isEmpty() && !pairs.contains(s[i])) return true;

        pairs.add(s[i]);
      }
    }
    return false;
  }

  private static boolean isValidPassword(char[] s) {
    return !containsBannedLetter(s) && threeSuccessiveLetters(s) && hasTwoPairs(s);
  }

  private static String solvePart1(String in) {
    var arr = in.toCharArray();
    do {
      increment(arr);
    } while (!isValidPassword(arr));
    return new String(arr);
  }

  private static String solvePart2(String in) {
    return solvePart1(solvePart1(in));
  }

  public static void main(String[] args) throws Exception {
    var in = Files.readString(Path.of("./../../input/input11.txt"));

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}