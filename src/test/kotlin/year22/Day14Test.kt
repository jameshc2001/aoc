package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year22.Day14.Companion.simulateOneSand
import year22.Day14.Coordinate

class Day14Test {

    @Test
    fun `can generate rocks from one line of input`() {
        val input = "498,4 -> 498,6 -> 496,6"
        assertThat(Day14.parseLine(input)).isEqualTo(setOf(
            Coordinate(498, 4),
            Coordinate(498, 5),
            Coordinate(498, 6),
            Coordinate(497, 6),
            Coordinate(496, 6),
        ))
    }

    @Test
    fun `can simulate three pieces of sand on sample input`() {
        val rocks = Day14.parseInput(sampleInput)
        val abyssStart = rocks.maxOf { it.y }
        val sandRestPosition1 = simulateOneSand(rocks, abyssStart)!!
        val sandRestPosition2 = simulateOneSand(rocks.plus(sandRestPosition1), abyssStart)!!
        val sandRestPosition3 = simulateOneSand(rocks.plus(setOf(sandRestPosition1, sandRestPosition2)), abyssStart)
        assertThat(sandRestPosition1).isEqualTo(Coordinate(500, 8))
        assertThat(sandRestPosition2).isEqualTo(Coordinate(499, 8))
        assertThat(sandRestPosition3).isEqualTo(Coordinate(501, 8))
    }

    private val sampleInput = """
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent()

}