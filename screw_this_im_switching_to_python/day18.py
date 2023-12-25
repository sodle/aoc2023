from pathlib import Path


def read_dig_plan() -> [(str, int, str)]:
    plan = []

    dig_path = Path(__file__).parent.joinpath('input').joinpath('day18.txt')
    with dig_path.open('r') as dig_file:
        for dig_line in dig_file.readlines():
            [direction, distance, color] = dig_line.strip().split(' ')
            distance = int(distance)
            color = color[2:-1]
            plan.append((direction, distance, color))

    return plan


def draw(path: set[(int, int)]) -> str:
    rows = set([y for _, y in path])
    cols = set([x for x, _ in path])

    min_row = min(rows)
    max_row = max(rows)
    min_col = min(cols)
    max_col = max(cols)

    height = max_row - min_row
    width = max_col - min_col

    grid = [['.' for _ in range(width+1)] for _ in range(height+1)]
    for x, y in path:
        grid[y-min_row][x-min_col] = '#'

    print('\n'.join([''.join(row) for row in grid]))


def part1(plan: [(str, int, str)]) -> int:
    vertices = [(0, 0)]

    perimeter = 0
    for direction, distance, _ in plan:
        x, y = vertices[-1]
        match direction:
            case 'U': y -= distance
            case 'D': y += distance
            case 'L': x -= distance
            case 'R': x += distance
        vertices.append((x, y))
        perimeter += distance

    inner_area = shoelace(vertices)

    # Pick's theorem says the outer area is half the perimeter + 1
    outer_area = perimeter // 2 + 1

    return inner_area + outer_area


def part2(plan: [(str, int, str)]) -> int:
    vertices = [(0, 0)]

    perimeter = 0
    for _, _, instruction in plan:
        x, y = vertices[-1]

        distance = int(instruction[:5], 16)
        direction = int(instruction[5])

        match direction:
            case 0: x += distance
            case 1: y += distance
            case 2: x -= distance
            case 3: y -= distance

        vertices.append((x, y))
        perimeter += distance

    inner_area = shoelace(vertices)

    # Pick's theorem says the outer area is half the perimeter + 1
    outer_area = perimeter // 2 + 1

    return inner_area + outer_area


def shoelace(vertices):
    # Shoelace formula, to calculate inner area
    sum_x = 0
    sum_y = 0
    last_x = 0
    last_y = 0
    for x, y in vertices:
        sum_x += last_x * y
        sum_y += last_y * x
        last_x = x
        last_y = y
    inner_area = abs(sum_x - sum_y) // 2
    return inner_area


def main():
    plan = read_dig_plan()
    print('=======================')
    print(f'Part 1:\t{part1(plan)}')
    print(f'Part 2:\t{part2(plan)}')
    print('=======================')


if __name__ == '__main__':
    main()
