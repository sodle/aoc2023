private typealias InputType = Pair<String, NodeMap>
private typealias OutputType1 = Int
private typealias OutputType2 = Long

private typealias NodeMap = Map<String, Pair<String, String>>

// many thanks to the person i stole this math from https://stackoverflow.com/a/2641293/2395374
fun gcd(n1: Long, n2: Long): Long {
    var a = n1
    var b = n2
    while (b != 0L) {
        val temp = b
        b = a % b
        a = temp
    }
    return a
}

fun lcm(a: Long, b: Long): Long {
    return (a * b / gcd(a, b))
}

class Day08: BasePuzzle<InputType, OutputType1, OutputType2>() {
    override fun getPuzzleInput(): InputType {
        val lines = readLines("day08.txt").filter { it.isNotBlank() }
        val directionLine = lines.first()
        val nodeMap = lines.drop(1).map {
            val (node, outputs) = it.split(" = ")
            val (left, right) = outputs.drop(1).dropLast(1).split(", ")
            Pair(node, Pair(left, right))
        }.toMap()
        return Pair(directionLine, nodeMap)
    }

    override fun part1(input: InputType): OutputType1 {
        val (directions, nodes) = input
        var steps = 0
        var currentNode = "AAA"

        while (currentNode != "ZZZ") {
            val idx = steps % directions.length
            val direction = directions[idx]
            currentNode = when (direction) {
                'L' -> nodes[currentNode]!!.first
                'R' -> nodes[currentNode]!!.second
                else -> currentNode
            }
            steps++
        }

        return steps
    }

    override fun part2(input: InputType): OutputType2 {
        val (directions, nodes) = input
        val startingNodes = nodes.keys.filter { it.endsWith('A') }

        return startingNodes.map {
            var currentNode = it
            var steps = 0L
            var idx = 0
            while (!currentNode.endsWith('Z')) {
                val direction = directions[idx]
                currentNode = when(direction) {
                    'L' -> nodes[currentNode]!!.first
                    'R' -> nodes[currentNode]!!.second
                    else -> currentNode
                }
                steps++
                idx = ++idx % directions.length
            }
            steps
        }.reduce { acc, i -> lcm(acc, i) }
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val dayClass = this::class.java.declaringClass.getDeclaredConstructor()
            val day = dayClass.newInstance() as BasePuzzle<*, *, *>
            day.main()
        }
    }
}