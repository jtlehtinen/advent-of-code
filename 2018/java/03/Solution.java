import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Problem statement: https://adventofcode.com/2018/day/3
 */
public class Solution {
  private static record Claim(int id, int left, int top, int width, int height) { }

  private static record Int2(int x, int y) { }

  private static Int2 maxCoords(List<Claim> claims) {
    int maxX = 0;
    int maxY = 0;
    for (var claim : claims) {
      maxX = Math.max(maxX, claim.left() + claim.width());
      maxY = Math.max(maxY, claim.top() + claim.height());
    }
    return new Int2(maxX, maxY);
  }

  private static void fillGridWithClaims(int[][] grid, List<Claim> claims) {
    for (var claim : claims) {
      int minX = claim.left();
      int minY = claim.top();
      int maxX = minX + claim.width();
      int maxY = minY + claim.height();

      for (int y = minY; y < maxY; ++y) {
        for (int x = minX; x < maxX; ++x) {
          ++grid[y][x];
        }
      }
    }
  }

  private static boolean overlapsWithOtherClaim(int[][] filleGrid, Claim claim) {
    int minX = claim.left();
    int minY = claim.top();
    int maxX = minX + claim.width();
    int maxY = minY + claim.height();

    for (int y = minY; y < maxY; ++y) {
      for (int x = minX; x < maxX; ++x) {
        if (filleGrid[y][x] > 1) return true;
      }
    }
    return false;
  }

  private static int solvePart1(List<Claim> claims) {
    var max = maxCoords(claims);
    var grid = new int[max.y][max.x];
    fillGridWithClaims(grid, claims);

    int result = 0;
    for (int y = 0; y < max.y; ++y) {
      for (int x = 0; x < max.x; ++x) {
        if (grid[y][x] > 1) {
          ++result;
        }
      }
    }
    return result;
  }

  private static int solvePart2(List<Claim> claims) {
    var max = maxCoords(claims);
    var grid = new int[max.y][max.x];
    fillGridWithClaims(grid, claims);

    var result = claims.stream().filter(claim -> !overlapsWithOtherClaim(grid, claim)).findFirst();
    if (!result.isPresent()) throw new IllegalArgumentException("input has no solution");

    return result.get().id();
  }

  private static List<Claim> readInput(String filename) throws IOException {
    var pattern = Pattern.compile("^#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)$");

    var result = new ArrayList<Claim>();

    for (var line : Files.readAllLines(Path.of(filename))) {
      var matcher = pattern.matcher(line);
      if (!matcher.matches()) throw new IllegalArgumentException("filename does not represent valid input file");

      var id = Integer.parseInt(matcher.group(1));
      var left = Integer.parseInt(matcher.group(2));
      var top = Integer.parseInt(matcher.group(3));
      var width = Integer.parseInt(matcher.group(4));
      var length = Integer.parseInt(matcher.group(5));

      result.add(new Claim(id, left, top, width, length));
    }

    return result;
  }

  public static void main(String[] args) throws Exception {
    var in = readInput("./../../input/input3.txt");

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}
