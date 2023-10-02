package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day5Test {

    @Test
    fun `can parse supply stacks`() {
        val supplyStacks = Day5.SupplyStacks.fromString(sampleInput.substringBefore("\n\n"))
        val expectedSupplyStacks = Day5.SupplyStacks(
            stacks = mapOf(
                1 to ArrayDeque(listOf('Z', 'N')),
                2 to ArrayDeque(listOf('M', 'C', 'D')),
                3 to ArrayDeque(listOf('P')),
            )
        )
        assertThat(supplyStacks).isEqualTo(expectedSupplyStacks)
    }

    @Test
    fun `can perform move on supply stacks`() {
        val supplyStacks = Day5.SupplyStacks.fromString(sampleInput.substringBefore("\n\n"))
        val expectedSupplyStacks = Day5.SupplyStacks(
            stacks = mapOf(
                1 to ArrayDeque(listOf('Z', 'N', 'D')),
                2 to ArrayDeque(listOf('M', 'C')),
                3 to ArrayDeque(listOf('P')),
            )
        )
        supplyStacks.performMove("move 1 from 2 to 1")
        assertThat(supplyStacks).isEqualTo(expectedSupplyStacks)
    }

    @Test
    fun `can get word formed from characters at top of each stack`() {
        val supplyStacks = Day5.SupplyStacks.fromString(sampleInput.substringBefore("\n\n"))
        assertThat(supplyStacks.topWord()).isEqualTo("NDP")
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day5.runMovesAndGetTopWord(sampleInput)).isEqualTo("CMZ")
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day5::class.java.getResourceAsStream("/year22/day5.txt")!!.bufferedReader().readText()
        assertThat(Day5.runMovesAndGetTopWord(input)).isEqualTo("LJSVLTWQM")
    }

    @Test
    fun `can perform grouped move on supply stacks`() {
        val supplyStacks = Day5.SupplyStacks(
            stacks = mapOf(
                1 to ArrayDeque(listOf('Z', 'N', 'D')),
                2 to ArrayDeque(listOf('M', 'C')),
                3 to ArrayDeque(listOf('P')),
            )
        )
        val expectedSupplyStacks = Day5.SupplyStacks(
            stacks = mapOf(
                1 to ArrayDeque(listOf()),
                2 to ArrayDeque(listOf('M', 'C')),
                3 to ArrayDeque(listOf('P', 'Z', 'N', 'D')),
            )
        )
        supplyStacks.performMove("move 3 from 1 to 3", true)
        assertThat(supplyStacks).isEqualTo(expectedSupplyStacks)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day5.runMovesAndGetTopWord(sampleInput, true)).isEqualTo("MCD")
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day5::class.java.getResourceAsStream("/year22/day5.txt")!!.bufferedReader().readText()
        assertThat(Day5.runMovesAndGetTopWord(input, true)).isEqualTo("BRQWDBBJM")
    }

    private val sampleInput = """
            [D]    
        [N] [C]    
        [Z] [M] [P]
         1   2   3 
        
        move 1 from 2 to 1
        move 3 from 1 to 3
        move 2 from 2 to 1
        move 1 from 1 to 2
    """.trimIndent()
}