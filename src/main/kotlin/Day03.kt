data class Number(
    val y: Int,
    val x: List<Int>,
    val n: Int,
) {
    fun occupies(x: Int, y: Int): Boolean {
        return y == this.y && this.x.contains(x)
    }
}

class Schematic(schematicLines: List<String>) {
    var symbols = mutableMapOf<Pair<Int, Int>, Char>()
    var numbers = mutableListOf<Number>()

    init {
        schematicLines.forEachIndexed { y, line ->
            var numberInProgress = ""
            val numberCoordinates = mutableListOf<Int>()

            fun resetNumber() {
                if (numberInProgress.isNotEmpty()) {
                    numbers.addLast(Number(y, numberCoordinates.toList(), numberInProgress.toInt()))
                }
                numberInProgress = ""
                numberCoordinates.clear()
            }

            line.forEachIndexed { x, c ->
                if (c == '.' || c.isWhitespace()) {
                    resetNumber()
                } else if (c.isDigit()) {
                    numberInProgress += c
                    numberCoordinates.addLast(x)
                } else {
                    symbols[Pair(x, y)] = c
                    resetNumber()
                }
            }

            resetNumber()
        }
    }

    fun isPartNumber(n: Number): Boolean {
        val symbolCoordinates = symbols.keys.toSet()

        val y = n.y
        n.x.forEach { x ->
            val neighbors = neighborCoordinates(x, y)
            if (neighbors.intersect(symbolCoordinates).isNotEmpty()) {
                return true
            }
        }

        return false
    }

    private fun neighborCoordinates(x: Int, y: Int) = setOf(
        Pair(x - 1, y - 1),
        Pair(x - 1, y),
        Pair(x - 1, y + 1),
        Pair(x, y - 1),
        Pair(x, y + 1),
        Pair(x + 1, y - 1),
        Pair(x + 1, y),
        Pair(x + 1, y + 1),
    )

    val gears: List<Int> get() {
        val gears = mutableListOf<Int>()
        
        val stars = symbols.filter { it.value == '*' }.map { it.key }
        stars.forEach { star ->
            val neighbors = mutableSetOf<Number>()
            neighborCoordinates(star.first, star.second).forEach { coordinate ->
                neighbors += numbers.filter { it.occupies(coordinate.first, coordinate.second) }
            }

            if (neighbors.size == 2) {
                gears.addLast(neighbors.first().n * neighbors.last().n)
            }
        }
        
        return gears
    }
}

class Day03: BasePuzzle<Schematic, Int, Int>() {
    override fun getPuzzleInput(): Schematic {
        return Schematic(readLines("day03.txt"))
    }

    override fun part1(input: Schematic): Int {
        return input.numbers.filter { input.isPartNumber(it) }.sumOf { it.n }
    }

    override fun part2(input: Schematic): Int {
        return input.gears.sum()
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val dayClass = this::class.java.declaringClass.getDeclaredConstructor()
            val day = dayClass.newInstance() as BasePuzzle<*, *, *>
            day.main()
        }
    }
}