import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class TestDay09 {
    private val puzzle = Day09()

    @Test
    fun getPuzzleInput() {
        val expected = listOf(
            listOf(0, 3, 6, 9, 12, 15),
            listOf(1, 3, 6, 10, 15, 21),
            listOf(10, 13, 16, 21, 30, 45),
        )
        val actual = puzzle.getPuzzleInput()
        assertEquals(expected, actual)
    }

    @Test
    fun part1() {
        val expected = 114
        val actual = puzzle.part1()
        assertEquals(expected, actual)
    }

    @Test
    fun part2() {
        val expected = 2
        val actual = puzzle.part2()
        assertEquals(expected, actual)
    }
}