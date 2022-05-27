import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.function.Predicate;

/**
 * Problem statement: https://adventofcode.com/2021/day/5
 */
public class Solution {

  private static record Point(int x, int y) {}

  private static record Line(Point start, Point end) {
    public boolean isHorizontal() { return start.y() == end.y(); }
    public boolean isVertical() { return start.x() == end.x(); }
  }

  private static int calcStep(int start, int end) {
    if (end > start) return 1;
    if (start == end) return 0;
    return -1;
  }

  private static int max(int a, int b, int c) { return Math.max(a, Math.max(b, c)); }
  private static int min(int a, int b, int c) { return Math.min(a, Math.min(b, c)); }

  private static int solve(List<Line> lines, Predicate<Line> filter) {
    var minX = Integer.MAX_VALUE;
    var minY = Integer.MAX_VALUE;
    var maxX = Integer.MIN_VALUE;
    var maxY = Integer.MIN_VALUE;
    for (var line : lines) {
      minX = min(minX, line.start.x(), line.end.x());
      maxX = max(maxX, line.start.x(), line.end.x());
      minY = min(minY, line.start.y(), line.end.y());
      maxY = max(maxY, line.start.y(), line.end.y());
    }

    int width = maxY - minY + 1;
    int height = maxX - minX + 1;
    var grid = new int[width * height];

    for (var line : lines) {
      if (!filter.test(line)) continue;

      var fromX = line.start.x() - minX;
      var fromY = line.start.y() - minY;
      var toX = line.end.x() - minX;
      var toY = line.end.y() - minY;

      var stepX = calcStep(fromX, toX);
      var stepY = calcStep(fromY, toY);
      var steps = Math.max(Math.abs(toX - fromX), Math.abs(toY - fromY)) + 1;

      for (var step = 0; step < steps; ++step) {
        grid[fromY * width + fromX] += 1;
        fromY += stepY;
        fromX += stepX;
      }
    }

    return (int)Arrays
      .stream(grid)
      .filter(i -> i >= 2)
      .count();
  }

  private static int solvePart1(List<Line> lines) {
    return solve(lines, line -> line.isVertical() || line.isHorizontal());
  }

  private static int solvePart2(List<Line> lines) {
    return solve(lines, line -> true);
  }

  private static List<Line> readInput(String filename) throws IOException {
    var pattern = Pattern.compile("^(\\d+),(\\d+) -> (\\d+),(\\d+)$");

    return Files
      .readAllLines(Path.of(filename))
      .stream()
      .map(line -> {
        var matcher = pattern.matcher(line);
        if (!matcher.matches()) throw new IllegalArgumentException("input does not match expected pattern");
        return new Line(
          new Point(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))),
          new Point(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)))
        );
      })
      .toList();
  }

  public static void main(String[] args) throws Exception {
    var input = readInput("./../../input/input5.txt");

    var solution1 = solvePart1(input);
    var solution2 = solvePart2(input);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}
