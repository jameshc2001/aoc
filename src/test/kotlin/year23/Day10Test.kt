package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year23.Day10.Pos

class Day10Test {

    @Test
    fun `can parse the sample input`() {
        val (start, map) = Day10.parseInput(sampleInput)
        assertThat(start).isEqualTo(Pos(0, 2))
        assertThat(map).hasSize(23)
        assertThat(map).containsEntry(Pos(0, 2), setOf(Pos(0, 3), Pos(1, 2)))
    }


    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day10.stepsToFurthestPoint(sampleInput)).isEqualTo(8)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day10::class.java.getResourceAsStream("/year23/day10.txt")!!.bufferedReader().readText()
        assertThat(Day10.stepsToFurthestPoint(input)).isEqualTo(6757)
    }

    private val sampleInput = """
        7-F7-
        .FJ|7
        SJLL7
        |F--J
        LJ.LJ
    """.trimIndent()

}