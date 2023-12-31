import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class TestDay02 {
    private val puzzle = Day02()

    @Test
    fun testGetInput() {
        val input = puzzle.getPuzzleInput()
        assertEquals(1, input.first().id)
        assertEquals(listOf(3, 6), input.first().blue)
        assertEquals(listOf(4, 1), input.first().red)
        assertEquals(listOf(2, 2), input.first().green)
    }

    @Test
    fun testPart1() {
        val actual = puzzle.part1()
        val expected = 8
        assertEquals(expected, actual)
    }

    @Test
    fun testPart2() {
        val actual = puzzle.part2()
        val expected = 2286
        assertEquals(expected, actual)
    }
}