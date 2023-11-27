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
    }

    data class Pos(val x: Int, val y: Int)
    data class RockShape(val coordinates: Set<Pos>) {
        fun withOffset(offset: Pos) = coordinates.map { Pos(it.x + offset.x, it.y + offset.y) }
    }

    enum class Direction {
        LEFT, RIGHT;

        companion object {
            fun fromChar(char: Char): Direction = when(char) {
                '<' -> LEFT
                '>' -> RIGHT
                else -> throw RuntimeException("Unexpected direction")
            }
        }
    }

    companion object {
        private val rockShapes = CircularList(
            listOf(
                RockShape(setOf(Pos(0, 0), Pos(1, 0), Pos(2, 0), Pos(3, 0))), //-
                RockShape(setOf(Pos(1, 0), Pos(0, 1), Pos(1, 1), Pos(2, 1), Pos(1, 2))), //+
                RockShape(setOf(Pos(0, 0), Pos(1, 0), Pos(2, 0), Pos(2, 1), Pos(2, 2))), //reverse L
                RockShape(setOf(Pos(0, 0), Pos(0, 1), Pos(0, 2), Pos(0, 3))), //|
                RockShape(setOf(Pos(0, 0), Pos(1, 0), Pos(0, 1), Pos(1, 1))) //square
            ) //define with bottom left as 0, 0
        )

        fun parseInput(input: String) = CircularList(
            input.replace("\n", "")
                .replace("\r", "")
                .map { Direction.fromChar(it) }
        )

        //for part 2 change this function to work by only keeping track of top level coordinates. Also switch to using Long in Pos
        fun heightAfterRocksFall(input: String, rocks: Int): Int {
            val jetPattern = parseInput(input)

            var highestY = 0
            val coordinates = mutableSetOf<Pos>()

            repeat((0 ..< rocks).count()) {
                val rockShape = rockShapes.next()
                var position = Pos(2, highestY + 3)
                var fall = false
                var landed = false

                do {
//                    draw(coordinates.plus(rockShape.withOffset(position)))

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

        private fun getCollision(
            rockShape: RockShape,
            nextPos: Pos,
            coordinates: MutableSet<Pos>
        ): Boolean {
            val rockCoordinates = rockShape.withOffset(nextPos)
            return rockCoordinates.any { it in coordinates }
                    || rockCoordinates.minOf { it.y } < 0
                    || rockCoordinates.any { it.x !in (0..6) }
        }

        private fun draw(coordinates: Set<Pos>) {
            (0 .. coordinates.maxOf { it.y }).reversed().forEach { y ->
                (-1 .. 7).forEach { x ->
                    val pos = Pos(x, y)
                    if (pos in coordinates) print('#')
                    else if (pos.x == -1 || pos.x == 7) print('|')
                    else print('.')
                }
                println()
            }
            print('+')
            repeat((0..6).count()) { print('-') }
            print('+')
            println()
            println()
            println()
        }
    }
}