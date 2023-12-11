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
            val posToPipes = getPosToPipes(input)
            return StartAndMap(
                posToPipes.filterValues { pipe -> pipe == Start }.keys.single(),
                generateMap(posToPipes)
            )
        }

        private fun getPosToPipes(input: String) = input
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
            val path = getLoop(start, map)
            return path.size / 2L
        }

        private fun getLoop(start: Pos, map: Map<Pos, Set<Pos>>): MutableList<Pos> {
            val path = mutableListOf<Pos>()
            var currentPipe = start
            do {
                path.add(currentPipe)
                val neighbours = map[currentPipe]!!
                currentPipe = if (neighbours.first() in path) neighbours.last() else neighbours.first()
            } while (currentPipe != start)
            return path
        }

        private fun getCornerPipeForStart(start: Pos, map: Map<Pos, Set<Pos>>): Pipe {
            val neighbours = map[start]!!
            if (neighbours.contains(Pos(start.x - 1, start.y)) && neighbours.contains(Pos(start.x, start.y + 1))) return SouthWestBend
            if (neighbours.contains(Pos(start.x + 1, start.y)) && neighbours.contains(Pos(start.x, start.y + 1))) return SouthEastBend
            if (neighbours.contains(Pos(start.x - 1, start.y)) && neighbours.contains(Pos(start.x, start.y - 1))) return NorthWestBend
            if (neighbours.contains(Pos(start.x + 1, start.y)) && neighbours.contains(Pos(start.x, start.y - 1))) return NorthEastBend

            throw RuntimeException("could not determine corner pipe for start")
        }

        fun enclosedArea(input: String): Int {
            val (start, map) = parseInput(input)
            val loop = getLoop(start, map)

            val posToPipes = getPosToPipes(input).toMutableMap()
            posToPipes[start] = getCornerPipeForStart(start, map)

            val min = Pos(loop.minOf { it.x - 1 }, loop.minOf { it.y - 1 })
            val max = Pos(loop.maxOf { it.x + 1 }, loop.maxOf { it.y + 1 })

            val area = (min.x .. max.x).flatMap { x ->
                (min.y .. max.y).map { y ->
                    Pos(x, y)
                }
            }.filter { insideLoop(it, loop.toSet(), posToPipes, max.x) }
            return area.size
        }

        private fun edgeClosesInSameDirectionAsItOpens(firstEdgePipe: Pipe, lastEdgePipe: Pipe): Boolean {
            if (firstEdgePipe == NorthEastBend && lastEdgePipe == NorthWestBend) return true
            if (firstEdgePipe == NorthWestBend && lastEdgePipe == NorthEastBend) return true
            if (firstEdgePipe == SouthEastBend && lastEdgePipe == SouthWestBend) return true
            if (firstEdgePipe == SouthWestBend && lastEdgePipe == SouthEastBend) return true
            return false
        }

        private fun insideLoop(pos: Pos, loop: Set<Pos>, posToPipes: Map<Pos, Pipe>, maxX: Long): Boolean {
            if (pos in loop) return false

            val cornerPipes = setOf(NorthEastBend, NorthWestBend, SouthEastBend, SouthWestBend)
            var onEdge = false
            var firstEdgePipe: Pipe? = null
            var currentPos = pos
            var counter = 0

            do {
                currentPos = Pos(currentPos.x + 1, currentPos.y)
                val currentPipe = posToPipes[currentPos]

                if (onEdge) {
                    if (currentPipe in cornerPipes) {
                        onEdge = false
                        if (edgeClosesInSameDirectionAsItOpens(firstEdgePipe!!, currentPipe!!)) counter++
                    }
                } else {
                    if (currentPos in loop) {
                        onEdge = currentPipe in cornerPipes
                        firstEdgePipe = currentPipe
                        counter++
                    }
                }

            } while (currentPos.x <= maxX)

            return (counter % 2 != 0) //odd counter means point is inside
        }
    }
}