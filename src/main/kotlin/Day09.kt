class Day09: BasePuzzle<List<List<Int>>, Int, Int>() {
    override fun getPuzzleInput(): List<List<Int>> {
        return readLines("day09.txt").filter { it.isNotBlank() }.map { line ->
            line.split(' ').map { it.toInt() }
        }
    }

    override fun part1(input: List<List<Int>>): Int {
        return input.sumOf { s ->
            val sequence = s.toMutableList()
            val steps = calculateSteps(sequence)

            steps.forEachIndexed { index, step ->
                if (index == 0) {
                    step.add(0)
                } else {
                    step.add(step.last() + steps[index - 1].last())
                }
            }
            steps.last().last()
        }
    }

    override fun part2(input: List<List<Int>>): Int {
        return input.sumOf { s ->
            val sequence = s.toMutableList()
            val steps = calculateSteps(sequence)

            steps.forEachIndexed { index, step ->
                if (index == 0) {
                    step.add(0, 0)
                } else {
                    step.add(0, step.first() - steps[index - 1].first())
                }
            }
            steps.last().first()
        }
    }

    private fun calculateSteps(sequence: MutableList<Int>): MutableList<MutableList<Int>> {
        val steps = mutableListOf(sequence)
        var previousStep = sequence

        while (previousStep.filterNot { it == 0 }.isNotEmpty()) {
            val nextStep = previousStep.mapIndexedNotNull { index, i ->
                when (index) {
                    0 -> null
                    else -> i - previousStep[index - 1]
                }
            }.toMutableList()
            previousStep = nextStep
            steps.add(nextStep)
        }

        steps.reverse()
        return steps
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val dayClass = this::class.java.declaringClass.getDeclaredConstructor()
            val day = dayClass.newInstance() as BasePuzzle<*, *, *>
            day.main()
        }
    }
}