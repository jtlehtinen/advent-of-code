//! Problem statement: https://adventofcode.com/2015/day/2

const std = @import("std");
const expect = std.testing.expect;

const data = @embedFile("../../input/input2.txt");

fn surface_area(x: i32, y: i32, z: i32) i32 {
  return 2 * (x * y + y * z + z * x);
}

fn volume(x: i32, y: i32, z: i32) i32 {
  return x * y * z;
}

fn solve_part1(s: []const u8) i32 {
  var result: i32 = 0;
  var it = std.mem.tokenize(u8, s, "\n");
  while (it.next()) |line| {
    var it2 = std.mem.tokenize(u8, line, "x");
    var x = std.fmt.parseInt(i32, it2.next().?, 10) catch unreachable;
    var y = std.fmt.parseInt(i32, it2.next().?, 10) catch unreachable;
    var z = std.fmt.parseInt(i32, it2.next().?, 10) catch unreachable;
    result += surface_area(x, y, z) + std.math.min3(x * y, y * z, z * x);
  }
  return result;
}

fn solve_part2(s: []const u8) i32 {
  var result: i32 = 0;
  var it = std.mem.tokenize(u8, s, "\n");
  while (it.next()) |line| {
    var it2 = std.mem.tokenize(u8, line, "x");
    var x = std.fmt.parseInt(i32, it2.next().?, 10) catch unreachable;
    var y = std.fmt.parseInt(i32, it2.next().?, 10) catch unreachable;
    var z = std.fmt.parseInt(i32, it2.next().?, 10) catch unreachable;
    result += volume(x, y, z) + 2 * std.math.min3(x + y, y + z, z + x);
  }
  return result;
}

pub fn main() !void {
  std.debug.print("solution part 1: {}\n", .{solve_part1(data)});
  std.debug.print("solution part 2: {}\n", .{solve_part2(data)});
}

test "part 1" {
  try expect(solve_part1("2x3x4") == 58);
  try expect(solve_part1("1x1x10") == 43);
  try expect(solve_part1(data) == 1586300);
}

test "part 2" {
  try expect(solve_part2("2x3x4") == 34);
  try expect(solve_part2("1x1x10") == 14);
  try expect(solve_part2(data) == 3737498);
}
