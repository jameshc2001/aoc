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

    @Test
    fun `can find cycle using sample input`() {
        val jetPattern = Day17.parseInput(sampleInput)
        val cycleData = Day17.findRockFallCycle(jetPattern)
        assertThat(cycleData.initialHeightDeltas).hasSize(15)
        assertThat(cycleData.cycleHeightDeltas).hasSize(35)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day17.heightAfterRocksFallUsingCycle(sampleInput, 1000000000000)).isEqualTo(1514285714288)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day17::class.java.getResourceAsStream("/year22/day17.txt")!!.bufferedReader().readText()
        assertThat(Day17.heightAfterRocksFallUsingCycle(input, 1000000000000)).isEqualTo(1560932944615)
    }

    private val sampleInput = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"

}