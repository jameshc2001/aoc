package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year23.Day11.Pos

class Day11Test {

    @Test
    fun `can parse sample input`() {
        val galaxies = Day11.parseInput(sampleInput)
        assertThat(galaxies).hasSize(9)
        assertThat(galaxies).contains(Pos(3, 0))
        assertThat(galaxies).contains(Pos(4, 9))
    }

    @Test
    fun `can expand galaxies`() {
        val galaxies = Day11.parseInput(sampleInput)
        val expandedGalaxies = Day11.expandGalaxies(galaxies, 2)
        val sampleExpandedGalaxies = Day11.parseInput(sampleInputExpanded)
        assertThat(expandedGalaxies).isEqualTo(sampleExpandedGalaxies)
    }

    @Test
    fun `can calculate distances between expanded galaxies`() {
        val galaxies = Day11.parseInput(sampleInput)
        val expandedGalaxies = Day11.expandGalaxies(galaxies, 2)

        val one = Pos(4, 0)
        val seven = Pos(9, 10)
        assertThat(expandedGalaxies.contains(one))
        assertThat(expandedGalaxies.contains(seven))
        assertThat(one.distance(seven)).isEqualTo(15)

        val three = Pos(0, 2)
        val six = Pos(12, 7)
        assertThat(expandedGalaxies.contains(three))
        assertThat(expandedGalaxies.contains(six))
        assertThat(three.distance(six)).isEqualTo(17)
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day11.sumOfDistances(sampleInput, 2)).isEqualTo(374)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day11::class.java.getResourceAsStream("/year23/day11.txt")!!.bufferedReader().readText()
        assertThat(Day11.sumOfDistances(input, 2)).isEqualTo(9509330)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day11.sumOfDistances(sampleInput, 10)).isEqualTo(1030)
        assertThat(Day11.sumOfDistances(sampleInput, 100)).isEqualTo(8410)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day11::class.java.getResourceAsStream("/year23/day11.txt")!!.bufferedReader().readText()
        assertThat(Day11.sumOfDistances(input, 1000000)).isEqualTo(635832237682)
    }

    private val sampleInput = """
        ...#......
        .......#..
        #.........
        ..........
        ......#...
        .#........
        .........#
        ..........
        .......#..
        #...#.....
    """.trimIndent()

    private val sampleInputExpanded = """
        ....1........
        .........2...
        3............
        .............
        .............
        ........4....
        .5...........
        ............6
        .............
        .............
        .........7...
        8....9.......
    """.trimIndent()

}
