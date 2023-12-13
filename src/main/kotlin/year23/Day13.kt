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

        fun findSymmetry(lines: List<List<Tile>>): Int { //lines can be either rows or columns, it does not matter
            lines.indices.drop(1).forEach { index ->
                val range = min(index, lines.size - index)
                val left = lines.subList(index - range, index)
                val right = lines.subList(index, index + range)
                if (left.reversed() == right) return index
            }
            return -1 //no symmetry found
        }

        fun summary(input: String): Int {
            return parseInput(input).sumOf { pattern ->
                val columnSymmetry = findSymmetry(pattern.columns)
                val rowSymmetry = findSymmetry(pattern.rows)
                if (columnSymmetry != -1) columnSymmetry
                else rowSymmetry * 100
            }
        }
    }
}