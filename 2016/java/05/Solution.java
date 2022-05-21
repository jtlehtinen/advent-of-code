import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Problem statement: https://adventofcode.com/2016/day/5
 */
public class Solution {
  private static final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

  private static char hex(int value) {
    return HEX[value & 0b1111];
  }

  private static String toHexString(byte[] array) {
    var sb = new StringBuilder();
    for (var value : array) {
      sb.append(hex(value >> 4));
      sb.append(hex(value));
    }
    return sb.toString();
  }

  private static String hash(MessageDigest md, byte[] input) {
    md.reset();
    var hash = md.digest(input);
    return toHexString(hash);
  }

  private static String solvePart1(MessageDigest md, String in) {
    // @TODO: slow and dum...
    int i = 0;
    var sb = new StringBuilder();
    while (sb.length() < 8) {
      var h = hash(md, (in + i).getBytes(StandardCharsets.UTF_8));
      if (h.startsWith("00000")) sb.append(h.charAt(5));
      ++i;
    }
    return sb.toString();
  }

  private static String solvePart2(MessageDigest md, String in) {
    int filled = 0;
    var result = new char[8];

    for (int i = 0; filled < result.length; ++i) {
      var h = hash(md, (in + i).getBytes(StandardCharsets.UTF_8));
      if (h.startsWith("00000")) {
        var position = h.charAt(5) - '0';
        if (position < result.length && result[position] == 0) {
          result[position] = h.charAt(6);
          ++filled;
        }
      }
    }
    return new String(result);
  }

  public static void main(String[] args) throws Exception {
    var in = Files.readString(Path.of("./../../input/input5.txt"));

    MessageDigest md = MessageDigest.getInstance("MD5");
    var solution1 = solvePart1(md, in);
    var solution2 = solvePart2(md, in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}
