package year22

import kotlin.math.max

class Day17 {

    class CircularList<out T>(private val list: List<T>) {
        init { require(list.isNotEmpty()) }

        private var iterator = 0
        fun next(): T {
            val element = list[iterator]
            iterator = (iterator + 1) % list.size
            return element
        }
    }

    data class Pos(val x: Long, val y: Long)

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
            val coordinates = mutableSetOf<Pos>()

            (0L..<rocks).forEach { _ ->
                highestY = heightAfterDroppingARock(highestY, jetPattern, rockShapesCircularList.next(), coordinates)
            }

            return highestY
        }

        private fun heightAfterDroppingARock(
            highestY: Long,
            jetPattern: CircularList<Direction>,
            rockShape: RockShape,
            coordinates: MutableSet<Pos>
        ) : Long {
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
            coordinates.addAll(rockShapeWithOffset)
            return max(highestY, rockShapeWithOffset.maxOf { it.y } + 1) //+1 accounts for height of single coordinate
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

        data class RockFallCycleData(
            val initialHeightDeltas: List<Long>,
            val cycleHeightDeltas: List<Long>
        )

        fun findRockFallCycle(jetPattern: CircularList<Direction>): RockFallCycleData {
            val rockShapesCircularList = CircularList(rockShapes)
            val heightDeltas = mutableListOf<Long>()
            val coordinates = mutableSetOf<Pos>()

            var prevHeight = 0L
            var cycleData : CycleData?

            do {
                val newHeight = heightAfterDroppingARock(prevHeight, jetPattern, rockShapesCircularList.next(), coordinates)
                heightDeltas.add(newHeight - prevHeight)
                prevHeight = newHeight
                cycleData = findCycle(heightDeltas)
            } while (cycleData == null)

            return RockFallCycleData(
                heightDeltas.take(cycleData.start),
                heightDeltas.subList(cycleData.start, cycleData.start + cycleData.size)
            )
        }

        data class CycleData(
            val start: Int, //first element in cycle
            val size: Int
        )

        private fun findCycle(data: List<Long>) : CycleData? {
            (0 ..< data.size - 1).forEach { start ->
                val subSection = data.drop(start)
                if (subSection.size % 2 == 0) {
                    val size = subSection.size / 2
                    val left = subSection.subList(0, size)
                    val right = subSection.subList(size, subSection.size)
                    if (size > 5 && left == right) return CycleData(start, size)
                }
            }
            return null
        }

        fun heightAfterRocksFallUsingCycle(input: String, rocks: Long): Long {
            val jetPattern = parseInput(input)
            val cycleData = findRockFallCycle(jetPattern)

            val initialHeight = cycleData.initialHeightDeltas.sum()
            val initialRocks = cycleData.initialHeightDeltas.size
            val cycleHeight = cycleData.cycleHeightDeltas.sum()
            val cycleRocks = cycleData.cycleHeightDeltas.size

            val cyclesToAdd = (rocks - initialRocks) / cycleRocks //implicitly round down
            val extraRocksToAdd = (rocks - initialRocks) % cycleRocks

            val heightFromCycles = cyclesToAdd * cycleHeight
            val heightFromExtraRocks = cycleData.cycleHeightDeltas.take(extraRocksToAdd.toInt()).sum()

            return initialHeight + heightFromCycles + heightFromExtraRocks
        }
    }
}