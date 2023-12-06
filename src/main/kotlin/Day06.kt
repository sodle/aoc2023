typealias TimeCard = List<Pair<Long, Long>>

fun propelBoat(heldSeconds: Long, totalSeconds: Long): Long {
    val movingSeconds = totalSeconds - heldSeconds
    return heldSeconds * movingSeconds
}

class Day06: BasePuzzle<TimeCard, Int, Int>() {
    override fun getPuzzleInput(): TimeCard {
        val (timeLine, distanceLine) = readLines("day06.txt").filter { it.isNotBlank() }
        val times = timeLine.split("\\s+".toRegex()).filterNot { it.endsWith(':') }.map { it.toLong() }
        val distances = distanceLine.split("\\s+".toRegex()).filterNot { it.endsWith(':') }.map { it.toLong() }
        return times.zip(distances)
    }

    override fun part1(input: TimeCard): Int {
        return input.map { race ->
            val (totalSeconds, minimumDistance) = race
            (0..totalSeconds).map { propelBoat(it, totalSeconds) }.count { it > minimumDistance }
        }.reduce { acc, i -> acc * i }
    }

    override fun part2(input: TimeCard): Int {
        val time = input.joinToString("") { it.first.toString() }.toLong()
        val distance = input.joinToString("") { it.second.toString() }.toLong()
        return (0..time).map { propelBoat(it, time) }.count { it > distance }
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val dayClass = this::class.java.declaringClass.getDeclaredConstructor()
            val day = dayClass.newInstance() as BasePuzzle<*, *, *>
            day.main()
        }
    }
}