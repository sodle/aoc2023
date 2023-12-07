import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class TestDay07 {
    private val puzzle = Day07()

    @Test
    fun getPuzzleInput() {
        val expected = 19
        val actual = puzzle.getPuzzleInput().count()
        assertEquals(expected, actual)
    }

    @Test
    fun part1() {
        val expected = 6592
        val actual = puzzle.part1()
        assertEquals(expected, actual)
    }

    @Test
    fun part2() {
        val expected = 6839
        val actual = puzzle.part2()
        assertEquals(expected, actual)
    }
}