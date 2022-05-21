import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Problem statement: https://adventofcode.com/2017/day/7
 *
 * @TODO: This is a BIG MESS.
 */
public class Solution {
  private static record Row(String name, int weight, List<String> children) {  }

  private static String solvePart1(Map<String, Row>  in) {
    // A set of all children.
    var child = new HashSet<String>();
    in.values().forEach(row -> row.children().forEach(c -> child.add(c)));

    // Find all non-children.
    var roots = new ArrayList<String>();
    for (var name : in.keySet()) {
      if (!child.contains(name)) {
        roots.add(name);
      }
    }

    if (roots.size() != 1) {
      throw new IllegalArgumentException("there must a single root");
    }

    return roots.get(0);
  }

  private static Map<String, Integer> buildWeightMap(Map<String, Row> rows, String rootName, Map<String, Integer> result) {
    var root = rows.get(rootName);

    root.children().forEach(child -> buildWeightMap(rows, child, result));

    var weight = root.weight() + root.children().stream().mapToInt(result::get).sum();
    result.put(root.name(), weight);

    return result;
  }

  private static Stack<Row> buildProcessingStack(Map<String, Row> rows, String rootName, Stack<Row> result) {
    var root = rows.get(rootName);
    result.push(root);

    root.children().forEach(c -> buildProcessingStack(rows, c, result));

    return result;
  }

  private static boolean allSame(int[] arr) {
    if (arr.length < 2) return true;
    return Arrays.stream(arr).allMatch(a -> a == arr[0]);
  }

  private static double average(int[] arr) {
    return Arrays.stream(arr).sum() / (double)arr.length;
  }

  private static int outlierIndex(int[] arr) {
    var avg = average(arr);

    double maxDiff = 0.0;
    int result = 0;

    for (int i = 0; i < arr.length; ++i) {
      var diff = Math.abs(arr[i] - avg);
      if (diff > maxDiff) {
        maxDiff = diff;
        result = i;
      }
    }

    return result;
  }

  private static int solvePart2(Map<String, Row> in) {
    var root = solvePart1(in);

    var weights = buildWeightMap(in, root, new HashMap<String, Integer>());
    var next = buildProcessingStack(in, root, new Stack<Row>());

    while (!next.empty()) {
      var row = next.pop();

      var childWeights = row.children().stream().mapToInt(weights::get).toArray();
      if (allSame(childWeights)) continue;

      var idx = outlierIndex(childWeights);
      var outlierWeight = childWeights[idx];
      var expectedWeight = childWeights[idx == 0 ? 1 : 0];
      var diff = expectedWeight - outlierWeight;

      var outlierName = row.children().get(idx);
      var outlier = in.get(outlierName);
      return outlier.weight() + diff;
    }

    throw new IllegalStateException("no solution found");
  }

  private static Map<String, Row> readInput(String filename) throws IOException {
    var pattern = Pattern.compile("\\w+");
    var lines = Files.readAllLines(Path.of(filename));

    var result = new HashMap<String, Row>();
    for (var line : lines) {
      var tokens = new ArrayList<String>();

      var matcher = pattern.matcher(line.trim());
      while (matcher.find()) {
        tokens.add(matcher.group());
      }

      var name = tokens.remove(0);
      var weight = Integer.parseInt(tokens.remove(0));
      result.put(name, new Row(name, weight, tokens));
    }

    return result;
  }

  public static void main(String[] args) throws Exception {
    var in = readInput("./../../input/input7.txt");

    var solution1 = solvePart1(in);
    var solution2 = solvePart2(in);

    System.out.println("solution part 1: " + solution1);
    System.out.println("solution part 2: " + solution2);
  }
}
