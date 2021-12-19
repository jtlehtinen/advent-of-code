import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;

/**
 * Problem statement: https://adventofcode.com/2015/day/4
 */
public class Solution {
  private static int topNibble(byte b) {
    return (b >> 4) & 0xf;
  }

  private static boolean hasFiveZerosPrefix(byte[] bytes) {
    return (Byte.toUnsignedInt(bytes[0]) + Byte.toUnsignedInt(bytes[1]) + topNibble(bytes[2])) == 0;
  }

  private static boolean hasSixZerosPrefix(byte[] bytes) {
    return (Byte.toUnsignedInt(bytes[0]) + Byte.toUnsignedInt(bytes[1]) + Byte.toUnsignedInt(bytes[1])) == 0;
  }

  private static byte[] md5(MessageDigest md, String text)  {
    md.reset();
    return md.digest(text.getBytes(StandardCharsets.UTF_8));
  }

  private static int solvePart1(MessageDigest md, String in) {
    for (int i = 1;; ++i) {
      if (hasFiveZerosPrefix(md5(md, in + i))) return i;
    }
  }

  private static int solvePart2(MessageDigest md, String in) {
    for (int i = 1;; ++i) {
      if (hasSixZerosPrefix(md5(md, in + i))) return i;
    }
  }

  public static void main(String[] args) throws Exception {
    var in = Files.readString(Path.of("../../input/input4.txt"));

    var md = MessageDigest.getInstance("MD5");
    var solution1 = solvePart1(md, in);
    var solution2 = solvePart2(md, in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}