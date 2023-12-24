package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day21Test {

    @Test
    fun `can parse the sample input`() {
        val (start, gardens) = Day21.parseInput(sampleInput)
        assertThat(start).isEqualTo(Day21.Pos(5, 5))
        assertThat(gardens).contains(Day21.Pos(10, 10))
        assertThat(gardens).contains(Day21.Pos(0, 0))
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day21.reachableGardens(sampleInput, 6)).isEqualTo(16)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day21::class.java.getResourceAsStream("/year23/day21.txt")!!.bufferedReader().readText()
        assertThat(Day21.reachableGardens(input, 64)).isEqualTo(3724)
    }

    @Test
    fun `can get wrap around reachable gardens`() {
        val (start, gardens) = Day21.parseInput(sampleInput)
        val size = gardens.maxOf { it.x } + 1
        assertThat(Day21.wrapAroundReachableGardens(start, gardens, size, 10)).isEqualTo(50)
        assertThat(Day21.wrapAroundReachableGardens(start, gardens, size, 50)).isEqualTo(1594)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day21::class.java.getResourceAsStream("/year23/day21.txt")!!.bufferedReader().readText()
        assertThat(Day21.infiniteReachableGardens(input, 26501365)).isEqualTo(620348631910321)
    }

    private val sampleInput = """
        ...........
        .....###.#.
        .###.##..#.
        ..#.#...#..
        ....#.#....
        .##..S####.
        .##..#...#.
        .......##..
        .##.#.####.
        .##..##.##.
        ...........
    """.trimIndent()

}