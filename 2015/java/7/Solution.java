import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Problem statement: https://adventofcode.com/2015/day/7
 */
public class Solution {
  private static boolean isNumber(String s) {
    return Character.isDigit(s.charAt(0));
  }

  private static String[] parseOperands(String from, String operator) {
    var operands = from.trim().split(operator);
    for (int i = 0; i < operands.length; ++i) operands[i] = operands[i].trim();
    return operands;
  }

  private static int applyOperator(String operator, int left, int right) {
    if (operator.equals("AND")) return left & right;
    if (operator.equals("OR")) return left | right;
    if (operator.equals("RSHIFT")) return left >> right;
    if (operator.equals("LSHIFT")) return left << right;
    throw new IllegalArgumentException("invalid operator");
  }

  private static boolean handleUnaryOperator(HashMap<String, Integer> signals, String operator, String operand, String dest) {
    boolean num = isNumber(operand);
    boolean key = signals.containsKey(operand);
    if (!(num || key)) return false;

    int value = num ? Integer.parseInt(operand) : signals.get(operand);
    signals.put(dest, operator.equals("NOT") ? ~value : value);

    return true;
  }

  private static boolean handleBinaryOperator(HashMap<String, Integer> signals, String operator, String[] operands, String dest) {
    boolean lnum = isNumber(operands[0]);
    boolean lkey = signals.containsKey(operands[0]);

    boolean rnum = isNumber(operands[1]);
    boolean rkey = signals.containsKey(operands[1]);

    if (!(lnum || lkey) || !(rnum || rkey)) return false;

    int left = lnum ? Integer.parseInt(operands[0]) : signals.get(operands[0]);
    int right = rnum ? Integer.parseInt(operands[1]) : signals.get(operands[1]);
    signals.put(dest, applyOperator(operator, left, right));
    return true;
  }

  private static int solvePart1(List<String> in) {
    var inCopy = new ArrayList<String>(in);
    var signals = new HashMap<String, Integer>();

    // @TODO: wasteful... recursive starting from 'a'?
    while (!inCopy.isEmpty()) {
      for (int i = inCopy.size() - 1; i >= 0; --i) {
        var line = inCopy.get(i);
        var parts = line.split("->");
        for (int j = 0; j < parts.length; ++j) parts[j] = parts[j].trim();

        if (line.contains("AND")) {
          if (handleBinaryOperator(signals, "AND", parseOperands(parts[0], "AND"), parts[1])) {
            inCopy.remove(i);
          }
        } else if (line.contains("OR")) {
          if (handleBinaryOperator(signals, "OR", parseOperands(parts[0], "OR"), parts[1])) {
            inCopy.remove(i);
          }
        } else if (line.contains("RSHIFT")) {
          if (handleBinaryOperator(signals, "RSHIFT", parseOperands(parts[0], "RSHIFT"), parts[1])) {
            inCopy.remove(i);
          }
        } else if (line.contains("LSHIFT")) {
          if (handleBinaryOperator(signals, "LSHIFT", parseOperands(parts[0], "LSHIFT"), parts[1])) {
            inCopy.remove(i);
          }
        } else if (line.contains("NOT")) {
          if (handleUnaryOperator(signals, "NOT", parts[0].substring("NOT".length()).trim(), parts[1])) {
            inCopy.remove(i);
          }
        } else {
          if (handleUnaryOperator(signals, "ASSIGN", parts[0], parts[1])) {
            inCopy.remove(i);
          }
        }
      }
    }
    return signals.get("a") & 0xffff;
  }

  private static int solvePart2(List<String> in) {
    int solution1 = solvePart1(in);

    var modified = new ArrayList<String>(in.size());
    for (var line : in) {
      var b = line.endsWith("-> b");
      modified.add(b ? solution1 + " -> b" : line);
    }

    return solvePart1(modified);
  }

  public static void main(String[] args) throws Exception {
    var in = Files.readAllLines(Path.of("./../../input/input7.txt"));

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}