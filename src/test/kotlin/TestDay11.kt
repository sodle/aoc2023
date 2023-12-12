import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class TestDay11 {
    private val puzzle = Day11()

    @Test
    fun getPuzzleInput() {
        val expected = 9
        val actual = puzzle.getPuzzleInput().sumOf { it.count { c -> c == '#' } }
        assertEquals(expected, actual)
    }

    @Test
    fun part1() {
        val expected = 374
        val actual = puzzle.part1()
        assertEquals(expected, actual)
    }

    @Test
    fun part2() {
        val expected = 374
        val actual = puzzle.part1()
        assertEquals(expected, actual)
    }
}