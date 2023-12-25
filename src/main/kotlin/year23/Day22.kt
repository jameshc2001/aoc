package year23

import kotlin.math.sqrt

class Day22 {

    data class Pos(val x: Int, val y: Int, val z: Int) {
        operator fun minus(other: Pos) = Pos(x - other.x, y - other.y, z - other.z)
        operator fun plus(other: Pos) = Pos(x + other.x, y + other.y, z + other.z)
        operator fun div(num: Int) = Pos(x / num, y / num, z /num)

        private fun length() = sqrt((x * x + y * y + z * z).toDouble())
        fun safeUnit() = length().toInt().let { if (it == 0) this else this / it }

        companion object {
            val down = Pos(0, 0, -1)
        }
    }

    data class Brick(val id: Int, val cubes: List<Pos>) {
        fun move(pos: Pos) = Brick(id, cubes.map { it + pos })
    }

    companion object {
        fun parseInput(input: String): List<Brick> {
            return input.lines()
                .map { it.split('~') }
                .map { (start, end) ->
                    val (sx, sy, sz) = start.split(',').map { it.toInt() }
                    val (ex, ey, ez) = end.split(',').map { it.toInt() }
                    Pos(sx, sy, sz) to Pos(ex, ey, ez)
                }
                .mapIndexed { index, (start, end) ->
                    val direction = (end - start).safeUnit()
                    val cubes = mutableListOf(start)
                    var current = start
                    do {
                        current += direction
                        cubes.add(current)
                    } while (current != end)
                    Brick(index, cubes)
                }
        }

        private fun Brick.canMoveDown(occupied: Set<Pos>): Boolean {
            val lowestZ = cubes.minOf { it.z }
            val lowestCubes = cubes.filter { it.z == lowestZ }
            return !lowestCubes.any { cube -> cube.z == 1 || cube + Pos.down in occupied }
        }
        
        private fun Brick.canRemove(bricks: List<Brick>): Boolean {
            val otherBricks = bricks - this
            val bricksAbove = otherBricks.filter { other -> other.cubes.any { it + Pos.down in this.cubes } }
            val occupied = otherBricks.map { it.cubes }.flatten().toSet()
            return !bricksAbove.any { it.canMoveDown(occupied) } //no bricks allowed to fall if this one is removed
        }

        private fun Brick.bricksInReaction(bricks: List<Brick>): Int {
            val otherBricks = bricks - this
            val simulated = simulate(otherBricks)
            return (simulated.toSet() - otherBricks.toSet()).size
        }

        //could optimize by not simulating bricks that have settled
        private fun simulate(bricks: List<Brick>): List<Brick> {
            var simulatedBricks = bricks
            var bricksThatFell: Int
            do {
                bricksThatFell = 0
                val occupied = simulatedBricks.map { it.cubes }.flatten().toSet()
                simulatedBricks = simulatedBricks.map { brick ->
                    if (brick.canMoveDown(occupied)) brick.move(Pos.down).also { bricksThatFell++ } else brick
                }
            } while (bricksThatFell != 0)
            return simulatedBricks
        }

        fun removableBricks(input: String): Int {
            val bricks = simulate(parseInput(input))
            return bricks.count { it.canRemove(bricks) }
        }

        fun chainReactionSum(input: String): Int {
            val bricks = simulate(parseInput(input))
            val reactionCausingBricks = bricks.filterNot { it.canRemove(bricks) }
            return reactionCausingBricks.sumOf { it.bricksInReaction(bricks) }
        }
    }
}