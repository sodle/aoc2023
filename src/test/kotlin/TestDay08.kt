import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class TestDay08 {
    private val puzzle = Day08()

    @Test
    fun getPuzzleInput() {
        val actualInput = puzzle.getPuzzleInput()

        val expectedDirections = "RL"
        assertEquals(expectedDirections, actualInput.first)

        val expectedFirstChildren = Pair("BBB", "CCC")
        assertEquals(expectedFirstChildren, actualInput.second["AAA"])
    }

    @Test
    fun part1() {
        val expected = 2
        val actual = puzzle.part1()
        assertEquals(expected, actual)
    }

    @Test
    fun part2() {
        val input = Pair("LR", mapOf(
            Pair("11A", Pair("11B", "XXX")),
            Pair("11B", Pair("XXX", "11Z")),
            Pair("11Z", Pair("11B", "XXX")),
            Pair("22A", Pair("22B", "XXX")),
            Pair("22B", Pair("22C", "22C")),
            Pair("22C", Pair("22Z", "22Z")),
            Pair("22Z", Pair("22B", "22B")),
            Pair("XXX", Pair("XXX", "XXX")),
        ))

        val expected = 6L
        val actual = puzzle.part2(input)
        assertEquals(expected, actual)
    }
}