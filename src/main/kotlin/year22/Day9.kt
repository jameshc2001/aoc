package year22

import year22.Day9.Move.*
import kotlin.math.abs

class Day9 {
    enum class Move {
        RIGHT, LEFT, UP, DOWN,
        RIGHT_UP, RIGHT_DOWN, LEFT_UP, LEFT_DOWN,
        STAY;

        companion object {
            fun fromString(value: String) = when (value) {
                "R" -> RIGHT
                "L" -> LEFT
                "U" -> UP
                "D" -> DOWN
                else -> throw RuntimeException("No Move found for value $value")
            }
        }
    }

    data class Coordinate(val x: Int, val y: Int) {
        fun move(move: Move): Coordinate = when(move) {
            RIGHT -> Coordinate(x + 1, y)
            RIGHT_UP -> Coordinate(x + 1, y + 1)
            RIGHT_DOWN -> Coordinate(x + 1, y - 1)
            LEFT -> Coordinate(x - 1, y)
            LEFT_UP -> Coordinate(x - 1, y + 1)
            LEFT_DOWN -> Coordinate(x - 1, y - 1)
            UP -> Coordinate(x, y + 1)
            DOWN -> Coordinate(x, y - 1)
            STAY -> copy()
        }
    }

    companion object {
        fun parseInput(input: String): List<Move> = input
            .lines()
            .map { it.split(" ") }
            .flatMap { (moveString, amount) ->
                val move = Move.fromString(moveString)
                (0..<amount.toInt()).map { move }
            }

        fun getTailsMove(head: Coordinate, tail: Coordinate): Move {
            val distance = abs(head.x - tail.x) + abs(head.y - tail.y)

            val right = head.x > tail.x
            val left = head.x < tail.x
            val up = head.y > tail.y
            val down = head.y < tail.y

            if (distance >= 3) {
                if (right && up) return RIGHT_UP
                if (left && up) return LEFT_UP
                if (right && down) return RIGHT_DOWN
                if (left && down) return LEFT_DOWN
            } else if (distance == 2) {
                if (right && !up && !down) return RIGHT
                if (left && !up && !down) return LEFT
                if (up && !left && !right) return UP
                if (down && !left && !right) return DOWN
            }

            return STAY
        }

        fun uniqueTailPositions(knots: Int, input: String): Int {
            val headPositions = parseInput(input)
                .fold(listOf(Coordinate(0, 0))) { acc, move ->
                    acc + acc.last().move(move)
                }
            val tailPositions = getTailPositionsForKnots(knots, headPositions)
            return tailPositions.toSet().size
        }

        private tailrec fun getTailPositionsForKnots(knots: Int, headPositions: List<Coordinate>): List<Coordinate> =
            if (knots == 1) headPositions
            else getTailPositionsForKnots(knots - 1, getTailPositions(headPositions))

        private fun getTailPositions(headPositions: List<Coordinate>) = headPositions
            .fold(emptyList<Coordinate>()) { acc, coordinate ->
                val previousPosition = acc.lastOrNull() ?: Coordinate(0, 0)
                val move = getTailsMove(coordinate, previousPosition)
                acc + previousPosition.move(move)
            }
    }
}