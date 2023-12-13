import kotlin.math.min

class Day13: BasePuzzle<List<List<String>>, Int, Int>() {
    override fun getPuzzleInput(): List<List<String>> {
        return this::class.java.getResource("day13.txt")!!.readText()
            .split("\n\n").filter { it.isNotBlank() }
            .map { it.lines().filter { l -> l.isNotBlank() } }
    }

    private fun findReflections(pattern: List<String>): List<Int> {
        val numLines = pattern.count()
        return (1..<numLines).filter {
            val width = min(it, numLines-it)
            val startIndexLeft = it - width
            val endIndexLeft = it - 1
            val startIndexRight = it
            val endIndexRight = it + width - 1
            val left = pattern.slice(startIndexLeft..endIndexLeft)
            val right = pattern.slice(startIndexRight..endIndexRight)
            left == right.reversed()
        }
    }

    private fun transpose(pattern: List<String>): List<String> {
        return List(pattern[0].length) { index ->
            pattern.joinToString("") { it[index].toString() }
        }
    }

    override fun part1(input: List<List<String>>): Int {
        return input.sumOf {
            val rowScore = findReflections(it).sum() * 100
            val colScore = findReflections(transpose(it)).sum()
            rowScore + colScore
        }
    }

    override fun part2(input: List<List<String>>): Int {
        return input.sumOf {
            val originalRowReflections = findReflections(it).toSet()
            val originalColReflections = findReflections(transpose(it)).toSet()
            it.forEachIndexed { row, _ ->
                it[row].forEachIndexed { col, c ->
                    val alt = it.toMutableList()
                    val builder = StringBuilder(alt[row])
                    builder.setCharAt(col, when(c) {
                        '.' -> '#'
                        '#' -> '.'
                        else -> ' '
                    })
                    alt[row] = builder.toString()
                    val newRowReflections = findReflections(alt).toSet()
                    val newColReflections = findReflections(transpose(alt)).toSet()
                    val addedRows = newRowReflections - originalRowReflections
                    val addedCols = newColReflections - originalColReflections

                    if (addedRows.isNotEmpty()) {
                        return@sumOf addedRows.first() * 100
                    }

                    if (addedCols.isNotEmpty()) {
                        return@sumOf addedCols.first()
                    }
                }
            }
            0
        }
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val dayClass = this::class.java.declaringClass.getDeclaredConstructor()
            val day = dayClass.newInstance() as BasePuzzle<*, *, *>
            day.main()
        }
    }
}