import java.net.URL

class GridShapeException(override val message: String?): IllegalArgumentException()
class GridIndexException(override val message: String?): IndexOutOfBoundsException()

typealias GridCoordinate = Pair<Int, Int>

public class Grid<T>(private val contents: MutableList<T>, val height: Int, val width: Int) {
    private fun index(row: Int, col: Int) = row * width + col
    private fun index(coordinate: GridCoordinate) = index(coordinate.first, coordinate.second)

    operator fun get(row: Int, col: Int): T {
        return contents[index(row, col)]
    }
    operator fun get(coordinate: GridCoordinate): T {
        val (row, col) = coordinate
        return get(row, col)
    }
    operator fun get(coordinates: List<GridCoordinate>): MutableList<T> {
        return coordinates.map { get(it) }.toMutableList()
    }
    operator fun get(vararg coordinates: GridCoordinate): MutableList<T> {
        return coordinates.map { get(it) }.toMutableList()
    }

    operator fun set(row: Int, col: Int, value: T) {
        contents[index(row, col)] = value
    }
    operator fun set(coordinate: GridCoordinate, value: T) {
        val (row, col) = coordinate
        set(row, col, value)
    }
    operator fun set(coordinates: List<GridCoordinate>, values: List<T>) {
        coordinates.forEachIndexed { index, pair -> contents[index(pair)] = values[index] }
    }
    operator fun set(vararg coordinates: GridCoordinate, values: List<T>) {
        coordinates.forEachIndexed { index, pair -> contents[index(pair)] = values[index] }
    }

    fun columnSlice(col: Int, reverse: Boolean = false): List<GridCoordinate> = if (col in 0..<width) {
        (0..<height).let { if (reverse) it.reversed() else it }.map { GridCoordinate(it, col) }
    } else {
        throw GridIndexException("Column index $col out of range for grid with width $width")
    }
    fun rowSlice(row: Int, reverse: Boolean = false): List<GridCoordinate> = if (row in 0..<height) {
        (0..<width).let { if (reverse) it.reversed() else it }.map { GridCoordinate(row, it) }
    } else {
        throw GridIndexException("Row index $row out of range for grid with height $height")
    }

    fun flatten() = contents.toList()

    companion object {
        fun charFromLines(lines: List<String>): Grid<Char> {
            val filteredLines = lines.filter { it.isNotBlank() }
            if (filteredLines.isEmpty())
                throw GridShapeException("This grid has no contents")
            val height = filteredLines.size

            val lineLengths = filteredLines.map { it.length }.toSet()
            if (lineLengths.size != 1)
                throw GridShapeException("The rows of this grid have inconsistent lengths: $lineLengths")
            val width = lineLengths.first()

            val contents = filteredLines.flatMap { it.toList() }.toMutableList()

            return Grid(contents, height, width)
        }

        fun charFromFile(url: URL): Grid<Char> {
            return charFromLines(url.readText().lines())
        }
    }
}
