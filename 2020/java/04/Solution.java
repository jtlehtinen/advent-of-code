import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Problem statement: https://adventofcode.com/2020/day/4
 */
public class Solution {
  private static record Record(Map<String, String> keyValuePairs) { }

  private static boolean containsRequiredFields(Record record) {
    return
      record.keyValuePairs.containsKey("byr") &&
      record.keyValuePairs.containsKey("iyr") &&
      record.keyValuePairs.containsKey("eyr") &&
      record.keyValuePairs.containsKey("hgt") &&
      record.keyValuePairs.containsKey("hcl") &&
      record.keyValuePairs.containsKey("ecl") &&
      record.keyValuePairs.containsKey("pid");
  }

  private static boolean isIntegerBetween(String value, int min, int max) {
    try {
      var i = Integer.parseInt(value);
      return i >= min && i <= max;
    } catch (NumberFormatException ex) {
      return false;
    }
  }

  private static boolean validateBYR(String value) {
    return isIntegerBetween(value, 1920, 2002);
  }

  private static boolean validateIYR(String value) {
    return isIntegerBetween(value, 2010, 2020);
  }

  private static boolean validateEYR(String value) {
    return isIntegerBetween(value, 2020, 2030);
  }

  private static boolean validateHGT(String value) {
    if (value.endsWith("cm")) {
      return isIntegerBetween(value.substring(0, value.length() - 2), 150, 193);
    }

    if (value.endsWith("in")) {
      return isIntegerBetween(value.substring(0, value.length() - 2), 59, 76);
    }

    return false;
  }

  private static boolean validateHCL(String value) {
    if (!value.startsWith("#")) return false;
    if (value.length() != 7) return false;

    for (char c : value.substring(1).toCharArray()) {
      if (!(c >= '0' && c <= '9') && !(c >= 'a' && c <= 'f')) return false;
    }

    return true;
  }

  private static boolean validateECL(String value) {
    return
      value.equals("amb") ||
      value.equals("blu") ||
      value.equals("brn") ||
      value.equals("gry") ||
      value.equals("grn") ||
      value.equals("hzl") ||
      value.equals("oth");
  }

  private static boolean validatePID(String value) {
    if (value.length() != 9) return false;
    for (char c : value.toCharArray()) {
      if (!(c >= '0' && c <= '9')) return false;
    }
    return true;
  }

  private static int solvePart1(List<Record> records) {
    int count = 0;
    for (var record : records) {
      if (containsRequiredFields(record)) ++count;
    }
    return count;
  }

  private static int solvePart2(List<Record> records) {
    int count = 0;
    for (var rec : records) {
      if (containsRequiredFields(rec)) {
        if (
          validateBYR(rec.keyValuePairs.get("byr")) &&
          validateIYR(rec.keyValuePairs.get("iyr")) &&
          validateEYR(rec.keyValuePairs.get("eyr")) &&
          validateHGT(rec.keyValuePairs.get("hgt")) &&
          validateHCL(rec.keyValuePairs.get("hcl")) &&
          validateECL(rec.keyValuePairs.get("ecl")) &&
          validatePID(rec.keyValuePairs.get("pid"))) {
          ++count;
        }
      }
    }
    return count;
  }

  private static List<Record> readInput(String filename) throws IOException {
    var lines = Files.readAllLines(Path.of(filename));

    var result = new ArrayList<Record>();
    var pairs = new HashMap<String, String>();
    for (var line : lines) {
      if (line.isEmpty()) {
        result.add(new Record(pairs));
        pairs = new HashMap<String, String>();
        continue;
      }

      var tokens = line.trim().split("\\s+");
      for (var token : tokens) {
        var keyAndPair = token.trim().split(":");
        pairs.put(keyAndPair[0], keyAndPair[1]);
      }
    }

    if (!pairs.isEmpty()) {
      result.add(new Record(pairs));
    }

    return result;
  }

  public static void main(String[] args) throws Exception {
    var in = readInput("./../../input/input4.txt");

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}
