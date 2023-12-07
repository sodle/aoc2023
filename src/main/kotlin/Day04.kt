import kotlin.math.pow

class Scratcher(line: String) {
    var index: Int
    var scratchedNumbers: Set<Int>
    var winningNumbers: Set<Int>

    init {
        val (indexLine, withoutIndex) = line.split(':')
        val (scratched, winners) = withoutIndex.split('|')
        index = indexLine.split(' ').last().toInt()
        scratchedNumbers = scratched.split(' ').filter { it.isNotBlank() }.map { it.toInt() }.toSet()
        winningNumbers = winners.split(' ').filter { it.isNotBlank() }.map { it.toInt() }.toSet()
    }
}

class Day04: BasePuzzle<List<Scratcher>, Int, Int>() {
    override fun getPuzzleInput(): List<Scratcher> {
        return readLines("day04.txt").filter { it.isNotBlank() }.map {
            Scratcher(it)
        }
    }

    override fun part1(input: List<Scratcher>): Int {
        return input.map {
            it.scratchedNumbers.intersect(it.winningNumbers).size
        }.sumOf {
            2f.pow(it - 1).toInt()
        }
    }

    override fun part2(input: List<Scratcher>): Int {
        val copies = mutableListOf<Scratcher>()

        fun processCopies(scratcher: Scratcher) {
            val score = scratcher.scratchedNumbers.intersect(scratcher.winningNumbers).size
            (scratcher.index..<scratcher.index + score).forEach { idx ->
                copies.add(input[idx])
            }
        }

        input.forEach { scratcher ->
            processCopies(scratcher)
        }

        var copyIdx = 0
        while (copyIdx < copies.size) {
            val scratcher = copies[copyIdx]
            processCopies(scratcher)
            copyIdx++
        }
        return input.size + copies.size
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val dayClass = this::class.java.declaringClass.getDeclaredConstructor()
            val day = dayClass.newInstance() as BasePuzzle<*, *, *>
            day.main()
        }
    }
}