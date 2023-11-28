package year22

import kotlin.math.max

class Day17 {

    class CircularList<out T>(private val list: List<T>) {
        init {
            require(list.isNotEmpty())
        }

        private var iterator = 0

        fun next(): T {
            val element = list[iterator]
            iterator = (iterator + 1) % list.size
            return element
        }

        fun skip(n: Int) {
            require(n > 0)
            iterator = (iterator + n) % list.size
        }

        fun peek() = list[iterator]
    }

    data class Pos(val x: Long, val y: Long) {
        fun surroundingCoordinates() =
            listOf(Pos(-1, 0), Pos(0, 1), Pos(1, 0), Pos(0, -1))
                .map { Pos(x + it.x, y + it.y) }
    }
    data class RockShape(val coordinates: Set<Pos>) {
        fun withOffset(offset: Pos) = coordinates.map { Pos(it.x + offset.x, it.y + offset.y) }
    }

    enum class Direction {
        LEFT, RIGHT;

        companion object {
            fun fromChar(char: Char): Direction = when (char) {
                '<' -> LEFT
                '>' -> RIGHT
                else -> throw RuntimeException("Unexpected direction")
            }
        }
    }

    companion object {
        private val rockShapes = listOf(
            RockShape(setOf(Pos(0, 0), Pos(1, 0), Pos(2, 0), Pos(3, 0))), //-
            RockShape(setOf(Pos(1, 0), Pos(0, 1), Pos(1, 1), Pos(2, 1), Pos(1, 2))), //+
            RockShape(setOf(Pos(0, 0), Pos(1, 0), Pos(2, 0), Pos(2, 1), Pos(2, 2))), //reverse L
            RockShape(setOf(Pos(0, 0), Pos(0, 1), Pos(0, 2), Pos(0, 3))), //|
            RockShape(setOf(Pos(0, 0), Pos(1, 0), Pos(0, 1), Pos(1, 1))) //square
        ) //define with bottom left as 0, 0

        fun parseInput(input: String) = CircularList(
            input.replace("\n", "")
                .replace("\r", "")
                .map { Direction.fromChar(it) }
        )

        fun heightAfterRocksFall(input: String, rocks: Long): Long {
            val jetPattern = parseInput(input)
            val rockShapesCircularList = CircularList(rockShapes)

            var highestY = 0L
            var coordinates = mutableSetOf<Pos>()

            (0L..<rocks).forEach { _ ->
                coordinates = optimise(coordinates).toMutableSet()
                val rockShape = rockShapesCircularList.next()
                var position = Pos(2, highestY + 3)
                var fall = false
                var landed = false

                do {
                    val nextPos = if (fall) position.copy(y = position.y - 1) else {
                        when (jetPattern.next()) {
                            Direction.LEFT -> position.copy(x = position.x - 1)
                            Direction.RIGHT -> position.copy(x = position.x + 1)
                        }
                    }

                    val collision = getCollision(rockShape, nextPos, coordinates)
                    if (collision && fall) landed = true
                    else if (!collision) position = nextPos

                    fall = !fall
                } while (!landed)

                val rockShapeWithOffset = rockShape.withOffset(position)
                highestY = max(highestY, rockShapeWithOffset.maxOf { it.y } + 1) //+1 accounts for height of single coordinate
                coordinates.addAll(rockShapeWithOffset)
            }

            return highestY
        }

        private fun optimise(oldCoordinates: Set<Pos>): Set<Pos> {
            if (oldCoordinates.groupBy { it.x }.keys.size < 7) return oldCoordinates //guarantees path from left to right

            val start = oldCoordinates.maxBy { it.y }.let { it.copy(y = it.y + 1) }
            val sandFill = explore(start, oldCoordinates, start.y + 2)

            return oldCoordinates.filter { pos ->
                pos.surroundingCoordinates().any { it in sandFill }
            }.toSet()
        }

        private fun explore(pos: Pos, map: Set<Pos>, maxY: Long): Set<Pos> {
            val discovered = mutableSetOf<Pos>()
            explore(pos, map, maxY, discovered)
            return discovered
        }

        private fun explore(pos: Pos, map: Set<Pos>, maxY: Long, discovered: MutableSet<Pos>) {
            if (pos.y > maxY || pos.x !in (0L..6L) || pos in map || pos in discovered) return
            discovered.add(pos)
            pos.surroundingCoordinates().forEach { explore(it, map, maxY, discovered) }
        }

        private fun getCollision(
            rockShape: RockShape,
            nextPos: Pos,
            coordinates: Set<Pos>
        ): Boolean {
            val rockCoordinates = rockShape.withOffset(nextPos)
            return rockCoordinates.any { it in coordinates }
                    || rockCoordinates.minOf { it.y } < 0
                    || rockCoordinates.any { it.x !in (0..6) }
        }

        private fun draw(coordinates: Set<Pos>): String {
            var printout = ""
            (0..coordinates.maxOf { it.y }).reversed().forEach { y ->
                (-1L..7).forEach { x ->
                    val pos = Pos(x, y)
                    printout = if (pos in coordinates) printout.plus('#')
                    else if (pos.x == -1L || pos.x == 7L) printout.plus('|')
                    else printout.plus('.')
                }
                printout += "\n"
            }
            printout = printout.plus('+')
            repeat((0..6).count()) { printout = printout.plus('-') }
            printout = printout.plus('+')
            printout = printout.plus("\n")
            printout = printout.plus("\n")
            printout = printout.plus("\n")
            return printout
        }
    }
}