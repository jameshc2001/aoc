package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year23.Day17.*

class Day17Test {

    @Test
    fun `can parse the sample input`() {
        val graph = Day17.parseInput(sampleInput)
        assertThat(graph.nodes).contains(Pos(12, 12))
        assertThat(graph.weights).containsEntry(Pos(1, 1), 2)
        assertThat(graph.neighbours).containsEntry(
            Pos(2, 2),
            listOf(Pos(x=1, y=2), Pos(x=3, y=2), Pos(x=2, y=1), Pos(x=2, y=3))
        )
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day17.leastHeatLoss(sampleInput)).isEqualTo(102)
    }

    private val sampleInput = """
        2413432311323
        3215453535623
        3255245654254
        3446585845452
        4546657867536
        1438598798454
        4457876987766
        3637877979653
        4654967986887
        4564679986453
        1224686865563
        2546548887735
        4322674655533
    """.trimIndent()

}