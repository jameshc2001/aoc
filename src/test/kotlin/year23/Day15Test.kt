package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day15Test {

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day15.verificationNumber(sampleInput)).isEqualTo(1320)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day15::class.java.getResourceAsStream("/year23/day15.txt")!!.bufferedReader().readText()
        assertThat(Day15.verificationNumber(input)).isEqualTo(517551)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day15.focusingPower(sampleInput)).isEqualTo(145)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day15::class.java.getResourceAsStream("/year23/day15.txt")!!.bufferedReader().readText()
        assertThat(Day15.focusingPower(input)).isEqualTo(286097)
    }

    private val sampleInput = """
        rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7
    """.trimIndent()

}