package year23

import kotlin.math.sqrt

class Day24 {

    data class Pos(val x: Double, val y: Double, val z: Double) {
        operator fun plus(other: Pos) = Pos(x + other.x, y + other.y, z + other.z)
        operator fun times(num: Double) = Pos(x * num, y * num, z * num)
        operator fun div(num: Double) = Pos(x / num, y / num, z / num)
        operator fun unaryMinus() = Pos(-x, -y, -z)

        private fun length() = sqrt((x * x + y * y + z * z))
        fun unit() = if (length() == 0.0) Pos(0.0, 0.0, 0.0) else this / length()
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

        fun intersectionsIn(input: String, min: Double, max: Double): Int {
            val hailstones = parseInput(input)
            val paired = hailstones.foldIndexed(emptyList<Pair<Hailstone, Hailstone>>()) { index, acc, hailstone ->
                val remaining = hailstones.drop(index + 1)
                acc + remaining.map { hailstone to it }
            }
            return paired
                .mapNotNull { (h1, h2) -> timeOfIntersection(h1, h2)?.let { t -> h1.pos + h1.vel * t } }
                .count { pos -> pos.x >= min && pos.y >= min && pos.x <= max && pos.y <= max }
        }
    }


}