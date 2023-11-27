package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year22.Day17.Direction

class Day17Test {

    @Test
    fun `can parse input`() {
        val input = Day17.parseInput("><<")

        assertThat(input.next()).isEqualTo(Direction.RIGHT)
        assertThat(input.next()).isEqualTo(Direction.LEFT)
        assertThat(input.next()).isEqualTo(Direction.LEFT)
        assertThat(input.next()).isEqualTo(Direction.RIGHT)

        assertThat(Day17.parseInput(sampleInput).next()).isEqualTo(Direction.RIGHT)
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day17.heightAfterRocksFall(sampleInput, 2022)).isEqualTo(3068)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day17::class.java.getResourceAsStream("/year22/day17.txt")!!.bufferedReader().readText()
        assertThat(Day17.heightAfterRocksFall(input, 2022)).isEqualTo(3163)
    }

    private val sampleInput = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"

}