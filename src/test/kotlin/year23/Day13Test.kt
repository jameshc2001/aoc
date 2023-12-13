package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year23.Day13.Tile.Ash
import year23.Day13.Tile.Rock

class Day13Test {

    @Test
    fun `can parse sample input`() {
        val patterns = Day13.parseInput(sampleInput)
        assertThat(patterns).hasSize(2)

        val pattern1 = patterns.first()
        assertThat(pattern1.rows[2]).isEqualTo(listOf(
            Rock, Rock, Ash, Ash, Ash, Ash, Ash, Ash, Rock
        ))
        assertThat(pattern1.columns[5]).isEqualTo(listOf(
            Ash, Rock, Ash, Ash, Rock, Ash, Rock
        ))
        assertThat(pattern1.columns[4]).isEqualTo(pattern1.columns[5])
    }

    @Test
    fun `can detect line of symmetry in pattern`() {
        val patterns = Day13.parseInput(sampleInput)
        assertThat(Day13.linesOfSymmetry(patterns.first().columns)).isEqualTo(listOf(5))
        assertThat(Day13.linesOfSymmetry(patterns.first().rows)).isEqualTo(emptyList<Int>())
        assertThat(Day13.linesOfSymmetry(patterns.last().columns)).isEqualTo(emptyList<Int>())
        assertThat(Day13.linesOfSymmetry(patterns.last().rows)).isEqualTo(listOf(4))
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day13.summary(sampleInput)).isEqualTo(405)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day13::class.java.getResourceAsStream("/year23/day13.txt")!!.bufferedReader().readText()
        assertThat(Day13.summary(input)).isEqualTo(39939)
    }

    private val sampleInput = """
        #.##..##.
        ..#.##.#.
        ##......#
        ##......#
        ..#.##.#.
        ..##..##.
        #.#.##.#.

        #...##..#
        #....#..#
        ..##..###
        #####.##.
        #####.##.
        ..##..###
        #....#..#
    """.trimIndent()

}