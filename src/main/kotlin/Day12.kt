internal typealias SpringRow = Pair<String, List<Int>>

class Day12: BasePuzzle<List<SpringRow>, Int, Int>() {
    override fun getPuzzleInput(): List<SpringRow> {
        return readLines("day12.txt").filter { it.isNotBlank() }.map {
            val (line, constraintString) = it.split(' ')
            val constraints = constraintString.split(',').map { c -> c.toInt() }
            SpringRow(line, constraints)
        }
    }

    private fun generatePermutations(row: String): List<String> {
        val permutations = mutableListOf("")

        row.forEach {
            if (it == '?') {
                val altPermutations = mutableListOf<String>()
                permutations.forEachIndexed { index, s ->
                    permutations[index] += "#"
                    altPermutations.add("$s.")
                }
                permutations.addAll(altPermutations)
            } else {
                permutations.forEachIndexed { index, _ -> permutations[index] += it.toString() }
            }
        }

        return permutations
    }

    private fun checkPermutation(permutation: String, constraints: List<Int>): Boolean {
        val pSplit = permutation.split("\\.+".toRegex()).map { it.length }.filter { it != 0 }
        return constraints == pSplit
    }

    private fun unfoldPermutation(permutation: String, constraints: List<Int>, stems: List<String>): Int {
        val permutations = generatePermutations(permutation).toMutableList()

        repeat(4) {
            val altPermutations = mutableListOf<String>()
            permutations.forEachIndexed { index, _ ->
                stems.forEach { intermediate ->
                    altPermutations.add(permutations[index] + "#$intermediate")
                    permutations[index] += ".$intermediate"
                }
            }
            permutations.addAll(altPermutations)
        }

        return permutations.count { checkPermutation(it, constraints) }
    }

    private fun unfoldConstraints(constraint: List<Int>): List<Int> {
        return constraint + constraint + constraint + constraint + constraint
    }

    override fun part1(input: List<SpringRow>): Int {
        return input.sumOf { line ->
            generatePermutations(line.first).count { checkPermutation(it, line.second) }
        }
    }

    override fun part2(input: List<SpringRow>): Int {
        return input.sumOf { line ->
            val constraints = unfoldConstraints(line.second)
            val permutations = generatePermutations(line.first)
            permutations.sumOf { unfoldPermutation(it, constraints, permutations) }
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