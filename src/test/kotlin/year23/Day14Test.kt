package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year23.Day14.Pos

class Day14Test {

    @Test
    fun `can parse sample input`() {
        val platform = Day14.parseInput(sampleInput)
        assertThat(platform.max).isEqualTo(Pos(10, 10))
        assertThat(platform.cubeRocks).contains(Pos(8, 5))
        assertThat(platform.roundRocks).contains(Pos(2, 8))
    }

    @Test
    fun `can tilt north`() {
        val platform = Day14.parseInput(sampleInput)
        val expected = Day14.parseInput(sampleTiltedNorth)
        val tiltedNorth = Day14.tilt(platform, Pos(0, 1))
        assertThat(tiltedNorth.roundRocks.toSet()).isEqualTo(expected.roundRocks.toSet())
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day14.northLoad(sampleInput)).isEqualTo(136)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day14::class.java.getResourceAsStream("/year23/day14.txt")!!.bufferedReader().readText()
        assertThat(Day14.northLoad(input)).isEqualTo(113078)
    }

    private val sampleInput = """
        O....#....
        O.OO#....#
        .....##...
        OO.#O....O
        .O.....O#.
        O.#..O.#.#
        ..O..#O..O
        .......O..
        #....###..
        #OO..#....
    """.trimIndent()

    private val sampleTiltedNorth = """
        OOOO.#.O..
        OO..#....#
        OO..O##..O
        O..#.OO...
        ........#.
        ..#....#.#
        ..O..#.O.O
        ..O.......
        #....###..
        #....#....
    """.trimIndent()
}