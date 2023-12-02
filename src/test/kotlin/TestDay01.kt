import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class TestDay01 {
    private val puzzle = Day01()

    @Test
    fun testGetInput() {
        val input = puzzle.getPuzzleInput()
        val expected = listOf(
            "1abc2",
            "pqr3stu8vwx",
            "a1b2c3d4e5f",
            "treb7uchet",
        )
        assertLinesMatch(expected, input)
    }

    @Test
    fun testPart1() {
        val expected = 142
        val actual = puzzle.part1()
        assertEquals(expected, actual)
    }

    @Test
    fun testPart2() {
        val expected = 281
        val actual = puzzle.part2(listOf(
            "two1nine",
            "eightwothree",
            "abcone2threexyz",
            "xtwone3four",
            "4nineeightseven2",
            "zoneight234",
            "7pqrstsixteen",
        ))
        assertEquals(expected, actual)
    }
}