package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year23.Day22.Pos

class Day22Test {

    @Test
    fun `can parse the sample input`() {
        val bricks = Day22.parseInput(sampleInput)
        assertThat(bricks).hasSize(7)
        assertThat(bricks).contains(
            Day22.Brick(
                4,
                listOf(Pos(2, 0, 5), Pos(2, 1, 5), Pos(2, 2, 5))
            )
        )
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day22.removableBricks(sampleInput)).isEqualTo(5)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day22::class.java.getResourceAsStream("/year23/day22.txt")!!.bufferedReader().readText()
        assertThat(Day22.removableBricks(input)).isEqualTo(505)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day22.chainReactionSum(sampleInput)).isEqualTo(7)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day22::class.java.getResourceAsStream("/year23/day22.txt")!!.bufferedReader().readText()
        assertThat(Day22.chainReactionSum(input)).isEqualTo(71002)
    }

    private val sampleInput = """
        1,0,1~1,2,1
        0,0,2~2,0,2
        0,2,3~2,2,3
        0,0,4~0,2,4
        2,0,5~2,2,5
        0,1,6~2,1,6
        1,1,8~1,1,9
    """.trimIndent()

}