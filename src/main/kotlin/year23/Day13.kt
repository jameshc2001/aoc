package year23

import kotlin.math.min

class Day13 {

    data class Pos(val x: Int, val y: Int)

    enum class Tile {
        Ash, Rock;

        companion object {
            fun fromChar(c: Char) = when (c) {
                '.' -> Ash
                '#' -> Rock
                else -> throw RuntimeException("no tile for character $c")
            }
        }
    }

    data class Pattern(val rows: List<List<Tile>>, val columns: List<List<Tile>>)

    companion object {
        fun parseInput(input: String): List<Pattern> {
            val patterns = input
                .split("\n\n")
                .let { if (it.size == 1) it.first().split("\r\n\r\n") else it }

            val grids = patterns.map { patternText ->
                patternText
                    .lines()
                    .map { it.replace("\n", "") }
                    .map { it.replace("\r", "") }
                    .flatMapIndexed { y: Int, line: String ->
                        line.mapIndexed { x, c ->
                            Pos(x, y) to Tile.fromChar(c)
                        }
                    }
            }

            return grids.map { grid ->
                val rows = grid
                    .groupBy { it.first.y }
                    .toSortedMap()
                    .map { (_, posToTile) ->
                        posToTile
                            .sortedBy { it.first.x }
                            .map { it.second }
                    }

                val columns = grid
                    .groupBy { it.first.x }
                    .toSortedMap()
                    .map { (_, posToTile) ->
                        posToTile
                            .sortedBy { it.first.y }
                            .map { it.second }
                    }

                Pattern(rows, columns)
            }
        }

        fun linesOfSymmetry(lines: List<List<Tile>>): List<Int> { //lines can be either rows or columns, it does not matter
            return lines.indices.drop(1).filter { index ->
                val range = min(index, lines.size - index)
                val left = lines.subList(index - range, index)
                val right = lines.subList(index, index + range)
                left.reversed() == right
            }
        }

        private fun summary(patterns: List<Pattern>) : Int {
            return patterns.sumOf { pattern ->
                val columnSymmetry = linesOfSymmetry(pattern.columns)
                val rowSymmetry = linesOfSymmetry(pattern.rows)
                columnSymmetry.sum() + rowSymmetry.sumOf { it * 100 }
            }
        }

        fun summary(input: String) = summary(parseInput(input))
    }
}