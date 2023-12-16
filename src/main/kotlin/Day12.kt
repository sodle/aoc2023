internal typealias SpringRow = Pair<String, List<Int>>

class Day12: BasePuzzle<List<SpringRow>, Long, Long>() {
    override fun getPuzzleInput(): List<SpringRow> {
        return readLines("day12.txt").filter { it.isNotBlank() }.map {
            val (line, constraintString) = it.split(' ')
            val constraints = constraintString.split(',').map { c -> c.toInt() }
            SpringRow(line, constraints)
        }
    }

    private fun unfoldConstraints(constraint: List<Int>): List<Int> {
        return constraint + constraint + constraint + constraint + constraint
    }

    override fun part1(input: List<SpringRow>): Long {
        return input.sumOf {
             checkPermutation(it.first, it.second, false)
        }
    }

    private val memo = mutableMapOf<SpringRow, Long>()

    private fun checkPermutation(permutation: String, constraints: List<Int>, unfold: Boolean = false): Long {
        var fullConstraints = if (unfold) unfoldConstraints(constraints) else constraints
        var fullPermutation = if (unfold) listOf(permutation, permutation, permutation, permutation, permutation)
            .joinToString("?") else permutation

        val key = Pair(fullPermutation, fullConstraints)
        if (memo.contains(key)) {
            return memo[key]!!
        }

        while (true) {
            // remove leading intact springs, we don't care about them
            fullPermutation = fullPermutation.dropWhile { it == '.' }

            if (fullPermutation.isBlank() && fullConstraints.isEmpty()) {
                // if we empty out both lists at the same time, we succeeded
                return 1
            }

            if (fullPermutation.isBlank()) {
                // if we empty out the permutation before the constraints, we failed
                return 0
            }

            if (fullConstraints.isEmpty()) {
                // if we empty out the constraints first,
                // we've succeeded iff the remaining permutation contains no broken springs
                return if (fullPermutation.contains('#')) {
                    0
                } else {
                    1
                }
            }

            val nextConstraint = fullConstraints.first()
            if (fullPermutation.length == nextConstraint && fullConstraints.size == 1) {
                // if the last constraint matches the remaining length of the permutation,
                // we've succeeded iff it contains only broken and unknown springs
                return if (fullPermutation.contains('.')) {
                    0
                } else {
                    1
                }
            }

            if (fullPermutation.length < nextConstraint + 1) {
                // If there aren't enough springs left to satisfy the constraint, we've failed
                return 0
            }

            if (fullPermutation.startsWith('#')) {
                // if the next spring is broken, we've succeeded iff there are exactly enough broken or unknown
                // springs to satisfy the constraint, terminated with an intact or unknown spring
                val slice = fullPermutation.slice(0..<nextConstraint)
                val terminator = fullPermutation[nextConstraint]
                if (!slice.contains('.') && (terminator == '.' || terminator == '?')) {
                    fullPermutation = fullPermutation.drop(nextConstraint + 1)
                    fullConstraints = fullConstraints.drop(1)
                } else {
                    return 0
                }
            }

            if (fullPermutation.startsWith('?')) {
                val broken = '#' + fullPermutation.slice(1..<fullPermutation.length)
                val intact = '.' + fullPermutation.slice(1..<fullPermutation.length)

                val brokenCount = checkPermutation(broken, fullConstraints)
                memo[Pair(broken, fullConstraints)] = brokenCount

                val intactCount = checkPermutation(intact, fullConstraints)
                memo[Pair(intact, fullConstraints)] = intactCount

                return brokenCount + intactCount
            }
        }
    }

    override fun part2(input: List<SpringRow>): Long {
        return input.sumOf {
            checkPermutation(it.first, it.second, true)
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