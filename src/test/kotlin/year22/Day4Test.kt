package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year22.Day4.AssignmentPair

class Day4Test {

    @Test
    fun `can parse assignment pair`() {
        val assignmentPair = AssignmentPair.fromString("2-4,6-8")
        val expectedAssignmentPair = AssignmentPair(
            left = 2..4,
            right = 6..8
        )
        assertThat(assignmentPair).isEqualTo(expectedAssignmentPair)
    }

    @Test
    fun `can parse assignment for double digits`() {
        val assignmentPair = AssignmentPair.fromString("20-42,61-82")
        val expectedAssignmentPair = AssignmentPair(
            left = 20..42,
            right = 61..82
        )
        assertThat(assignmentPair).isEqualTo(expectedAssignmentPair)
    }

    @Test
    fun `can determine if one assignment fully contains another in an assignment pair`() {
        assertThat(AssignmentPair.fromString("2-4,6-8").oneContainsAnother()).isFalse()
        assertThat(AssignmentPair.fromString("6-6,4-6").oneContainsAnother()).isTrue()
        assertThat(AssignmentPair.fromString("4-6,6-6").oneContainsAnother()).isTrue()
        assertThat(AssignmentPair.fromString("6-6,6-6").oneContainsAnother()).isTrue()
    }

    @Test
    fun `can find answer for part 1 using sample input`() {
        assertThat(Day4.numberOfPairsWhereOneContainsAnother(sampleInput)).isEqualTo(2)
    }

    @Test
    fun `can find answer for part 1 using question input`() {
        val input = Day4::class.java.getResourceAsStream("/year22/day4.txt")!!.bufferedReader().readText()
        assertThat(Day4.numberOfPairsWhereOneContainsAnother(input)).isEqualTo(448)
    }

    @Test
    fun `can determine if one assignments overlap for an assignment pair`() {
        assertThat(AssignmentPair.fromString("2-4,6-8").overlap()).isFalse()
        assertThat(AssignmentPair.fromString("6-8,2-4").overlap()).isFalse()
        assertThat(AssignmentPair.fromString("6-7,4-6").overlap()).isTrue()
        assertThat(AssignmentPair.fromString("4-6,6-7").overlap()).isTrue()
        assertThat(AssignmentPair.fromString("6-6,6-6").overlap()).isTrue()
        assertThat(AssignmentPair.fromString("6-9,7-8").overlap()).isTrue()
    }

    @Test
    fun `can find answer for part 2 using sample input`() {
        assertThat(Day4.numberOfPairsWithOverlap(sampleInput)).isEqualTo(4)
    }

    @Test
    fun `can find answer for part 2 using question input`() {
        val input = Day4::class.java.getResourceAsStream("/year22/day4.txt")!!.bufferedReader().readText()
        assertThat(Day4.numberOfPairsWithOverlap(input)).isEqualTo(794)
    }

    private val sampleInput = """
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
    """.trimIndent()
}