package year23

import kotlin.math.abs

class Day5 {

    data class AlmanacMap(val sourceStart: Long, val destinationStart: Long, val range: Long) {
        fun get(index: Long): Long? {
            return if (index in (sourceStart..<sourceStart + range)) {
                destinationStart + abs(index - sourceStart)
            } else null
        }

        fun getFromValue(value: Long): Long? {
            return if (value in (destinationStart..<destinationStart + range)) {
                sourceStart + abs(value - destinationStart)
            } else null
        }

        companion object {
            fun fromString(input: String): AlmanacMap {
                val (destinationStart, sourceStart, range) = input.split(" ").mapNotNull { it.toLongOrNull() }
                return AlmanacMap(sourceStart, destinationStart, range)
            }
        }
    }

    data class AlmanacCombinedMaps(val maps: List<AlmanacMap>) {
        fun get(index: Long) = maps.mapNotNull { it.get(index) }.singleOrNull() ?: index

        fun getFromValue(value: Long) = maps.mapNotNull { it.getFromValue(value) }.singleOrNull() ?: value

        companion object {
            fun fromString(input: String): AlmanacCombinedMaps {
                val lines = input.lines().drop(1)
                return AlmanacCombinedMaps(lines.map { AlmanacMap.fromString(it) })
            }
        }
    }

    data class Almanac(
        val seeds: List<Long>,
        val seedToSoil: AlmanacCombinedMaps,
        val soilToFertilizer: AlmanacCombinedMaps,
        val fertilizerToWater: AlmanacCombinedMaps,
        val waterToLight: AlmanacCombinedMaps,
        val lightToTemperature: AlmanacCombinedMaps,
        val temperatureToHumidity: AlmanacCombinedMaps,
        val humidityToLocation: AlmanacCombinedMaps,
    ) {
        fun locationForSeed(seed: Long): Long {
            val soil = seedToSoil.get(seed)
            val fertilizer = soilToFertilizer.get(soil)
            val water = fertilizerToWater.get(fertilizer)
            val light = waterToLight.get(water)
            val temperature = lightToTemperature.get(light)
            val humidity = temperatureToHumidity.get(temperature)
            return humidityToLocation.get(humidity)
        }

        fun seedForLocation(location: Long): Long {
            val humidity = humidityToLocation.getFromValue(location)
            val temperature = temperatureToHumidity.getFromValue(humidity)
            val light = lightToTemperature.getFromValue(temperature)
            val water = waterToLight.getFromValue(light)
            val fertilizer = fertilizerToWater.getFromValue(water)
            val soil = soilToFertilizer.getFromValue(fertilizer)
            return seedToSoil.getFromValue(soil)
        }
    }

    companion object {
        fun parseInput(input: String): Almanac {
            val parts = input
                .split("\n\n")
                .let { if (it.size == 1) it.first().split("\r\n\r\n") else it }
            return Almanac(
                seeds = parts[0].substringAfter("seeds:").intsList(),
                seedToSoil = AlmanacCombinedMaps.fromString(parts[1]),
                soilToFertilizer = AlmanacCombinedMaps.fromString(parts[2]),
                fertilizerToWater = AlmanacCombinedMaps.fromString(parts[3]),
                waterToLight = AlmanacCombinedMaps.fromString(parts[4]),
                lightToTemperature = AlmanacCombinedMaps.fromString(parts[5]),
                temperatureToHumidity = AlmanacCombinedMaps.fromString(parts[6]),
                humidityToLocation = AlmanacCombinedMaps.fromString(parts[7]),
            )
        }

        private fun String.intsList() = split(" ").mapNotNull { it.toLongOrNull() }

        fun lowestSeedLocation(input: String): Long {
            val almanac = parseInput(input)
            return almanac.seeds.minOf { almanac.locationForSeed(it) }
        }

        fun lowestSeedLocationUsingRanges(input: String): Long {
            val almanac = parseInput(input)
            val seeds = almanac.seeds
                .zipWithNext()
                .filterIndexed { index, _ -> index % 2 == 0 }
                .map { it.first ..< it.first + it.second }

            (0 ..< almanac.humidityToLocation.maps.maxOf { it.destinationStart + it.range }).forEach { location ->
                val seed = almanac.seedForLocation(location)
                if (seeds.any { seed in it }) return location
            }

            throw RuntimeException("could not find lowest location")
        }
    }


}