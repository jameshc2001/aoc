package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year22.Day8.Coordinate
import year22.Day8.*

class Day8Test {

    @Test
    fun `can parse basic example`() {
        val input = """
            30
            25
        """.trimIndent()
        val expectedTreeGrid = TreeGrid(
            mapOf(
                Coordinate(0, 0) to 3,
                Coordinate(1, 0) to 0,
                Coordinate(0, 1) to 2,
                Coordinate(1, 1) to 5,
            ),
            2,
            2
        )
        assertThat(TreeGrid.fromString(input)).isEqualTo(expectedTreeGrid)
    }

    @Test
    fun `can correctly determine if tree is visible`() {
        val treeGrid = TreeGrid.fromString(sampleInput)
        assertThat(treeGrid.treeIsVisible(Coordinate(0, 0))).isTrue()
        assertThat(treeGrid.treeIsVisible(Coordinate(1, 1))).isTrue()
        assertThat(treeGrid.treeIsVisible(Coordinate(2, 2))).isFalse()
        assertThat(treeGrid.treeIsVisible(Coordinate(3, 1))).isFalse()
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day8.totalVisibleTrees(sampleInput)).isEqualTo(21)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day8::class.java.getResourceAsStream("/year22/day8.txt")!!.bufferedReader().readText()
        assertThat(Day8.totalVisibleTrees(input)).isEqualTo(1776)
    }

    @Test
    fun `can get tree scenic score`() {
        val treeGrid = TreeGrid.fromString(sampleInput)
        assertThat(treeGrid.treeScenicScore(Coordinate(2, 1))).isEqualTo(4)
        assertThat(treeGrid.treeScenicScore(Coordinate(2, 3))).isEqualTo(8)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day8.bestScenicScore(sampleInput)).isEqualTo(8)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day8::class.java.getResourceAsStream("/year22/day8.txt")!!.bufferedReader().readText()
        assertThat(Day8.bestScenicScore(input)).isEqualTo(234416)
    }


    private val sampleInput = """
        30373
        25512
        65332
        33549
        35390
    """.trimIndent()

}