package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year23.Day7.*

class Day7Test {

    @Test
    fun `can parse sample input`() {
        val handsAndBids = Day7.parseInput(sampleInput)
        assertThat(handsAndBids).hasSize(5)
        assertThat(handsAndBids).contains(
            HandAndBid(
                Hand(listOf(Card('T'), Card('5'), Card('5'), Card('J'), Card('5'))),
                684
            )
        )
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day7.totalWinnings(sampleInput)).isEqualTo(6440)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day7::class.java.getResourceAsStream("/year23/day7.txt")!!.bufferedReader().readText()
        assertThat(Day7.totalWinnings(input)).isEqualTo(248569531)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day7.totalWinnings(sampleInput, true)).isEqualTo(5905)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day7::class.java.getResourceAsStream("/year23/day7.txt")!!.bufferedReader().readText()
        assertThat(Day7.totalWinnings(input, true)).isEqualTo(250382098)
    }

    private val sampleInput = """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
    """.trimIndent()

}