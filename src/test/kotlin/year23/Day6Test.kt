package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day6Test {

    @Test
    fun `can parse sample input`() {
        val races = Day6.parseInput(sampleInput)
        assertThat(races).isNotEmpty()
        assertThat(races).contains(Day6.Race(7, 9))
        assertThat(races).contains(Day6.Race(15, 40))
        assertThat(races).contains(Day6.Race(30, 200))
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day6.waysToWin(sampleInput)).isEqualTo(288)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day6::class.java.getResourceAsStream("/year23/day6.txt")!!.bufferedReader().readText()
        assertThat(Day6.waysToWin(input)).isEqualTo(1731600)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day6.waysToWinSingleRace(sampleInput)).isEqualTo(71503)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day6::class.java.getResourceAsStream("/year23/day6.txt")!!.bufferedReader().readText()
        assertThat(Day6.waysToWinSingleRace(input)).isEqualTo(40087680)
    }

    private val sampleInput = """
        Time:      7  15   30
        Distance:  9  40  200
    """.trimIndent()

}