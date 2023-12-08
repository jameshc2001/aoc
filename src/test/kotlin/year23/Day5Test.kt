package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day5Test {

    @Test
    fun `can parse sample input`() {
        val almanac = Day5.parseInput(sampleInput)
        assertThat(almanac.seeds).hasSize(4)
        assertThat(almanac.humidityToLocation).containsEntry(56, 60)
        assertThat(almanac.humidityToLocation).containsEntry(56 + 36, 60 + 36)
        assertThat(almanac.humidityToLocation).doesNotContainEntry(56 + 37, 60 + 37)
    }

    @Test
    fun `can get location number for soil number`() {
        val almanac = Day5.parseInput(sampleInput)
        assertThat(almanac.locationForSeed(79)).isEqualTo(82)
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day5.lowestSeedLocation(sampleInput)).isEqualTo(35)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day5::class.java.getResourceAsStream("/year23/day5.txt")!!.bufferedReader().readText()
        assertThat(Day5.lowestSeedLocation(input)).isEqualTo(35)
    }

    private val sampleInput = """
        seeds: 79 14 55 13

        seed-to-soil map:
        50 98 2
        52 50 48

        soil-to-fertilizer map:
        0 15 37
        37 52 2
        39 0 15

        fertilizer-to-water map:
        49 53 8
        0 11 42
        42 0 7
        57 7 4

        water-to-light map:
        88 18 7
        18 25 70

        light-to-temperature map:
        45 77 23
        81 45 19
        68 64 13

        temperature-to-humidity map:
        0 69 1
        1 0 69

        humidity-to-location map:
        60 56 37
        56 93 4
    """.trimIndent()

}