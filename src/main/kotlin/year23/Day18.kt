package year23

import kotlin.math.abs

class Day18 {

    data class Instruction(val direction: Char, val moves: Long, val colour: String)
    data class Pos(val x: Long, val y: Long) {
        operator fun plus(other: Pos) = Pos(x + other.x, y + other.y)
        operator fun times(factor: Long) = Pos(x * factor, y * factor)
        fun distance(other: Pos) = abs(x - other.x) + abs(y - other.y)
    }

    companion object {
        fun parseInput(input: String): List<Instruction> = input
            .lines().map { it.replace("\n", "").replace("\r", "") }
            .map { line ->
                val (direction, moves, colour) = line.split(' ')
                Instruction(direction.first(), moves.toLong(), colour.drop(2).dropLast(1))
            }

        fun verticesFromInstructions(instructions: List<Instruction>) =
            instructions.fold(listOf(Pos(0, 0))) { acc, instruction ->
                val direction = when (instruction.direction) {
                    'R' -> Pos(1, 0)
                    'L' -> Pos(-1, 0)
                    'U' -> Pos(0, 1)
                    'D' -> Pos(0, -1)
                    else -> throw RuntimeException("no direction for ${instruction.direction}")
                }
                acc.plus(acc.last() + direction * instruction.moves)
            }

        private fun boundedPoints(vertices: List<Pos>): Long {
            val zippedVertices = vertices.plus(vertices.first()).zipWithNext()

            val exteriorPoints = zippedVertices.sumOf { (a, b) -> a.distance(b) }
            val area = abs((0.5 * zippedVertices.sumOf { (a, b) -> (a.x * b.y) - (a.y * b.x) }).toLong()) //shoelace formula
            val interiorPoints = area - (exteriorPoints / 2) + 1 //rearranged pick's theorem for i

            return exteriorPoints + interiorPoints
        }

        fun part1BoundedPoints(input: String): Long {
            val instructions = parseInput(input)
            val vertices = verticesFromInstructions(instructions)
            return boundedPoints(vertices)
        }

        private fun fixInstructions(instructions: List<Instruction>) = instructions.map { instruction ->
            val distance = instruction.colour.take(5).toLong(16)
            val direction = when (instruction.colour.last()) {
                '0' -> 'R'
                '1' -> 'D'
                '2' -> 'L'
                '3' -> 'U'
                else -> throw RuntimeException("no direction for ${instruction.colour.last()}")
            }
            Instruction(direction, distance, instruction.moves.toString())
        }

        fun part2BoundedPoints(input: String): Long {
            val instructions = fixInstructions(parseInput(input))
            val loop = verticesFromInstructions(instructions)
            return boundedPoints(loop)
        }
    }
}