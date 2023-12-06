package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day3Test{

    @Test
    fun `can parse sample input`() {
        val schematic = Day3.parseInput(sampleInput)
        assertThat(schematic).isNotEmpty()
        assertThat(schematic).containsEntry(Day3.Pos(3, 1), '*')
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day3.sumOfPartNumbers(sampleInput)).isEqualTo(4361)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day3::class.java.getResourceAsStream("/year23/day3.txt")!!.bufferedReader().readText()
        assertThat(Day3.sumOfPartNumbers(input)).isEqualTo(517021)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day3.sumOfGearRatios(sampleInput)).isEqualTo(467835)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day3::class.java.getResourceAsStream("/year23/day3.txt")!!.bufferedReader().readText()
        assertThat(Day3.sumOfGearRatios(input)).isEqualTo(81296995)
    }

    private val sampleInput = """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...${'$'}.*....
        .664.598..
    """.trimIndent()

}