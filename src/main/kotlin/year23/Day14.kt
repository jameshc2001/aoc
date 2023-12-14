package year23

class Day14 {

    data class Pos(val x: Int, val y: Int) {
        operator fun plus(other: Pos) = Pos(x + other.x, y + other.y)
        fun bounded(lower: Pos, upper: Pos) = x >= lower.x && y >= lower.y && x < upper.x && y < upper.y
    }

    data class Platform(val roundRocks: List<Pos>, val cubeRocks: Set<Pos>, val max: Pos) {
        fun northLoad() = roundRocks.sumOf { it.y + 1 }
    }

    data class RepetitionInfo(
        val loadsBeforeRepeatedSection: List<Int>,
        val loadsForRepeatedSection: List<Int>,
    )

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
            return tiltedNorth.northLoad()
        }

        fun cycle(platform: Platform) = listOf(
            Pos(0, 1),
            Pos(-1, 0),
            Pos(0, -1),
            Pos(1, 0)
        ).fold(platform) { acc, direction -> tilt(acc, direction) }

        fun getRepetitionInfo(platform: Platform): RepetitionInfo {
            val loads = mutableListOf<Int>()
            var currentPlatform = platform
            var repeatedSection = emptyList<Int>()
            do {
                currentPlatform = cycle(currentPlatform)
                loads.add(currentPlatform.northLoad())
                if (loads.size >= 10) {
                    (5 ..< minOf(loads.size / 2, 20)).forEach { sectionLength ->
                        val a = loads.subList(loads.size - sectionLength, loads.size)
                        val b = loads.subList(loads.size - 2 * sectionLength, loads.size - sectionLength)
                        if (a == b) repeatedSection = a
                    }
                }
            } while (repeatedSection.isEmpty())
            val loadsBeforeRepeatedSection = loads.subList(0, loads.size - 2 * repeatedSection.size)
            return RepetitionInfo(loadsBeforeRepeatedSection, repeatedSection)
        }

        fun northLoadAfterCycles(input: String, cycles: Long): Int {
            val platform = parseInput(input)
            val repetitionInfo = getRepetitionInfo(platform)

            val cyclesBeforeRepetition = repetitionInfo.loadsBeforeRepeatedSection.size
            val cyclesInRepetition = repetitionInfo.loadsForRepeatedSection.size

            //-1 because repetitionInfo.loadsForRepeatedSection indices start from 0 not 1
            val indexOfLoad = (cycles - cyclesBeforeRepetition - 1) % cyclesInRepetition
            return repetitionInfo.loadsForRepeatedSection[indexOfLoad.toInt()]
        }
    }
}