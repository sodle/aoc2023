class Game(gameString: String) {
    val id = "Game (\\d+)".toRegex().find(gameString)?.destructured?.toList()?.first()?.toInt()!!
    val blue = "(\\d+) blue".toRegex().findAll(gameString).map {
        it.destructured.toList().first().toInt()
    }.toList()
    val green = "(\\d+) green".toRegex().findAll(gameString).map {
        it.destructured.toList().first().toInt()
    }
    val red = "(\\d+) red".toRegex().findAll(gameString).map {
        it.destructured.toList().first().toInt()
    }
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