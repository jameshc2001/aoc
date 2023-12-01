package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day1Test {

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day1.sumOfCalibrationValues(sampleInput)).isEqualTo(142)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day1::class.java.getResourceAsStream("/year23/day1.txt")!!.bufferedReader().readText()
        assertThat(Day1.sumOfCalibrationValues(input)).isEqualTo(53080)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day1.sumOfCalibrationValuesWithWords(sampleInput2)).isEqualTo(281)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day1::class.java.getResourceAsStream("/year23/day1.txt")!!.bufferedReader().readText()
        assertThat(Day1.sumOfCalibrationValuesWithWords(input)).isEqualTo(53268)
    }

    private val sampleInput = """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
    """.trimIndent()

    private val sampleInput2 = """
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen
    """.trimIndent()

}