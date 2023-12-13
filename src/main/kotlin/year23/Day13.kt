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

    data class Pattern(val grid: Map<Pos, Tile>) {
        val rows = grid.entries
            .groupBy { it.key.y }
            .map { (_, posToTile) -> posToTile.map { it.value } }

        val columns = grid.entries
            .groupBy { it.key.x }
            .map { (_, posToTile) -> posToTile.map { it.value } }
    }

    companion object {
        fun parseInput(input: String): List<Pattern> {
            val patterns = input.replace("\r", "").split("\n\n")
            return patterns.map { patternText ->
                patternText
                    .lines()
                    .map { it.replace("\n", "") }
                    .flatMapIndexed { y: Int, line: String ->
                        line.mapIndexed { x, c ->
                            Pos(x, y) to Tile.fromChar(c)
                        }
                    }
            }.map { Pattern(it.toMap()) }
        }

        fun lineOfSymmetry(lines: List<List<Tile>>): Int? { //lines can be either rows or columns, it does not matter
            return lines.indices.drop(1).find { index ->
                val range = min(index, lines.size - index)
                val left = lines.subList(index - range, index)
                val right = lines.subList(index, index + range)
                left.reversed() == right
            }
        }

        private fun lineOfSymmetryWithSmudgeRemoval(lines: List<List<Tile>>): Int? {
            return lines.indices.drop(1).find { index ->
                val range = min(index, lines.size - index)
                val left = lines.subList(index - range, index)
                val right = lines.subList(index, index + range)

                val differentLine = left.reversed().zip(right).singleOrNull { (l, r) -> l != r }
                if (differentLine != null) {
                    val differentTiles = differentLine.first.zip(differentLine.second).count { (l, r) -> l != r }
                    differentTiles == 1
                } else false
            }
        }

        fun summary(input: String) = parseInput(input).sumOf { pattern ->
            val columnSymmetry = lineOfSymmetry(pattern.columns) ?: 0
            val rowSymmetry = lineOfSymmetry(pattern.rows) ?: 0
            columnSymmetry + (rowSymmetry * 100)
        }

        fun summaryWithSmudgeRemoval(input: String) = parseInput(input).sumOf { pattern ->
            val columnSymmetry = lineOfSymmetryWithSmudgeRemoval(pattern.columns) ?: 0
            val rowSymmetry = lineOfSymmetryWithSmudgeRemoval(pattern.rows) ?: 0
            columnSymmetry + (rowSymmetry * 100)
        }
    }
}