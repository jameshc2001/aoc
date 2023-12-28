package year23

import kotlin.math.sqrt

//part 2 solution heavily inspired by
//https://github.com/Jadarma/advent-of-code-kotlin-solutions/blob/main/solutions/aockt/y2023/Y2023D24.kt

class Day24 {

    data class Pos(val x: Double, val y: Double, val z: Double) {
        operator fun plus(other: Pos) = Pos(x + other.x, y + other.y, z + other.z)
        operator fun minus(other: Pos) = Pos(x - other.x, y - other.y, z - other.z)
        operator fun times(num: Double) = Pos(x * num, y * num, z * num)
        operator fun div(num: Double) = Pos(x / num, y / num, z / num)
        operator fun unaryMinus() = Pos(-x, -y, -z)

        private fun length() = sqrt((x * x + y * y + z * z))
        fun unit() = length().let { l -> if (l == 0.0) Pos(l, l, l) else this / l }
    }

    data class Hailstone(val pos: Pos, val vel: Pos) {
        val velXYUnit = vel.copy(x = vel.x, y = vel.y, z = 0.0).unit()
    }

    companion object {
        fun parseInput(input: String): List<Hailstone> {
            val regex = "-?[0-9]+".toRegex()
            return input.lines().map { it.replace("\r", "").replace("\n", "") }
                .map { line ->
                    val (ax, ay, az) = regex.findAll(line.substringBefore('@')).toList().map { it.value.toDouble() }
                    val (vx, vy, vz) = regex.findAll(line.substringAfter('@')).toList().map { it.value.toDouble() }
                    Hailstone(Pos(ax, ay, az), Pos(vx, vy, vz))
                }
        }

        fun parallelXY(h1: Hailstone, h2: Hailstone) = h1.velXYUnit == h2.velXYUnit || h1.velXYUnit == -h2.velXYUnit

        fun timeOfIntersection(h1: Hailstone, h2: Hailstone): Double? {
            if (parallelXY(h1, h2)) return null

            val (a, b) = h1.pos
            val (c, d) = h1.vel
            val (e, f) = h2.pos
            val (g, h) = h2.vel

            val u = (c * (f - b) + d * (a - e)) / (d * g - c * h)
            val t = (e + g * u - a) / c

            return if (u < 0 || t < 0) null else String.format("%.3f", t).toDouble()
        }

        private fun uniquePairs(hailstones: List<Hailstone>) =
            hailstones.foldIndexed(emptyList<Pair<Hailstone, Hailstone>>()) { index, acc, hailstone ->
                val remaining = hailstones.drop(index + 1)
                acc + remaining.map { hailstone to it }
            }

        fun intersectionsIn(input: String, min: Double, max: Double): Int {
            val hailstones = parseInput(input)
            val uniquePairs = uniquePairs(hailstones)
            return uniquePairs
                .mapNotNull { (h1, h2) -> timeOfIntersection(h1, h2)?.let { t -> h1.pos + h1.vel * t } }
                .count { pos -> pos.x >= min && pos.y >= min && pos.x <= max && pos.y <= max }
        }

        private fun MutableSet<Long>.removeImpossibleVelocities(h1Start: Long, h1Vel: Long, h2Start: Long, h2Vel: Long) {
            if (h1Start > h2Start && h1Vel > h2Vel) removeAll((h2Vel .. h1Vel))
            if (h2Start > h1Start && h2Vel > h1Vel) removeAll((h1Vel .. h2Vel))
        }

        private fun getPossibleVelocities(hailstones: List<Hailstone>, range: Long): List<Pos> {
            val possibleX = (-range .. range).toMutableSet()
            val possibleY = (-range .. range).toMutableSet()
            val possibleZ = (-range .. range).toMutableSet()

            uniquePairs(hailstones).forEach { (h1, h2) ->
                possibleX.removeImpossibleVelocities(h1.pos.x.toLong(), h1.vel.x.toLong(), h2.pos.x.toLong(), h2.vel.x.toLong())
                possibleY.removeImpossibleVelocities(h1.pos.y.toLong(), h1.vel.y.toLong(), h2.pos.y.toLong(), h2.vel.y.toLong())
                possibleZ.removeImpossibleVelocities(h1.pos.z.toLong(), h1.vel.z.toLong(), h2.pos.z.toLong(), h2.vel.z.toLong())
            }

            return possibleX.flatMap { x ->
                possibleY.flatMap { y ->
                    possibleZ.map { z ->
                        Pos(x.toDouble(), y.toDouble(), z.toDouble())
                    }
                }
            }
        }

        private fun getStartPos(h1: Hailstone, h2: Hailstone, vel: Pos): Pos? {
            val h1Relative = h1.copy(vel = h1.vel - vel)
            val h2Relative = h2.copy(vel = h2.vel - vel)
            return timeOfIntersection(h1Relative, h2Relative)?.let { t -> h1Relative.pos + h1Relative.vel * t }
        }

        private fun collision(h1: Hailstone, h2: Hailstone): Boolean {
            val timeOfCollision = when {
                h1.vel.x != h2.vel.x -> (h2.pos.x - h1.pos.x) / (h1.vel.x - h2.vel.x)
                h1.vel.y != h2.vel.y -> (h2.pos.y - h1.pos.y) / (h1.vel.y - h2.vel.y)
                h1.vel.z != h2.vel.z -> (h2.pos.z - h1.pos.z) / (h1.vel.z - h2.vel.z)
                else -> return false
            }
            if (timeOfCollision < 0) return false
            return (h1.pos + h1.vel * timeOfCollision) == (h2.pos + h2.vel * timeOfCollision)
        }

        fun startingSum(input: String): Long {
            val hailstones = parseInput(input)
            val (h1, h2) = hailstones
            val velocityRange = if (hailstones.size < 10) 10L else 400L

            val start = getPossibleVelocities(hailstones, velocityRange)
                .mapNotNull { vel -> getStartPos(h1, h2, vel)?.let { startPos -> Hailstone(startPos, vel) } }
                .first { rock -> hailstones.all { collision(rock, it) } }
                .pos

            return (start.x + start.y + start.z).toLong()
        }
    }
}