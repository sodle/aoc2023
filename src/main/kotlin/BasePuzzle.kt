open class BasePuzzle<InputType, OutputTypePart1, OutputTypePart2> {
    fun readFile(filename: String): String {
        return this::class.java.getResource(filename)!!.readText()
    }

    open fun getPuzzleInput(): InputType {
        TODO("Not yet implemented")
    }

    open fun part1(input: InputType): OutputTypePart1 {
        TODO("Not yet implemented")
    }
    fun part1(): OutputTypePart1 {
        return part1(getPuzzleInput())
    }

    open fun part2(input: InputType): OutputTypePart2 {
        TODO("Not yet implemented")
    }
    fun part2(): OutputTypePart2 {
        return part2(getPuzzleInput())
    }

    fun main() {
        val input = this.getPuzzleInput()
        val dayPadded = this::class.simpleName
        println("################################")
        println("$dayPadded Part 1:\t${this.part1(input)}")
        println("$dayPadded Part 2:\t${this.part2(input)}")
        println("################################")
    }
}