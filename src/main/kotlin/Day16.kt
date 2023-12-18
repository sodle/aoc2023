class Day16 : BasePuzzle<Grid<Char>, Long, Long>() {
    override fun getPuzzleInput(): Grid<Char> {
        return Grid.charFromFile(this::class.java.getResource("day16.txt")!!)
    }

    override fun part1(input: Grid<Char>): Long {
        return countEnergized(input, Pair(Pair(0, 0), 'R'))
    }

    private fun countEnergized(input: Grid<Char>, start: Pair<Pair<Int, Int>, Char>): Long {
        var beamTips = setOf(start)
        val energizedTiles = mutableSetOf(Pair(0, 0))
        val splitsTaken = mutableSetOf<Pair<Int, Int>>()

        while (beamTips.isNotEmpty()) {
            beamTips = beamTips.flatMap {
                val (coordinate, trajectory) = it
                val (row, col) = coordinate

                if (col < 0 || col >= input.width)
                    setOf()
                else if (row < 0 || row >= input.height)
                    setOf()
                else {
                    if (it != start)
                        energizedTiles.add(coordinate)

                    val currentStep = input[row, col]
                    when (currentStep) {
                        '.' -> when (trajectory) {
                            'R' -> setOf(Pair(Pair(row, col + 1), 'R'))
                            'L' -> setOf(Pair(Pair(row, col - 1), 'L'))
                            'U' -> setOf(Pair(Pair(row - 1, col), 'U'))
                            'D' -> setOf(Pair(Pair(row + 1, col), 'D'))
                            else -> setOf()
                        }

                        '/' -> when (trajectory) {
                            'R' -> setOf(Pair(Pair(row - 1, col), 'U'))
                            'L' -> setOf(Pair(Pair(row + 1, col), 'D'))
                            'U' -> setOf(Pair(Pair(row, col + 1), 'R'))
                            'D' -> setOf(Pair(Pair(row, col - 1), 'L'))
                            else -> setOf()
                        }

                        '\\' -> when (trajectory) {
                            'R' -> setOf(Pair(Pair(row + 1, col), 'D'))
                            'L' -> setOf(Pair(Pair(row - 1, col), 'U'))
                            'U' -> setOf(Pair(Pair(row, col - 1), 'L'))
                            'D' -> setOf(Pair(Pair(row, col + 1), 'R'))
                            else -> setOf()
                        }

                        '-' -> when (trajectory) {
                            'R' -> setOf(Pair(Pair(row, col + 1), 'R'))
                            'L' -> setOf(Pair(Pair(row, col - 1), 'L'))
                            else -> if (splitsTaken.contains(coordinate)) setOf() else {
                                splitsTaken.add(coordinate)
                                setOf(
                                    // U or D
                                    Pair(Pair(row, col - 1), 'L'),
                                    Pair(Pair(row, col + 1), 'R'),
                                )
                            }
                        }

                        '|' -> when (trajectory) {
                            'U' -> setOf(Pair(Pair(row - 1, col), 'U'))
                            'D' -> setOf(Pair(Pair(row + 1, col), 'D'))
                            else -> if (splitsTaken.contains(coordinate)) setOf() else {
                                splitsTaken.add(coordinate)
                                setOf(
                                    // L or R
                                    Pair(Pair(row - 1, col), 'U'),
                                    Pair(Pair(row + 1, col), 'D'),
                                )
                            }
                        }

                        else -> setOf()
                    }
                }
            }.toSet()
        }

        return energizedTiles.size.toLong()
    }

    override fun part2(input: Grid<Char>): Long {
        return listOf(
            input.columnSlice(0).maxOf {
                countEnergized(input, Pair(it, 'R'))
            },
            input.columnSlice(input.width - 1).maxOf {
                countEnergized(input, Pair(it, 'L'))
            },
            input.rowSlice(0).maxOf {
                countEnergized(input, Pair(it, 'D'))
            },
            input.rowSlice(input.height - 1).maxOf {
                countEnergized(input, Pair(it, 'U'))
            }
        ).max()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val dayClass = this::class.java.declaringClass.getDeclaredConstructor()
            val day = dayClass.newInstance() as BasePuzzle<*, *, *>
            day.main()
        }
    }
}