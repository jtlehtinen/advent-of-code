import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Problem statement: https://adventofcode.com/2015/day/8
 */
public class Solution {

  private static char peek(String s, int idx) {
    if (idx >= s.length()) return '\0';
    return s.charAt(idx);
  }

  private static boolean isHex(char c) {
    return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F');
  }

  private static int countDataLength(String s) {
    int remove = 0;
    for (int i = 0; i < s.length(); ++i) {
      char c = s.charAt(i);
      if (c == '\\') {
        char next = peek(s, i + 1);
        if (next == '\\') {
          i += 1;
          remove += 1;
        } else if (next == '"') {
          i += 1;
          remove += 1;
        } else if (next == 'x' && isHex(peek(s, i + 2)) && isHex(peek(s, i + 3))) {
          i += 3;
          remove += 3;
        }
      }
    }
    return s.length() - remove;
  }

  private static String encode(String s) {
    var sb = new StringBuilder();
    sb.append('"');
    for (char c : s.toCharArray()) {
      if (c == '"') sb.append('\\').append('"');
      else if (c == '\\') sb.append('\\').append('\\');
      else sb.append(c);
    }
    sb.append('"');
    return sb.toString();
  }

  private static int solvePart1(List<String> in) {
    int result = 0;
    for (var line : in) {
      result += line.length() - countDataLength(line.substring(1, line.length() - 1));
    }
    return result;
  }

  private static int solvePart2(List<String> in) {
    var newIn = new ArrayList<String>(in.size());
    for (var s : in) newIn.add(encode(s));

    return solvePart1(newIn);
  }

  public static void main(String[] args) throws Exception {
    var in = Files.readAllLines(Path.of("./../../input/input8.txt"));

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}