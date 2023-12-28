package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year23.Day2.Amounts
import year23.Day2.Game

class Day2Test {

    @Test
    fun `can parse sample input`() {
        val games = Day2.parseInput(sampleInput)
        assertThat(games).hasSize(5)
        assertThat(games).contains(Game(
            3,
            listOf(Amounts(20, 8, 6), Amounts(4, 13, 5), Amounts(1, 5, 0))
        ))
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day2.possibleGames(sampleInput, Amounts(12, 13, 14))).isEqualTo(8)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day2::class.java.getResourceAsStream("/year23/day2.txt")!!.bufferedReader().readText()
        assertThat(Day2.possibleGames(input, Amounts(12, 13, 14))).isEqualTo(2476)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day2.minimumSetPowers(sampleInput)).isEqualTo(2286)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day2::class.java.getResourceAsStream("/year23/day2.txt")!!.bufferedReader().readText()
        assertThat(Day2.minimumSetPowers(input)).isEqualTo(54911)
    }

    private val sampleInput = """
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    """.trimIndent()

}