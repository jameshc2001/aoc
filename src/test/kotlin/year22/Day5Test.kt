package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day5Test {

    @Test
    fun `can parse supply stacks`() {
        val supplyStacks = Day5.SupplyStacks.fromString(sampleInput.substringBefore("\n\n"))
        val expectedSupplyStacks = Day5.SupplyStacks(
            stacks = mapOf(
                1 to ArrayDeque(listOf('N', 'Z')),
                2 to ArrayDeque(listOf('D', 'C', 'M')),
                3 to ArrayDeque(listOf('P')),
            )
        )
        assertThat(supplyStacks).isEqualTo(expectedSupplyStacks)
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