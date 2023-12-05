import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class TestDay05 {
    private val puzzle = Day05()

    @Test
    fun getPuzzleInput() {
        val actual = puzzle.getPuzzleInput()
        val expectedSeeds = listOf(79L, 14L, 55L, 13L)
        assertEquals(expectedSeeds, actual.seeds)

        val expectedSeedsToSoil = 48L
        val actualSeedToSoil = actual.seedToSoilMap[(98L..<100L)]
        assertEquals(expectedSeedsToSoil, actualSeedToSoil)
    }

    @Test
    fun part1() {
        val actual = puzzle.part1()
        val expected = 35L
        assertEquals(expected, actual)
    }

    @Test
    fun part2() {
        val actual = puzzle.part2()
        val expected = 46L
        assertEquals(expected, actual)
    }
}