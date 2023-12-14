package year23

class Day14 {

    data class Pos(val x: Int, val y: Int) {
        operator fun plus(other: Pos) = Pos(x + other.x, y + other.y)
        operator fun minus(other: Pos) = Pos(x - other.x, y - other.y)
        operator fun times(scale: Int) = Pos(x * scale, y * scale)
        operator fun Int.times(pos: Pos) = Pos(pos.x * this, pos.y * this)
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

        private fun rocksInDirection(pos: Pos, platform: Platform, direction: Pos): List<Pos> {
            var currentPos = pos
            val rocks = mutableListOf<Pos>()
            do {
                currentPos += direction
                if (currentPos in platform.roundRocks) rocks.add(currentPos)
            } while (currentPos.bounded(Pos(0, 0), platform.max) && currentPos !in platform.cubeRocks)
            return rocks + currentPos //current pos is end
        }

        fun tilt(platform: Platform, direction: Pos): Platform {
            val newRoundRocks = platform.roundRocks.map { pos ->
                val rocksInDirection = rocksInDirection(pos, platform, direction)
                val end = rocksInDirection.last()
                end - (direction.times(rocksInDirection.size))
            }
            return platform.copy(roundRocks = newRoundRocks)
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
            var repeatedSection: List<Int>
            do {
                currentPlatform = cycle(currentPlatform)
                loads.add(currentPlatform.northLoad())
                repeatedSection = findRepeatedSection(loads)
            } while (repeatedSection.isEmpty())
            val loadsBeforeRepeatedSection = loads.subList(0, loads.size - 2 * repeatedSection.size)
            return RepetitionInfo(loadsBeforeRepeatedSection, repeatedSection)
        }

        private fun findRepeatedSection(loads: MutableList<Int>): List<Int> {
            if (loads.size >= 10) { //assume repetition has size between 5 and 20
                (5..<minOf(loads.size / 2, 20)).forEach { sectionLength ->
                    val left = loads.subList(loads.size - sectionLength, loads.size)
                    val right = loads.subList(loads.size - 2 * sectionLength, loads.size - sectionLength)
                    if (left == right) return left
                }
            }
            return emptyList()
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