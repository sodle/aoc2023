class Day01: BasePuzzle<List<String>, Int, Int>() {
    override fun getPuzzleInput(): List<String> {
        return readLines("day01.txt").filter { it.isNotBlank() }
    }

    override fun part1(input: List<String>): Int {
        return input.map { line ->
            line.filter { it.isDigit() }
        }.map { line ->
            line.first().toString() + line.last().toString()
        }.sumOf { line ->
            line.toInt()
        }
    }

    private fun findDigitName(input: String): Int {
        if (input.isBlank())
            return 0

        if (input.startsWith("one"))
            return 1
        if (input.startsWith("two"))
            return 2
        if (input.startsWith("three"))
            return 3
        if (input.startsWith("four"))
            return 4
        if (input.startsWith("five"))
            return 5
        if (input.startsWith("six"))
            return 6
        if (input.startsWith("seven"))
            return 7
        if (input.startsWith("eight"))
            return 8
        if (input.startsWith("nine"))
            return 9

        if (input.first().isDigit())
            return input.first().digitToInt()

        return 0
    }

    override fun part2(input: List<String>): Int {
        return input.sumOf {
            var firstDigit = 0
            var lastDigit = 0

            for (i in it.indices) {
                firstDigit = findDigitName(it.substring(i))
                if (firstDigit > 0) {
                    break
                }
            }

            for (i in it.indices.reversed()) {
                lastDigit = findDigitName(it.substring(i))
                if (lastDigit > 0) {
                    break
                }
            }

            firstDigit * 10 + lastDigit
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