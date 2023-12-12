package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year23.Day12.Condition.*
import year23.Day12.Row

class Day12Test {

    @Test
    fun `can parse sample input`() {
        val rows = Day12.parseInput(sampleInput)
        assertThat(rows).hasSize(6)
        assertThat(rows).contains(
            Row(
                listOf(Unknown, Unknown, Unknown, Operational, Damaged, Damaged, Damaged),
                listOf(1, 1, 3)
            )
        )
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day12.validConfigurationsForRows(sampleInput)).isEqualTo(21)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day12::class.java.getResourceAsStream("/year23/day12.txt")!!.bufferedReader().readText()
        assertThat(Day12.validConfigurationsForRows(input)).isEqualTo(7916)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day12.validConfigurationsForExpandedRows(sampleInput)).isEqualTo(525152)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day12::class.java.getResourceAsStream("/year23/day12.txt")!!.bufferedReader().readText()
        assertThat(Day12.validConfigurationsForExpandedRows(input)).isEqualTo(37366887898686)
    }

    private val sampleInput = """
        ???.### 1,1,3
        .??..??...?##. 1,1,3
        ?#?#?#?#?#?#?#? 1,3,1,6
        ????.#...#... 4,1,1
        ????.######..#####. 1,6,5
        ?###???????? 3,2,1
    """.trimIndent()

}