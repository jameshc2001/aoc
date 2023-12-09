package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day9Test {

    @Test
    fun `can parse sample input`() {
        val sequences = Day9.parseInput(sampleInput)
        assertThat(sequences).hasSize(3)
        assertThat(sequences).contains(listOf<Long>(10, 13, 16, 21, 30, 45))
    }

    @Test
    fun `can predict next value in sequence`() {
        val sequence = listOf<Long>(10, 13, 16, 21, 30, 45)
        assertThat(Day9.nextValue(sequence)).isEqualTo(68)
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day9.sumOfExtrapolatedValues(sampleInput)).isEqualTo(114)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day9::class.java.getResourceAsStream("/year23/day9.txt")!!.bufferedReader().readText()
        assertThat(Day9.sumOfExtrapolatedValues(input)).isEqualTo(1666172641)
    }

    @Test
    fun `can get previous value in a sequence`() {
        val sequence = listOf<Long>(10, 13, 16, 21, 30, 45)
        assertThat(Day9.previousValue(sequence)).isEqualTo(5)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day9.sumOfExtrapolatedPreviousValues(sampleInput)).isEqualTo(2)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day9::class.java.getResourceAsStream("/year23/day9.txt")!!.bufferedReader().readText()
        assertThat(Day9.sumOfExtrapolatedPreviousValues(input)).isEqualTo(933)
    }

    private val sampleInput = """
        0 3 6 9 12 15
        1 3 6 10 15 21
        10 13 16 21 30 45
    """.trimIndent()

}