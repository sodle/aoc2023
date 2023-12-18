class Day15: BasePuzzle<List<String>, Long, Long>() {
    override fun getPuzzleInput(): List<String> {
        return readLines("day15.txt").filter { it.isNotBlank() }.first().split(",")
    }

    override fun part1(input: List<String>): Long {
        return input.sumOf {
            hash(it)
        }.toLong()
    }

    private fun hash(it: String): Int {
        var hash = 0
        it.forEach { c ->
            hash += c.code
            hash *= 17
            hash %= 256
        }
        return hash
    }

    override fun part2(input: List<String>): Long {
        val boxes = (0..255).map { mutableListOf<Pair<String, Int>>() }.toMutableList()
        input.forEach {
            if (it.contains('=')) {
                val (label, focal) = it.split('=')
                val hash = hash(label)
                val lens = Pair(label, focal.toInt())

                val replaceIndex = boxes[hash].indexOfFirst { other -> label == other.first }
                if (replaceIndex > -1) {
                    boxes[hash][replaceIndex] = lens
                } else {
                    boxes[hash].add(lens)
                }
            }
            if (it.endsWith('-')) {
                val label = it.dropLast(1)
                val hash = hash(label)
                boxes[hash] = boxes[hash].filterNot { lens -> lens.first == label }.toMutableList()
            }
        }
        return boxes.mapIndexed { bid, box ->
            box.mapIndexed { slot, lens ->
                (bid+1) * (slot+1) * lens.second
            }.sum()
        }.sum().toLong()
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val dayClass = this::class.java.declaringClass.getDeclaredConstructor()
            val day = dayClass.newInstance() as BasePuzzle<*, *, *>
            day.main()
        }
    }
}