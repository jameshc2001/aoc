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
    fun `can check row is valid`() {
        assertThat(Day12.rowIsValid(Row.fromString("? 1"))).isFalse()
        assertThat(Day12.rowIsValid(Row.fromString("# 1"))).isTrue()
        assertThat(Day12.rowIsValid(Row.fromString("#.#.### 1,1,3"))).isTrue()
        assertThat(Day12.rowIsValid(Row.fromString("#....######..#####. 1,6,5"))).isTrue()
        assertThat(Day12.rowIsValid(Row.fromString("#....######..#####. 1,60,5"))).isFalse()
        assertThat(Day12.rowIsValid(Row.fromString("#....###..#####. 1,6,5"))).isFalse()
    }

    @Test
    fun `can get number of possible valid configurations for a row`() {
        assertThat(Day12.validConfigurations(Row.fromString("????.######..#####. 1,6,5"))).isEqualTo(4)
        assertThat(Day12.validConfigurations(Row.fromString("?###???????? 3,2,1"))).isEqualTo(10)
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

    private val sampleInput = """
        ???.### 1,1,3
        .??..??...?##. 1,1,3
        ?#?#?#?#?#?#?#? 1,3,1,6
        ????.#...#... 4,1,1
        ????.######..#####. 1,6,5
        ?###???????? 3,2,1
    """.trimIndent()

}