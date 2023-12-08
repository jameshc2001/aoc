package year23

class Day5 {
    data class Almanac(
        val seeds: List<Long>,
        val seedToSoil: Map<Long, Long>,
        val soilToFertilizer: Map<Long, Long>,
        val fertilizerToWater: Map<Long, Long>,
        val waterToLight: Map<Long, Long>,
        val lightToTemperature: Map<Long, Long>,
        val temperatureToHumidity: Map<Long, Long>,
        val humidityToLocation: Map<Long, Long>,
    ) {
        fun locationForSeed(seed: Long): Long {
            val soil = seedToSoil.getOrUseIndex(seed)
            val fertilizer = soilToFertilizer.getOrUseIndex(soil)
            val water = fertilizerToWater.getOrUseIndex(fertilizer)
            val light = waterToLight.getOrUseIndex(water)
            val temperature = lightToTemperature.getOrUseIndex(light)
            val humidity = temperatureToHumidity.getOrUseIndex(temperature)
            return humidityToLocation.getOrUseIndex(humidity)
        }

        private fun Map<Long, Long>.getOrUseIndex(index: Long) = getOrDefault(index, index)
    }

    companion object {
        fun parseInput(input: String): Almanac {
            val parts = input
                .split("\n\n")
                .let { if (it.size == 1) it.first().split("\r\n\r\n") else it }
            return Almanac(
                seeds = parts[0].substringAfter("seeds:").intsList(),
                seedToSoil = parseSection(parts[1]),
                soilToFertilizer = parseSection(parts[2]),
                fertilizerToWater = parseSection(parts[3]),
                waterToLight = parseSection(parts[4]),
                lightToTemperature = parseSection(parts[5]),
                temperatureToHumidity = parseSection(parts[6]),
                humidityToLocation = parseSection(parts[7]),
            )
        }

        private fun String.intsList() = split(" ").mapNotNull { it.toLongOrNull() }

        private fun parseSection(section: String): Map<Long, Long> {
            val lines = section.lines().drop(1)
            return lines.flatMap { it.destinationSourceRangeToMap() }.toMap()
        }

        private fun String.destinationSourceRangeToMap(): List<Pair<Long, Long>> {
            val (destinationStart, sourceStart, range) = this.intsList()
            return (0..<range).map { offset -> sourceStart + offset to destinationStart + offset }
        }

        fun lowestSeedLocation(input: String): Long {
            val almanac = parseInput(input)
            return almanac.seeds.minOf { almanac.locationForSeed(it) }
        }
    }


}