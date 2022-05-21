import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Problem statement: https://adventofcode.com/2016/day/4
 */
public class Solution {
  private static Character[] CHARACTER_ARRAY = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

  /**
   * @return non-negative sector id; or -1 if the room is not a real room
   */
  private static int realRoomSectorId(String line) {
    var common = new char[5];
    var counts = new int[26];
    var sector = 0;

    var charArray = line.toCharArray();
    for (int i = 0; i < charArray.length; ++i) {
      var c = charArray[i];
      if (c == '-'  || Character.isWhitespace(c)) continue;
      if (c == '[') {
        for (int j = 0; j < 5; ++j) common[j] = charArray[++i];
        break;
      }

      if (Character.isDigit(c)) sector = sector * 10 + (c - '0');
      else counts[c - 'a']++;
    }

    Arrays.sort(CHARACTER_ARRAY, (a, b) -> {
      var countA = counts[a - 'a'];
      var countB = counts[b - 'a'];
      if (countA != countB) return -Integer.compare(countA, countB);
      return Character.compare(a, b);
    });

    boolean realRoom =
        common[0] == CHARACTER_ARRAY[0] &&
        common[1] == CHARACTER_ARRAY[1] &&
        common[2] == CHARACTER_ARRAY[2] &&
        common[3] == CHARACTER_ARRAY[3] &&
        common[4] == CHARACTER_ARRAY[4];

    return realRoom ? sector : -1;
  }

  private static int solvePart1(List<String> lines) {
    int result = 0;
    for (var line : lines) {
      var sector = realRoomSectorId(line);
      if (sector != -1) result += sector;
    }
    return result;
  }

  private static int solvePart2(List<String> lines) {
    for (var line : lines) {
      var sector = realRoomSectorId(line);
      if (sector == -1) continue;

      var sb = new StringBuilder(line.substring(0, line.lastIndexOf('-')));
      for (int i = 0; i < sb.length(); ++i) {
        var newChar = (char)(((sb.charAt(i) - 'a' + sector) % CHARACTER_ARRAY.length) + 'a');
        sb.setCharAt(i, newChar);
      }

      var shifted = sb.toString();
      if (shifted.contains("north") && shifted.contains("pole")) return sector;
    }
    throw new IllegalArgumentException("invalid input");
  }

  public static void main(String[] args) throws Exception {
    var lines = Files.readAllLines(Path.of("./../../input/input4.txt"));

    var solution1 = solvePart1(lines);
    var solution2 = solvePart2(lines);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}