package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year23.Day8.DirectionsAndNodes.Direction.Left
import year23.Day8.Node

class Day8Test {

    @Test
    fun `can parse the sample input`() {
        val directionsAndNodes = Day8.parseInput(sampleInput)
        assertThat(directionsAndNodes.nextDirection()).isEqualTo(Left)
        assertThat(directionsAndNodes.nodes).hasSize(3)
        assertThat(directionsAndNodes.nodes).contains(Node("BBB", "AAA", "ZZZ"))
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day8.stepsToReach(sampleInput, "ZZZ")).isEqualTo(6)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day8::class.java.getResourceAsStream("/year23/day8.txt")!!.bufferedReader().readText()
        assertThat(Day8.stepsToReach(input, "ZZZ")).isEqualTo(11567)
    }

    private val sampleInput = """
        LLR

        AAA = (BBB, BBB)
        BBB = (AAA, ZZZ)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent()

}