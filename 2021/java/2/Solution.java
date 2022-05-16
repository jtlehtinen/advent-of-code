import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Problem statement: https://adventofcode.com/2021/day/2
 */
public class Solution {
  private static record Command(String name, int count) {}

  private static int solvePart1(List<Command> commands) {
    int hor = 0;
    int dep = 0;

    for (var command : commands) {
      switch (command.name) {
        case "forward": hor += command.count; break;
        case "down": dep += command.count; break;
        case "up": dep -= command.count; break;
      }
    }

    return hor * dep;
  }

  private static int solvePart2(List<Command> commands) {
    int hor = 0;
    int dep = 0;
    int aim = 0;

    for (var command : commands) {
      switch (command.name) {
        case "forward":
          hor += command.count;
          dep += aim * command.count;
          break;
        case "down":
          aim += command.count;
          break;
        case "up":
          aim -= command.count;
          break;
      }
    }

    return hor * dep;
  }

  private static List<Command> readInput(String filename) throws IOException {
    var lines = Files.readAllLines(Path.of(filename));
    return lines.stream().map(line -> {
      var tokens = line.trim().split("\\s");
      var command = tokens[0];
      var count = Integer.parseInt(tokens[1]);
      return new Command(command, count);
    }).collect(Collectors.toList());
  }

  public static void main(String[] args) throws Exception {
    var input = readInput("./../../input/input2.txt");

    var solution1 = solvePart1(input);
    var solution2 = solvePart2(input);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}
