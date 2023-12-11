package year23

import year23.Day10.Direction.*
import year23.Day10.Pipe.*

class Day10 {
    data class Pos(val x: Long, val y: Long) {
        fun east() = Pos(x + 1, y)
        fun west() = Pos(x - 1, y)
        fun north() = Pos(x, y - 1) //may seem wrong way around but 0, 0 is at top left
        fun south() = Pos(x, y + 1)
    }

    enum class Pipe {
        Start, Vertical, Horizontal, NorthEastBend, NorthWestBend, SouthEastBend, SouthWestBend;

        companion object {
            fun fromChar(c: Char) = when (c) {
                'S' -> Start
                '|' -> Vertical
                '-' -> Horizontal
                'L' -> NorthEastBend
                'J' -> NorthWestBend
                'F' -> SouthEastBend
                '7' -> SouthWestBend
                else -> null
            }
        }
    }

    enum class Direction {
        North, East, South, West
    }

    data class StartAndMap(
        val start: Pos,
        val map: Map<Pos, Set<Pos>>
    )

    companion object {
        fun parseInput(input: String): StartAndMap {
            val posToPipes = input
                .lines()
                .map { it.replace("\n", "") }
                .map { it.replace("\r", "") }
                .flatMapIndexed { y: Int, line: String ->
                    line.mapIndexedNotNull { x, c ->
                        Pipe.fromChar(c)?.let { pipe ->
                            Pos(x.toLong(), y.toLong()) to pipe
                        }
                    }
                }
                .toMap()
            return StartAndMap(
                posToPipes.filterValues { pipe -> pipe == Start }.keys.single(),
                generateMap(posToPipes)
            )
        }

        private fun generateMap(posToPipes: Map<Pos, Pipe>): Map<Pos, Set<Pos>> {
            return posToPipes.mapValues { (pos, pipe) ->
                val posDirections = pipe.directionsForPipe()
                pos.neighbouringPosForPipe(pipe)
                    .filter { it in posToPipes.keys }
                    .filter { neighbourPos ->
                        val neighbourPipe = posToPipes[neighbourPos]!!
                        val neighbourDirections = neighbourPipe.directionsForPipe()
                        pipesConnect(pos, posDirections, neighbourPos, neighbourDirections)
                    }.toSet()
            }
        }

        private fun Pos.neighbouringPosForPipe(pipe: Pipe) = when(pipe) {
            Start -> listOf(north(), south(), east(), west())
            Vertical -> listOf(north(), south())
            Horizontal -> listOf(east(), west())
            NorthEastBend -> listOf(north(), east())
            NorthWestBend -> listOf(north(), west())
            SouthEastBend -> listOf(south(), east())
            SouthWestBend -> listOf(south(), west())
        }

        private fun Pipe.directionsForPipe() = when (this) {
            Start -> setOf(North, East, South, West)
            Vertical -> setOf(North, South)
            Horizontal -> setOf(East, West)
            NorthEastBend -> setOf(North, East)
            NorthWestBend -> setOf(North, West)
            SouthEastBend -> setOf(South, East)
            SouthWestBend -> setOf(South, West)
        }

        private fun pipesConnect(pos: Pos, dirs: Set<Direction>, otherPos: Pos, otherDirs: Set<Direction>) : Boolean {
            return dirs.any { dir ->
                when (dir) {
                    North -> (otherPos.y == pos.y - 1 && otherDirs.contains(South))
                    East -> (otherPos.x == pos.x + 1 && otherDirs.contains(West))
                    South -> (otherPos.y == pos.y + 1 && otherDirs.contains(North))
                    West -> (otherPos.x == pos.x - 1 && otherDirs.contains(East))
                }
            }
        }

        fun stepsToFurthestPoint(input: String): Long {
            val (start, map) = parseInput(input)
            val path = mutableListOf<Pos>()
            var currentPipe = start
            do {
                path.add(currentPipe)
                val neighbours = map[currentPipe]!!
                currentPipe = if (neighbours.first() in path) neighbours.last() else neighbours.first()
            } while (currentPipe != start)
            return path.size / 2L
        }
    }
}