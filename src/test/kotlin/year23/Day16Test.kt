package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year23.Day16.Pos

class Day16Test {

    @Test
    fun `can parse sample input`() {
        val layout = Day16.parseInput(sampleInput)
        assertThat(layout.map).containsEntry(Pos(1, 0), '|')
        assertThat(layout.map).containsEntry(Pos(2, 1), '-')
        assertThat(layout.map).containsEntry(Pos(4, 1), '\\')
        assertThat(layout.map).containsEntry(Pos(3, 9), '/')
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day16.energisedTiles(sampleInput)).isEqualTo(46)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day16::class.java.getResourceAsStream("/year23/day16.txt")!!.bufferedReader().readText()
        assertThat(Day16.energisedTiles(input)).isEqualTo(7236)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day16.bestEnergisedTiles(sampleInput)).isEqualTo(51)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day16::class.java.getResourceAsStream("/year23/day16.txt")!!.bufferedReader().readText()
        assertThat(Day16.bestEnergisedTiles(input)).isEqualTo(7521)
    }

    private val sampleInput = """
        .|...\....
        |.-.\.....
        .....|-...
        ........|.
        ..........
        .........\
        ..../.\\..
        .-.-/..|..
        .|....-|.\
        ..//.|....
    """.trimIndent()

}