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

    @Test
    fun `can get answer for part 2 using simple sample input`() {
        assertThat(Day10.enclosedArea(sampleInputPart2Simple)).isEqualTo(4)
    }

    @Test
    fun `can get answer for part 2 using hard sample input`() {
        assertThat(Day10.enclosedArea(sampleInputPart2)).isEqualTo(8)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day10::class.java.getResourceAsStream("/year23/day10.txt")!!.bufferedReader().readText()
        assertThat(Day10.enclosedArea(input)).isEqualTo(523)
    }

    private val sampleInput = """
        7-F7-
        .FJ|7
        SJLL7
        |F--J
        LJ.LJ
    """.trimIndent()

    private val sampleInputPart2Simple = """
        ...........
        .S-------7.
        .|F-----7|.
        .||.....||.
        .||.....||.
        .|L-7.F-J|.
        .|..|.|..|.
        .L--J.L--J.
        ...........
    """.trimIndent()

    private val sampleInputPart2 = """
        .F----7F7F7F7F-7....
        .|F--7||||||||FJ....
        .||.FJ||||||||L7....
        FJL7L7LJLJ||LJ.L-7..
        L--J.L7...LJS7F-7L7.
        ....F-J..F7FJ|L7L7L7
        ....L7.F7||L7|.L7L7|
        .....|FJLJ|FJ|F7|.LJ
        ....FJL-7.||.||||...
        ....L---J.LJ.LJLJ...
    """.trimIndent()

}