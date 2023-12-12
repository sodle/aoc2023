import kotlin.math.min

class Day11: BasePuzzle<List<String>, Long, Long>() {
    override fun getPuzzleInput(): List<String> {
        return readLines("day11.txt").filter { it.isNotBlank() }
    }

    private fun rowEmpty(input: List<String>, rowIdx: Int): Boolean {
        return !input[rowIdx].contains('#')
    }

    private fun colEmpty(input: List<String>, colIdx: Int): Boolean {
        return !input.map { it[colIdx] }.contains('#')
    }

    private fun numEmptyRows(input: List<String>, start: Int, end: Int): Int {
        return (start+1..<end).count {
            rowEmpty(input, it)
        }
    }

    private fun numEmptyCols(input: List<String>, start: Int, end: Int): Int {
        return (start+1..<end).count {
            colEmpty(input, it)
        }
    }

    private fun findGalaxies(input: List<String>): List<Pair<Int, Int>> {
        val galaxyCoordinates = mutableListOf<Pair<Int, Int>>()

        input.forEachIndexed { row, s ->
            s.forEachIndexed { col, c ->
                if (c == '#') {
                    galaxyCoordinates.add(Pair(row, col))
                }
            }
        }

        return galaxyCoordinates
    }

    private fun galacticDistance(input: List<String>, g1: Pair<Int, Int>, g2: Pair<Int, Int>, gapSize: Int = 1): Long {
        val minRow = minOf(g1.first, g2.first)
        val maxRow = maxOf(g1.first, g2.first)
        val minCol = minOf(g1.second, g2.second)
        val maxCol = maxOf(g1.second, g2.second)

        val emptyRows = numEmptyRows(input, minRow, maxRow).toLong() * gapSize
        val emptyCols = numEmptyCols(input, minCol, maxCol).toLong() * gapSize

        val verticalDistance = maxRow - minRow
        val horizontalDistance = maxCol - minCol

        return verticalDistance + horizontalDistance + emptyCols + emptyRows
    }

    override fun part1(input: List<String>): Long {
        val galaxies = findGalaxies(input)

        return galaxies.flatMap { from ->
            galaxies.map { to ->
                when(from) {
                    to -> 0
                    else -> galacticDistance(input, from, to)
                }
            }
        }.sum() / 2
    }

    override fun part2(input: List<String>): Long {
        val galaxies = findGalaxies(input)

        return galaxies.flatMap { from ->
            galaxies.map { to ->
                when(from) {
                    to -> 0
                    else -> galacticDistance(input, from, to, 999999)
                }
            }
        }.sum() / 2
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val dayClass = this::class.java.declaringClass.getDeclaredConstructor()
            val day = dayClass.newInstance() as BasePuzzle<*, *, *>
            day.main()
        }
    }
}