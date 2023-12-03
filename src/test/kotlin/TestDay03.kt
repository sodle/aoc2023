import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class TestDay03 {
    private val puzzle = Day03()

    @Test
    fun testGetInput() {
        val input = puzzle.getPuzzleInput()

        val expectedNumbers = listOf(467, 114, 35, 633, 617, 58, 592, 755, 664, 598)
        val actualNumbers = input.numbers.map { it.n }
        assertEquals(expectedNumbers, actualNumbers)

        val expectedSymbols = listOf('*', '#', '*', '+', '$', '*')
        val actualSymbols = input.symbols.values.toList()
        assertEquals(expectedSymbols, actualSymbols)
    }

    @Test
    fun testPart1() {
        val expected = 4361
        val actual = puzzle.part1()
        assertEquals(expected, actual)
    }
    @Test
    fun testPart2() {
        val expected = 467835
        val actual = puzzle.part2()
        assertEquals(expected, actual)
    }
}
