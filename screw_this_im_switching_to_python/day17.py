from pathlib import Path

import networkx as nx
import networkx.exception as nxx


def read_grid() -> [[int]]:
    grid_path = Path(__file__).parent.joinpath('input').joinpath('day17.txt')
    with grid_path.open('r') as grid_file:
        return [[
            int(digit) for digit in line if digit.isdigit()
        ] for line in grid_file.readlines() if len(line) > 0]


def part1(grid: [[int]]) -> int:
    height = len(grid)
    width = len(grid[0])

    graph = nx.MultiDiGraph()
    for row in range(height):
        for col in range(width):
            # Up
            if row - 1 >= 0:
                graph.add_edge((row, col, 'L'), (row-1, col, 'U'), weight=grid[row-1][col])
                graph.add_edge((row, col, 'R'), (row-1, col, 'U'), weight=grid[row-1][col])
            if row - 2 >= 0:
                graph.add_edge((row, col, 'L'), (row-2, col, 'U'), weight=grid[row-2][col]+grid[row-1][col])
                graph.add_edge((row, col, 'R'), (row-2, col, 'U'), weight=grid[row-2][col]+grid[row-1][col])
            if row - 3 >= 0:
                graph.add_edge((row, col, 'L'), (row-3, col, 'U'),
                               weight=grid[row-3][col]+grid[row-2][col]+grid[row-1][col])
                graph.add_edge((row, col, 'R'), (row-3, col, 'U'),
                               weight=grid[row-3][col]+grid[row-2][col]+grid[row-1][col])
            # Down
            if row + 1 < height:
                graph.add_edge((row, col, 'L'), (row+1, col, 'D'), weight=grid[row+1][col])
                graph.add_edge((row, col, 'R'), (row+1, col, 'D'), weight=grid[row+1][col])
            if row + 2 < height:
                graph.add_edge((row, col, 'L'), (row+2, col, 'D'), weight=grid[row+2][col]+grid[row+1][col])
                graph.add_edge((row, col, 'R'), (row+2, col, 'D'), weight=grid[row+2][col]+grid[row+1][col])
            if row + 3 < height:
                graph.add_edge((row, col, 'L'), (row+3, col, 'D'),
                               weight=grid[row+3][col]+grid[row+2][col]+grid[row+1][col])
                graph.add_edge((row, col, 'R'), (row+3, col, 'D'),
                               weight=grid[row+3][col]+grid[row+2][col]+grid[row+1][col])
            # Left
            if col - 1 >= 0:
                graph.add_edge((row, col, 'U'), (row, col-1, 'L'), weight=grid[row][col-1])
                graph.add_edge((row, col, 'D'), (row, col-1, 'L'), weight=grid[row][col-1])
            if col - 2 >= 0:
                graph.add_edge((row, col, 'U'), (row, col-2, 'L'), weight=grid[row][col-2]+grid[row][col-1])
                graph.add_edge((row, col, 'D'), (row, col-2, 'L'), weight=grid[row][col-2]+grid[row][col-1])
            if col - 3 >= 0:
                graph.add_edge((row, col, 'U'), (row, col-3, 'L'),
                               weight=grid[row][col-3]+grid[row][col-2]+grid[row][col-1])
                graph.add_edge((row, col, 'D'), (row, col-3, 'L'),
                               weight=grid[row][col-3]+grid[row][col-2]+grid[row][col-1])
            # Right
            if col + 1 < width:
                graph.add_edge((row, col, 'U'), (row, col+1, 'R'), weight=grid[row][col+1])
                graph.add_edge((row, col, 'D'), (row, col+1, 'R'), weight=grid[row][col+1])
            if col + 2 < width:
                graph.add_edge((row, col, 'U'), (row, col+2, 'R'), weight=grid[row][col+2]+grid[row][col+1])
                graph.add_edge((row, col, 'D'), (row, col+2, 'R'), weight=grid[row][col+2]+grid[row][col+1])
            if col + 3 < width:
                graph.add_edge((row, col, 'U'), (row, col+3, 'R'),
                               weight=grid[row][col+3]+grid[row][col+2]+grid[row][col+1])
                graph.add_edge((row, col, 'D'), (row, col+3, 'R'),
                               weight=grid[row][col+3]+grid[row][col+2]+grid[row][col+1])

    return min(
        nx.algorithms.dijkstra_path_length(graph, (0, 0, 'D'), (height-1, width-1, 'D'), weight='weight'),
        nx.algorithms.dijkstra_path_length(graph, (0, 0, 'R'), (height-1, width-1, 'D'), weight='weight'),
        nx.algorithms.dijkstra_path_length(graph, (0, 0, 'D'), (height-1, width-1, 'R'), weight='weight'),
        nx.algorithms.dijkstra_path_length(graph, (0, 0, 'R'), (height-1, width-1, 'R'), weight='weight'),
    )


def part2(grid: [[int]]) -> int:
    height = len(grid)
    width = len(grid[0])

    graph = nx.MultiDiGraph()
    for row in range(height):
        for col in range(width):
            # Up
            start = max(row - 10, 0)
            end = max(row - 3, 0)
            search_range = range(start, end)
            for search_start in search_range:
                cost = 0
                for step in range(search_start, row):
                    cost += grid[step][col]
                graph.add_edge((row, col, 'L'), (search_start, col, 'U'), weight=cost)
                graph.add_edge((row, col, 'R'), (search_start, col, 'U'), weight=cost)
            # Down
            start = min(row + 4, height)
            end = min(row + 11, height)
            search_range = range(start, end)
            for search_start in search_range:
                cost = 0
                for step in range(row+1, search_start+1):
                    cost += grid[step][col]
                graph.add_edge((row, col, 'L'), (search_start, col, 'D'), weight=cost)
                graph.add_edge((row, col, 'R'), (search_start, col, 'D'), weight=cost)
            # Left
            start = max(col - 10, 0)
            end = max(col - 3, 0)
            search_range = range(start, end)
            for search_start in search_range:
                cost = 0
                for step in range(search_start, col):
                    cost += grid[row][step]
                graph.add_edge((row, col, 'U'), (row, search_start, 'L'), weight=cost)
                graph.add_edge((row, col, 'D'), (row, search_start, 'L'), weight=cost)
            # Right
            start = min(col + 4, width)
            end = min(col + 11, width)
            search_range = range(start, end)
            for search_start in search_range:
                cost = 0
                for step in range(col+1, search_start+1):
                    cost += grid[row][step]
                graph.add_edge((row, col, 'U'), (row, search_start, 'R'), weight=cost)
                graph.add_edge((row, col, 'D'), (row, search_start, 'R'), weight=cost)

    paths = []
    for out_direction in 'DRUL':
        for in_direction in 'DRUL':
            try:
                paths.append(nx.algorithms.dijkstra_path_length(
                    graph,
                    (0, 0, out_direction),
                    (height - 1, width - 1, in_direction),
                    weight='weight',
                ))
            except nxx.NetworkXNoPath:
                pass

    return min(paths)


def main():
    grid = read_grid()
    print('=======================')
    print(f'Part 1:\t{part1(grid)}')
    print(f'Part 2:\t{part2(grid)}')
    print('=======================')


if __name__ == '__main__':
    main()
