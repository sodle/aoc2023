import kotlin.math.max
import kotlin.math.min

enum class Direction {
    NORTH, SOUTH, EAST, WEST
}

class Day10: BasePuzzle<List<String>, Int, Int>() {
    override fun getPuzzleInput(): List<String> {
        return readLines("day10.txt").filter { it.isNotBlank() }
    }

    private fun findStart(input: List<String>): Pair<Int, Int> {
        val row = input.indexOfFirst { it.contains('S') }
        val col = input[row].indexOf('S')
        return Pair(row, col)
    }

    private fun traverse(input: List<String>, start: Pair<Int, Int>, startDirection: Direction): List<Pair<Int, Int>> {
        val maxRow = input.size - 1
        val maxCol = input[0].length - 1

        val squaresVisited = mutableListOf<Pair<Int, Int>>()
        var direction = startDirection

        var (row, col) = start
        var pipe = 'S'

        while (true) {
            when (direction) {
                Direction.NORTH -> {
                    if (row == 0)
                        return listOf()

                    if (!"S|JL".contains(pipe))
                        return listOf()

                    row -= 1
                    pipe = input[row][col]
                    squaresVisited.add(Pair(row, col))
                    direction = when (pipe) {
                        '|' -> Direction.NORTH
                        'F' -> Direction.EAST
                        '7' -> Direction.WEST
                        'S' -> return squaresVisited
                        else -> return listOf()
                    }
                }
                Direction.SOUTH -> {
                    if (row == maxRow)
                        return listOf()

                    if (!"S|F7".contains(pipe))
                        return listOf()

                    row += 1
                    pipe = input[row][col]
                    squaresVisited.add(Pair(row, col))
                    direction = when (pipe) {
                        '|' -> Direction.SOUTH
                        'J' -> Direction.WEST
                        'L' -> Direction.EAST
                        'S' -> return squaresVisited
                        else -> return listOf()
                    }
                }
                Direction.EAST -> {
                    if (col == maxCol)
                        return listOf()

                    if (!"S-FL".contains(pipe))
                        return listOf()

                    col += 1
                    pipe = input[row][col]
                    squaresVisited.add(Pair(row, col))
                    direction = when (pipe) {
                        '-' -> Direction.EAST
                        'J' -> Direction.NORTH
                        '7' -> Direction.SOUTH
                        'S' -> return squaresVisited
                        else -> return listOf()
                    }
                }
                Direction.WEST -> {
                    if (col == 0)
                        return listOf()

                    if (!"S-J7".contains(pipe))
                        return listOf()

                    col -= 1
                    pipe = input[row][col]
                    squaresVisited.add(Pair(row, col))
                    direction = when (pipe) {
                        '-' -> Direction.WEST
                        'F' -> Direction.SOUTH
                        'L' -> Direction.NORTH
                        'S' -> return squaresVisited
                        else -> return listOf()
                    }
                }
            }
        }
    }

    override fun part1(input: List<String>): Int {
        val start = findStart(input)
        return listOf(
            traverse(input, start, Direction.NORTH),
            traverse(input, start, Direction.EAST),
            traverse(input, start, Direction.WEST),
            traverse(input, start, Direction.SOUTH)
        ).maxOf { it.size } / 2
    }

    private fun allPoints(input: List<String>): List<Pair<Int, Int>> {
        val height = input.size
        val width = input[0].length

        return (0..<height).flatMap { row ->
            (0..<width).map { col ->
                Pair(row, col)
            }
        }
    }

    override fun part2(input: List<String>): Int {
        val start = findStart(input)
        val path = listOf(
            traverse(input, start, Direction.NORTH),
            traverse(input, start, Direction.EAST),
            traverse(input, start, Direction.WEST),
            traverse(input, start, Direction.SOUTH)
        ).maxBy { it.size }

        return allPoints(input).filterNot {
            path.contains(it)
        }.count {
            pointInPolygon(it, path)
        }
    }

    private fun pointInPolygon(point: Pair<Int, Int>, poly: List<Pair<Int, Int>>): Boolean {
        // Point-In-Polygon search algorithm from https://www.eecs.umich.edu/courses/eecs380/HANDOUTS/PROJ2/InsidePoly.html
        val numVertices = poly.size
        var xinters: Int
        var counter = 0

        var p1 = poly.first()
        (1..<numVertices).map { idx ->
            val p2 = poly[idx % numVertices]
            if (point.first > min(p1.first, p2.first)) {
                if (point.first <= max(p1.first, p2.first)) {
                    if (point.second <= max(p1.second, p2.second)) {
                        if (p1.first != p2.first) {
                            xinters = (point.first - p1.first) * (p2.second - p1.second) / (p2.first - p1.first) + p1.second
                            if (p1.second == p2.second || point.second <= xinters) {
                                counter++
                            }
                        }
                    }
                }
            }
            p1 = p2
        }

        return counter % 2 != 0
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val dayClass = this::class.java.declaringClass.getDeclaredConstructor()
            val day = dayClass.newInstance() as BasePuzzle<*, *, *>
            day.main()
        }
    }
}