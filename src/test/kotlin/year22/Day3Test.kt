package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year22.Day3.Item
import year22.Day3.Rucksack

class Day3Test {

    @Test
    fun `can parse a line to get a rucksack`() {
        val input = "ttgJtRGJQctTZtZT"
        val expectedRucksack = Rucksack(
            firstCompartment = listOf('t', 't', 'g', 'J', 't', 'R', 'G', 'J').map { Item(it) },
            secondCompartment = listOf('Q', 'c', 't', 'T', 'Z', 't', 'Z', 'T').map { Item(it) }
        )
        assertThat(Rucksack.fromString(input)).isEqualTo(expectedRucksack)
    }

    @Test
    fun `can get items in both compartments from a rucksack`() {
        val rucksack = Rucksack.fromString("ttgJtRGJQctTZtZT")
        val expectedChars = setOf(Item('t'))
        assertThat(rucksack.itemsInBothCompartments()).isEqualTo(expectedChars)
    }

    @Test
    fun `can get priority for items`() {
        assertThat(Item('a').priority()).isEqualTo(1)
        assertThat(Item('A').priority()).isEqualTo(27)
        assertThat(Item('z').priority()).isEqualTo(26)
        assertThat(Item('Z').priority()).isEqualTo(52)
    }

    @Test
    fun `can get part 1 answer for sample input`() {
        assertThat(Day3.sumOfItemPrioritiesInBothCompartments(sampleInput)).isEqualTo(157)
    }

    @Test
    fun `can get part 1 answer for question input`() {
        val input = Day3::class.java.getResourceAsStream("/year22/day3.txt")!!.bufferedReader().readText()
        assertThat(Day3.sumOfItemPrioritiesInBothCompartments(input)).isEqualTo(7766)
    }

    @Test
    fun `can get common item type between multiple rucksacks`() {
        val rucksacks = sampleInput.lines().map { Rucksack.fromString(it) }
        val group1 = rucksacks.take(3)
        val group2 = rucksacks.drop(3)
        assertThat(Day3.commonItem(group1)).isEqualTo(Item('r'))
        assertThat(Day3.commonItem(group2)).isEqualTo(Item('Z'))
    }

    @Test
    fun `can get part 2 answer for sample input`() {
        assertThat(Day3.sumOfItemPrioritiesOfBadges(sampleInput)).isEqualTo(70)
    }

    @Test
    fun `can get part 2 answer for question input`() {
        val input = Day3::class.java.getResourceAsStream("/year22/day3.txt")!!.bufferedReader().readText()
        assertThat(Day3.sumOfItemPrioritiesOfBadges(input)).isEqualTo(2415)
    }

    private val sampleInput = """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
        """.trimIndent()
}