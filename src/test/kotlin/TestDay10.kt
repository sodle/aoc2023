import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class TestDay10 {
    private val puzzle = Day10()

    @Test
    fun getPuzzleInput() {
        val expected = listOf(
            "..F7.",
            ".FJ|.",
            "SJ.L7",
            "|F--J",
            "LJ..."
        )
        val actual = puzzle.getPuzzleInput()
        assertEquals(expected, actual)
    }

    @Test
    fun part1() {
        val expected = 80
        val actual = puzzle.part1()
        assertEquals(expected, actual)
    }

    @Test
    fun part2() {
        val expected = 10
        val actual = puzzle.part2()
        assertEquals(expected, actual)
    }
}