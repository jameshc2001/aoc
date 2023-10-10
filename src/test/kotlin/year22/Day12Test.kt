package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year22.Day12.Coordinate
import year22.Day12.Height

class Day12Test {

    @Test
    fun `can parse input to map of coordinates to height`() {
        val heightMap = Day12.parseInput(sampleInput)
        assertThat(heightMap.coordinatesToHeights).containsEntry(Coordinate(0, 0), Height(0))
        assertThat(heightMap.coordinatesToHeights).containsEntry(Coordinate(5, 2), Height(25))
        assertThat(heightMap.coordinatesToHeights).containsEntry(Coordinate(7, 4), Height(8))
        assertThat(heightMap.coordinatesToHeights).doesNotContainKey(Coordinate(8, 4))
        assertThat(heightMap.coordinatesToHeights).doesNotContainKey(Coordinate(7, 5))
        assertThat(heightMap.start).isEqualTo(Coordinate(0, 0))
        assertThat(heightMap.end).isEqualTo(Coordinate(5, 2))
    }

    @Test
    fun `can create directed graph from height map`() {
        val directedGraph = Day12.createDirectedGraph(Day12.parseInput(sampleInput))
        assertThat(directedGraph).containsEntry(
            Coordinate(0, 0),
            setOf(Coordinate(0, 1), Coordinate(1, 0))
        )
        assertThat(directedGraph).containsEntry(
            Coordinate(1, 2),
            setOf(Coordinate(1, 3), Coordinate(1, 1), Coordinate(0, 2), Coordinate(2, 2))
        )
        assertThat(directedGraph).containsEntry(
            Coordinate(2, 0),
            setOf(Coordinate(1, 0), Coordinate(2, 1))
        )
    }

    @Test
    fun `can perform dijkstra's algorithm on graph`() {
        val heightMap = Day12.parseInput(sampleInput)
        val directedGraph = Day12.createDirectedGraph(heightMap)
        val dijkstraResults = Day12.dijkstra(directedGraph, heightMap.start, heightMap.end)
        assertThat(dijkstraResults.distances).containsEntry(Coordinate(5, 2), 31)
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day12.lengthOfShortestPath(sampleInput)).isEqualTo(31)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day12::class.java.getResourceAsStream("/year22/day12.txt")!!.bufferedReader().readText()
        assertThat(Day12.lengthOfShortestPath(input)).isEqualTo(370)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day12.lengthOfAnyShortedPath(sampleInput)).isEqualTo(29)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day12::class.java.getResourceAsStream("/year22/day12.txt")!!.bufferedReader().readText()
        assertThat(Day12.lengthOfAnyShortedPath(input)).isEqualTo(363)
    }

    private val sampleInput = """
        Sabqponm
        abcryxxl
        accszExk
        acctuvwj
        abdefghi
    """.trimIndent()

}