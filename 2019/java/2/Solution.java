import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Problem statement: https://adventofcode.com/2019/day/2
 */
public class Solution {
  private static final int OPCODE_HALT = 99;
  private static final int OPCODE_ADD = 1;
  private static final int OPCODE_MUL = 2;

  private static int solve(int[] code, int address1Value, int address2Value) {
    code = Arrays.copyOf(code, code.length);
    code[1] = address1Value;
    code[2] = address2Value;

    for (var pc = 0; pc < code.length; pc += 4) {
      var opcode = code[pc];
      if (opcode == OPCODE_HALT)
        return code[0];

      if (pc + 3 >= code.length)
        throw new IllegalArgumentException("invalid instruction stream " + length);

      if (opcode == OPCODE_ADD) {
        code[code[pc+3]] = code[code[pc+1]] + code[code[pc+2]];
      } else if (opcode == OPCODE_MUL) {
        code[code[pc+3]] = code[code[pc+1]] * code[code[pc+2]];
      } else {
        throw new IllegalArgumentException("invalid opcode " + opcode);
      }
    }
    throw new IllegalArgumentException("input has no solution");
  }

  private static int solvePart1(int[] code) {
    return solve(code, 12, 2);
  }

  private static int solvePart2(int[] code) {
    code = Arrays.copyOf(code, code.length);

    for (var noun = 0; noun < 100; ++noun) {
      for (var verb = 0; verb < 100; ++verb) {
        try {
          if (solve(code, noun, verb) == 19690720) {
            return 100 * noun + verb;
          }
        } catch (IllegalArgumentException ex) { /* ignore */ }
      }
    }

    throw new IllegalArgumentException("input has no solution");
  }

  private static int[] readInput(String filename) throws IOException {
    var tokens = Files.readString(Path.of(filename)).trim().split(",");
    return Arrays.stream(tokens).mapToInt(Integer::parseInt).toArray();
  }

  public static void main(String[] args) throws Exception {
    var in = readInput("./../../input/input2.txt");

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}
