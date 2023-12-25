package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year23.Day23.Pos

class Day23Test {

    @Test
    fun `can parse the sample input`() {
        val graph = Day23.parseInput(sampleInput)
        assertThat(graph.nodes).contains(Pos(1, 0))
        assertThat(graph.nodes).contains(Pos(21, 22))
        assertThat(graph.neighbours).containsEntry(Pos(1, 0), listOf(Pos(1, 1)))
        assertThat(graph.neighbours).containsEntry(Pos(10, 3), listOf(Pos(11, 3)))
        assertThat(graph.neighbours).containsEntry(Pos(3, 5), listOf(Pos(4, 5), Pos(3, 4), Pos(3, 6)))
        assertThat(graph.weights).containsEntry(Pos(1,0) to Pos(1, 1), 1)
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day23.longestPathLength(sampleInput)).isEqualTo(94)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day23::class.java.getResourceAsStream("/year23/day23.txt")!!.bufferedReader().readText()
        assertThat(Day23.longestPathLength(input)).isEqualTo(2222)
    }

    @Test
    fun `can parse the sample input when dry`() {
        val graph = Day23.parseInput(sampleInput, true)
        assertThat(graph.neighbours).containsEntry(Pos(10, 3), listOf(Pos(9, 3), Pos(11, 3)))
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day23.longestDryPathLength(sampleInput)).isEqualTo(154)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day23::class.java.getResourceAsStream("/year23/day23.txt")!!.bufferedReader().readText()
        assertThat(Day23.longestDryPathLength(input)).isEqualTo(154)
    }

    private val sampleInput = """
        #.#####################
        #.......#########...###
        #######.#########.#.###
        ###.....#.>.>.###.#.###
        ###v#####.#v#.###.#.###
        ###.>...#.#.#.....#...#
        ###v###.#.#.#########.#
        ###...#.#.#.......#...#
        #####.#.#.#######.#.###
        #.....#.#.#.......#...#
        #.#####.#.#.#########v#
        #.#...#...#...###...>.#
        #.#.#v#######v###.###v#
        #...#.>.#...>.>.#.###.#
        #####v#.#.###v#.#.###.#
        #.....#...#...#.#.#...#
        #.#########.###.#.#.###
        #...###...#...#...#.###
        ###.###.#.###v#####v###
        #...#...#.#.>.>.#.>.###
        #.###.###.#.###.#.#v###
        #.....###...###...#...#
        #####################.#
    """.trimIndent()

}