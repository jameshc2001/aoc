package year23

import kotlin.math.abs

class Day11 {
    data class Pos(val x: Long, val y: Long) {
        fun distance(other: Pos) = abs(x - other.x) + abs(y - other.y)
    }

    companion object {
        fun parseInput(input: String): Set<Pos> {
            return input
                .lines()
                .map { it.replace("\n", "") }
                .map { it.replace("\r", "") }
                .flatMapIndexed { y, line ->
                    line.mapIndexed { x, c ->
                        if (c != '.') Pos(x.toLong(), y.toLong()) else null
                    }
                }.filterNotNull().toSet()
        }

        fun expandGalaxies(galaxies: Set<Pos>, expansionSize: Long): Set<Pos> {
            val emptyColumns = (0..galaxies.maxOf { it.x }).filterNot { x -> galaxies.any { it.x == x } }
            val emptyRows = (0..galaxies.maxOf { it.y }).filterNot { y -> galaxies.any { it.y == y } }

            return galaxies.map { pos ->
                val columnsBefore = emptyColumns.count { it < pos.x }
                val rowsBefore = emptyRows.count { it < pos.y }
                Pos( //minus 1 to account for original size of column or row
                    pos.x + (columnsBefore * (expansionSize - 1)),
                    pos.y + (rowsBefore * (expansionSize - 1))
                )
            }.toSet()
        }

        fun sumOfDistances(input: String, expansionSize: Long): Long {
            val galaxies = expandGalaxies(parseInput(input), expansionSize)

            val uniquePairs = galaxies
                .flatMap { a -> galaxies.map { b -> setOf(a, b) } }
                .filter { it.size == 2 }
                .toSet()
                .map { it.first() to it.last() }

            return uniquePairs.sumOf { (a, b) -> a.distance(b) }
        }
    }
}