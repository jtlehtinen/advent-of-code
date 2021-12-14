//! Problem statement: https://adventofcode.com/2015/day/1

const std = @import("std");
const expect = std.testing.expect;

const data = @embedFile("../../input/input1.txt");

fn solve_part1(s: []const u8) i32 {
  var floor: i32 = 0;
  for (s) |c| {
    floor += @as(i32, if (c == '(') 1 else -1);
  }
  return floor;
}

fn solve_part2(s: []const u8) i32 {
  var floor: i32 = 0;
  for (s) |c, i| {
    floor += @as(i32, if (c == '(') 1 else -1);
    if (floor == -1) return @intCast(i32, i) + 1;
  }
  unreachable;
}

pub fn main() !void {
  std.debug.print("solution part 1: {}\n", .{solve_part1(data)});
  std.debug.print("solution part 2: {}\n", .{solve_part2(data)});
}

test "part 1" {
  try expect(solve_part1("(())") == 0);
  try expect(solve_part1("(()(()(") == 3);
  try expect(solve_part1("))(((((") == 3);
  try expect(solve_part1("())") == -1);
  try expect(solve_part1(")))") == -3);
  try expect(solve_part1(")())())") == -3);
  try expect(solve_part1(data) == 232);
}

test "part 2" {
  try expect(solve_part2(")") == 1);
  try expect(solve_part2("()())") == 5);
  try expect(solve_part2(data) == 1783);
}
