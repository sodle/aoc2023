import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class TestDay04 {
    private val puzzle = Day04()

    @Test
    fun testGetInput() {
        val input = puzzle.getPuzzleInput()
        val expectedSet = setOf(41, 48, 83, 86, 17)
        assertEquals(expectedSet, input.first().scratchedNumbers)
        assertEquals(1, input.first().index)
    }

    @Test
    fun testPart1() {
        val expected = 13
        val actual = puzzle.part1()
        assertEquals(expected, actual)
    }
    @Test
    fun testPart2() {
        val expected = 30
        val actual = puzzle.part2()
        assertEquals(expected, actual)
    }
}
