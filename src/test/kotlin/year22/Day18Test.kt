package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day18Test {

    @Test
    fun `can parse input`() {
        val input = Day18.parseInput(sampleInput)
        assertThat(input).contains(Day18.Pos(2, 1, 5))
        assertThat(input).hasSize(13)
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day18.surfaceArea(sampleInput)).isEqualTo(64)
    }


    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day18::class.java.getResourceAsStream("/year22/day18.txt")!!.bufferedReader().readText()
        assertThat(Day18.surfaceArea(input)).isEqualTo(3542)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day18.exteriorSurfaceArea(sampleInput)).isEqualTo(58)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day18::class.java.getResourceAsStream("/year22/day18.txt")!!.bufferedReader().readText()
        assertThat(Day18.exteriorSurfaceArea(input)).isEqualTo(2080)
    }

    private val sampleInput = """
        2,2,2
        1,2,2
        3,2,2
        2,1,2
        2,3,2
        2,2,1
        2,2,3
        2,2,4
        2,2,6
        1,2,5
        3,2,5
        2,1,5
        2,3,5
    """.trimIndent()

}