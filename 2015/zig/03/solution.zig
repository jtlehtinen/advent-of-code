//! Problem statement: https://adventofcode.com/2015/day/3

const std = @import("std");
const expect = std.testing.expect;

const data = @embedFile("../../input/input3.txt");

var arena = std.heap.GeneralPurposeAllocator(.{ }){ };
const allocator = arena.allocator();

const Point = struct { x: i32, y: i32, };

fn solve_part1(s: []const u8) u32 {
  var map = std.AutoHashMap(Point, i32).init(allocator);
  defer map.deinit();

  var point = Point{ .x = 0, .y = 0, };
  map.put(point, 1);

  for (s) |c| {
    switch (c) {
      '^' => point.y += 1,
      'v' => point.y -= 1,
      '>' => point.x += 1,
      '<' => point.x -= 1,
      else => unreachable,
    }
    map.put(point, 1);
  }

  return map.count();
}

fn solve_part2(s: []const u8) u32 {
  _ = s;
  return 0;
}

pub fn main() !void {
  std.debug.print("solution part 1: {}\n", .{solve_part1(data)});
  std.debug.print("solution part 2: {}\n", .{solve_part2(data)});
}

test "part 1" {
  try expect(solve_part1(">") == 2);
  try expect(solve_part1("^>v<") == 4);
  try expect(solve_part1("^v^v^v^v^v") == 2);
  try expect(solve_part1(data) == 2081);
}

test "part 2" {
  //try expect(solve_part2("^v") == 3);
  //try expect(solve_part2("^>v<") == 3);
  //try expect(solve_part2("^v^v^v^v^v") == 11);
  //try expect(solve_part2(data) == 2341);
}
