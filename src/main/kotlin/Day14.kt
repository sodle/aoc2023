private const val ONE_BILLION = 1000 * 1000 * 1000

class Day14: BasePuzzle<Grid<Char>, Long, Long>() {
    override fun getPuzzleInput(): Grid<Char> {
        return Grid.charFromFile(this::class.java.getResource("day14.txt")!!)
    }

    // We memoize here but it doesn't really help anything
    private val dropRocksMemo = mutableMapOf<List<Char>, List<Char>>()
    private fun dropRocks(stack: List<Char>): List<Char> {
        return dropRocksMemo.getOrPut(stack) {
            var rocks = 0
            var gaps = 0

            val newStack = mutableListOf<Char>()
            stack.forEach {
                when (it) {
                    'O' -> rocks++
                    '.' -> gaps++
                    '#' -> {
                        newStack.addAll((0..<rocks).map { 'O' })
                        newStack.addAll((0..<gaps).map { '.' })
                        rocks = 0
                        gaps = 0
                        newStack.add('#')
                    }
                }
            }
            newStack.addAll((0..<rocks).map { 'O' })
            newStack.addAll((0..<gaps).map { '.' })

            newStack
        }
    }

    private fun weighStack(stack: List<Char>): Long {
        return stack.mapIndexed { idx, it ->
            if (it == 'O') {
                stack.size - idx
            } else 0
        }.sum().toLong()
    }

    override fun part1(input: Grid<Char>): Long {
        (0..<input.width).forEach { col ->
            val slice = input.columnSlice(col)
            input[slice] = dropRocks(input[slice])
        }
        return (0..<input.width).sumOf {
            weighStack(input[input.columnSlice(it)])
        }
    }

    override fun part2(input: Grid<Char>): Long {
        // these are needed to rehydrate a Grid from history - Grid needs a copy/compare mechanism in the future.
        val height = input.height
        val width = input.width

        var repStart = -1
        var repEnd = -1
        val history = mutableListOf(input.flatten())
        while (repStart == -1 && repEnd == -1) {
            // tilt north
            (0..<input.width).forEach {
                val slice = input.columnSlice(it)
                input[slice] = dropRocks(input[slice])
            }
            // tilt west
            (0..<input.height).forEach {
                val slice = input.rowSlice(it)
                input[slice] = dropRocks(input[slice])
            }
            // tilt south
            (0..<input.width).forEach {
                val slice = input.columnSlice(it, reverse = true)
                input[slice] = dropRocks(input[slice])
            }
            // tilt east
            (0..<input.height).forEach {
                val slice = input.rowSlice(it, reverse = true)
                input[slice] = dropRocks(input[slice])
            }

            val flat = input.flatten()
            if (history.contains(flat)) {
                repStart = history.indexOf(flat)
                repEnd = history.size
            }
            history.add(flat)
        }
        val repeatSize = repEnd - repStart
        val cyclesRemaining = ONE_BILLION - repStart
        val repeatRemainder = cyclesRemaining % repeatSize

        val finalIdx = repeatRemainder + repStart
        val end = Grid(history[finalIdx].toMutableList(), height, width)

        return (0..<end.width).sumOf {
            weighStack(end[end.columnSlice(it)])
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