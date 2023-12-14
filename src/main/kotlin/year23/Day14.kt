package year23

class Day14 {

    data class Pos(val x: Int, val y: Int) {
        operator fun plus(other: Pos) = Pos(x + other.x, y + other.y)
        fun bounded(lower: Pos, upper: Pos) = x >= lower.x && y >= lower.y && x < upper.x && y < upper.y
    }
    data class Platform(val roundRocks: List<Pos>, val cubeRocks: Set<Pos>, val max: Pos) {
        fun print() {
            (0 ..< max.y).reversed().forEach { y ->
                println()
                (0 ..< max.x).forEach { x ->
                    val pos = Pos(x, y)
                    when (pos) {
                        in roundRocks -> print('O')
                        in cubeRocks -> print('#')
                        else -> print('.')
                    }
                }
            }
        }
    }

    companion object {
        fun parseInput(input: String): Platform {
            val roundRocks = mutableListOf<Pos>()
            val cubeRocks = mutableSetOf<Pos>()
            val lines = input.lines().map { it.replace("\r", "").replace("\n", "") }
            val max = Pos(lines.first().length, lines.size)
            lines.reversed().forEachIndexed { y, line -> //reversed so north is greater than south
                line.forEachIndexed { x, c ->
                    when (c) {
                        'O' -> roundRocks.add(Pos(x, y))
                        '#' -> cubeRocks.add(Pos(x, y))
                    }
                }
            }
            return Platform(roundRocks, cubeRocks, max)
        }

        fun tilt(platform: Platform, direction: Pos): Platform {
            val (roundRocks, cubeRocks, max) = platform

            var previousRoundRocks: List<Pos>
            var currentRoundRocks = roundRocks
            do {
                previousRoundRocks = currentRoundRocks
                currentRoundRocks = previousRoundRocks.map { oldPos ->
                    val newPos = oldPos + direction
                    if (newPos.bounded(Pos(0, 0), max)
                        && newPos !in cubeRocks
                        && newPos !in previousRoundRocks) newPos
                    else oldPos
                }
            } while (previousRoundRocks != currentRoundRocks)

            return platform.copy(roundRocks = currentRoundRocks)
        }

        fun northLoad(input: String): Int {
            val platform = parseInput(input)
            val tiltedNorth = tilt(platform, Pos(0, 1))
            return tiltedNorth.roundRocks.sumOf { pos -> pos.y + 1 }
        }
    }

}