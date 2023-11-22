package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year22.Day16.Valve

class Day16Test {

    @Test
    fun `can parse sample input`() {
        val map = Day16.parseInput(sampleInput)
        assertThat(map).hasSize(10)
        assertThat(map).containsEntry(Valve("HH", 22), listOf(Valve("GG", 0)))
        assertThat(map).containsEntry(
            Valve("II", 0),
            listOf(Valve("AA", 0), Valve("JJ", 21))
        )
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day16.findMaxPressureRelease(sampleInput)).isEqualTo(1651)
    }

    private val sampleInput = """
        Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
        Valve BB has flow rate=13; tunnels lead to valves CC, AA
        Valve CC has flow rate=2; tunnels lead to valves DD, BB
        Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
        Valve EE has flow rate=3; tunnels lead to valves FF, DD
        Valve FF has flow rate=0; tunnels lead to valves EE, GG
        Valve GG has flow rate=0; tunnels lead to valves FF, HH
        Valve HH has flow rate=22; tunnel leads to valve GG
        Valve II has flow rate=0; tunnels lead to valves AA, JJ
        Valve JJ has flow rate=21; tunnel leads to valve II
    """.trimIndent()

}