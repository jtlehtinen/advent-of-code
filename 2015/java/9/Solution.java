import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Problem statement: https://adventofcode.com/2015/day/9
 */
public class Solution {
  private static int ALL_VISITED_MASK = 0;

  private static boolean isVisited(int visitedBitField, int cityIndex) {
    return (visitedBitField & (1 << cityIndex)) != 0;
  }

  private static int shortestRoute(List<String> cities, Map<String, Map<String, Integer>> distances, String currentCity, int visitedBitField) {
    if (visitedBitField == ALL_VISITED_MASK) return 0;

    int shortest = Integer.MAX_VALUE;
    for (int i = 0; i < cities.size(); ++i) {
      if (!isVisited(visitedBitField, i)) {
        var city = cities.get(i);
        int distance = distances.get(currentCity).get(city);
        shortest = Math.min(shortest, distance + shortestRoute(cities, distances, city, visitedBitField | (1 << i)));
      }
    }
    return shortest;
  }

  private static int longestRoute(List<String> cities, Map<String, Map<String, Integer>> distances, String currentCity, int visitedBitField) {
    if (visitedBitField == ALL_VISITED_MASK) return 0;

    int longest = Integer.MIN_VALUE;
    for (int i = 0; i < cities.size(); ++i) {
      if (!isVisited(visitedBitField, i)) {
        var city = cities.get(i);
        int distance = distances.get(currentCity).get(city);
        longest = Math.max(longest, distance + longestRoute(cities, distances, city, visitedBitField | (1 << i)));
      }
    }
    return longest;
  }

  private static int solvePart1(Map<String, Map<String, Integer>> distances) {
    var cities = new ArrayList<>(distances.keySet());
    int shortest = Integer.MAX_VALUE;
    for (int i = 0; i < cities.size(); ++i) {
      shortest = Math.min(shortest, shortestRoute(cities, distances, cities.get(i), (1 << i)));
    }
    return shortest;
  }

  private static int solvePart2(Map<String, Map<String, Integer>> distances) {
    var cities = new ArrayList<>(distances.keySet());
    int longest = Integer.MIN_VALUE;
    for (int i = 0; i < cities.size(); ++i) {
      longest = Math.max(longest, longestRoute(cities, distances, cities.get(i), (1 << i)));
    }
    return longest;
  }

  private static Map<String, Map<String, Integer>> parseDistances(List<String> lines) {
    var distances = new HashMap<String, Map<String, Integer>>();

    for (var line : lines) {
      var tokens = line.split(" ");
      var cityA = tokens[0];
      var cityB = tokens[2];
      var distance = Integer.parseInt(tokens[4]);

      if (!distances.containsKey(cityA)) distances.put(cityA, new HashMap<>());
      if (!distances.containsKey(cityB)) distances.put(cityB, new HashMap<>());

      distances.get(cityA).put(cityB, distance);
      distances.get(cityB).put(cityA, distance);
    }

    return distances;
  }

  public static void main(String[] args) throws Exception {
    var lines = Files.readAllLines(Path.of("./../../input/input9.txt"));
    var distances = parseDistances(lines);
    ALL_VISITED_MASK = (1 << distances.size()) - 1;

    var solution1 = solvePart1(distances);
    var solution2 = solvePart2(distances);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}