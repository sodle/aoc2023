import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class TestDay06 {
    private val puzzle = Day06()

    @Test
    fun getPuzzleInput() {
        val expected = listOf(Pair(7L, 9L), Pair(15L, 40L), Pair(30L, 200L))
        val actual = puzzle.getPuzzleInput()
        assertEquals(expected, actual)
    }

    @Test
    fun part1() {
        val expected = 288
        val actual = puzzle.part1()
        assertEquals(expected, actual)
    }

    @Test
    fun part2() {
        val expected = 71503
        val actual = puzzle.part2()
        assertEquals(expected, actual)
    }
}