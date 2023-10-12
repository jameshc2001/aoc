package year22

import kotlin.math.max
import kotlin.math.min

class Day14 {
    data class Coordinate(val x: Int, val y: Int) {
        fun down() = Coordinate(x, y + 1)
        fun downLeft() = Coordinate(x - 1, y + 1)
        fun downRight() = Coordinate(x + 1, y + 1)
    }

    companion object {
        fun parseLine(input: String): Set<Coordinate> {
            val regex = "[0-9]+,[0-9]+".toRegex()
            val keyCoordinates = regex.findAll(input).map { result ->
                val (x, y) = result.value.split(",").map { it.toInt() }
                Coordinate(x, y)
            }.toList()
            return keyCoordinates
                .drop(1)
                .flatMapIndexed { index, current ->
                    val previous = keyCoordinates[index]
                    getRocksConnecting(current, previous)
                }
                .toSet()
        }

        private fun getRocksConnecting(current: Coordinate, previous: Coordinate) = when {
            current.x == previous.x -> { //vertical line
                val start = min(current.y, previous.y)
                val end = max(current.y, previous.y)
                (start..end).map { y -> Coordinate(current.x, y) }
            }
            current.y == previous.y -> { //horizontal line
                val start = min(current.x, previous.x)
                val end = max(current.x, previous.x)
                (start..end).map { x -> Coordinate(x, current.y) }
            }
            else -> throw RuntimeException("Diagonal line detected: $previous $current")
        }

        fun parseInput(input: String): Set<Coordinate> = input
            .replace("\r", "")
            .lines()
            .flatMap { parseLine(it) }
            .toSet()

        fun simulateOneSand(rocksAndSand: Set<Coordinate>, abyssStart: Int): Coordinate? {
            var currentPosition = Coordinate(500, 0)
            while (true) {
                currentPosition = when {
                    currentPosition.y == abyssStart -> return null
                    currentPosition.down() !in rocksAndSand -> currentPosition.down()
                    currentPosition.downLeft() !in rocksAndSand -> currentPosition.downLeft()
                    currentPosition.downRight() !in rocksAndSand -> currentPosition.downRight()
                    else -> return currentPosition
                }
            }
        }
    }
}