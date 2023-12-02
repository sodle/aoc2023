val gameIdRegex = "Game (\\d+)".toRegex()
val redRegex = "(\\d+) red".toRegex()
val greenRegex = "(\\d+) green".toRegex()
val blueRegex = "(\\d+) blue".toRegex()

class Game(gameString: String) {
    val id = gameIdRegex.find(gameString)!!.destructured.component1().toInt()

    val blue = blueRegex.findAll(gameString).map {
        it.destructured.component1().toInt()
    }.toList()
    val green = greenRegex.findAll(gameString).map {
        it.destructured.component1().toInt()
    }.toList()
    val red = redRegex.findAll(gameString).map {
        it.destructured.component1().toInt()
    }.toList()
}

class Day02: BasePuzzle<List<Game>, Int, Int>() {
    override fun getPuzzleInput(): List<Game> {
        return readLines("day02.txt").filter { it.isNotBlank() }.map { Game(it) }
    }

    override fun part1(input: List<Game>): Int {
        return input.filterNot { game ->
            game.red.any { it > 12 } || game.green.any { it > 13 } || game.blue.any { it > 14 }
        }.sumOf { it.id }
    }

    override fun part2(input: List<Game>): Int {
        return input.sumOf {
            it.red.max() * it.blue.max() * it.green.max()
        }
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val dayClass = this::class.java.declaringClass.getDeclaredConstructor()
            val day = dayClass.newInstance() as BasePuzzle<*, *, *>
            day.main()
        }
    }
}