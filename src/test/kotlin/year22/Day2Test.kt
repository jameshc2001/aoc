package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year22.Day2.Companion.Move
import year22.Day2.Companion.Move.*
import year22.Day2.Companion.Outcome.*

class Day2Test {

    @Test
    fun `can get move from character`() {
        assertThat(Move.fromString("A")).isEqualTo(ROCK)
        assertThat(Move.fromString("X")).isEqualTo(ROCK)
        assertThat(Move.fromString("B")).isEqualTo(PAPER)
        assertThat(Move.fromString("Y")).isEqualTo(PAPER)
        assertThat(Move.fromString("C")).isEqualTo(SCISSORS)
        assertThat(Move.fromString("Z")).isEqualTo(SCISSORS)
    }

    @Test
    fun `can get score given opponents move and your move`() {
        assertThat(Day2.getYourScore(ROCK, ROCK)).isEqualTo(4)
        assertThat(Day2.getYourScore(ROCK, PAPER)).isEqualTo(8)
        assertThat(Day2.getYourScore(ROCK, SCISSORS)).isEqualTo(3)
    }

    @Test
    fun `can get calculate score for part 1 on sample input`() {
        assertThat(Day2.calculateScoreForPart1(sampleInput)).isEqualTo(15)
    }

    @Test
    fun `can get calculate score for part 1 on question input`() {
        val input = Day2::class.java.getResourceAsStream("/year22/day2.txt")!!.bufferedReader().readText()
        assertThat(Day2.calculateScoreForPart1(input)).isEqualTo(15572)
    }

    @Test
    fun `can get move needed for desired outcome`() {
        assertThat(Day2.getRequiredMove(ROCK, LOSE)).isEqualTo(SCISSORS)
        assertThat(Day2.getRequiredMove(ROCK, DRAW)).isEqualTo(ROCK)
        assertThat(Day2.getRequiredMove(ROCK, WIN)).isEqualTo(PAPER)
    }

    @Test
    fun `can get calculate score for part 2 on sample input`() {
        assertThat(Day2.calculateScoreForPart2(sampleInput)).isEqualTo(12)
    }

    @Test
    fun `can get calculate score for part 2 on question input`() {
        val input = Day2::class.java.getResourceAsStream("/year22/day2.txt")!!.bufferedReader().readText()
        assertThat(Day2.calculateScoreForPart2(input)).isEqualTo(16098)
    }

    private val sampleInput = """
        A Y
        B X
        C Z
    """.trimIndent()

}