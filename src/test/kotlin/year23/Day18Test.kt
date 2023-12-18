package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year23.Day18.Instruction

class Day18Test {

    @Test
    fun `can parse the sample input`() {
        val instructions = Day18.parseInput(sampleInput)
        assertThat(instructions).hasSize(14)
        assertThat(instructions).contains(
            Instruction('U', 2, "caa173")
        )
    }

    @Test
    fun `can get vertices from instructions`() {
        val instructions = Day18.parseInput(sampleInput)
        val vertices = Day18.verticesFromInstructions(instructions).toSet()
        assertThat(vertices).hasSize(14)
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day18.part1BoundedPoints(sampleInput)).isEqualTo(62)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day18::class.java.getResourceAsStream("/year23/day18.txt")!!.bufferedReader().readText()
        assertThat(Day18.part1BoundedPoints(input)).isEqualTo(40761)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day18.part2BoundedPoints(sampleInput)).isEqualTo(952408144115)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day18::class.java.getResourceAsStream("/year23/day18.txt")!!.bufferedReader().readText()
        assertThat(Day18.part2BoundedPoints(input)).isEqualTo(106920098354636)
    }

    private val sampleInput = """
        R 6 (#70c710)
        D 5 (#0dc571)
        L 2 (#5713f0)
        D 2 (#d2c081)
        R 2 (#59c680)
        D 2 (#411b91)
        L 5 (#8ceee2)
        U 2 (#caa173)
        L 1 (#1b58a2)
        U 2 (#caa171)
        R 2 (#7807d2)
        U 3 (#a77fa3)
        L 2 (#015232)
        U 2 (#7a21e3)
    """.trimIndent()

}