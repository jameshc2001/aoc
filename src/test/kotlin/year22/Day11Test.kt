package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year22.Day11.Monkey
import year22.Day11.Monkey.MonkeyId

class Day11Test {

    @Test
    fun `can parse one monkey`() {
        val input = """
            Monkey 0:
              Starting items: 79, 98
              Operation: new = old * 19
              Test: divisible by 23
                If true: throw to monkey 2
                If false: throw to monkey 3
        """.trimIndent()
        val monkey = Monkey.fromString(input)
        val expectedMonkey = Monkey(
            MonkeyId(0),
            listOf(79, 98),
            { it * 19 },
            23,
            MonkeyId(2),
            MonkeyId(3)
        )
        assertThat(monkey).isEqualTo(expectedMonkey)
    }

    @Test
    fun `can parse input to get monkey map`() {
        val monkeyMap = Day11.parseInput(sampleInput)
        assertThat(monkeyMap).hasSize(4)
    }

    @Test
    fun `monkey can inspect and throw all of its item`() {
        val monkeyMap = Day11.parseInput(sampleInput)
        val throwingMonkey = monkeyMap[MonkeyId(0)]!!
        val catchingMonkey = monkeyMap[MonkeyId(3)]!!

        throwingMonkey.inspectAndThrowItems(monkeyMap)

        assertThat(throwingMonkey.getTotalInspectedItems()).isEqualTo(2)
        assertThat(throwingMonkey.currentItems()).isEmpty()
        assertThat(catchingMonkey.currentItems()).isEqualTo(listOf(74L, 500L, 620L))
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day11.monkeyBusinessAfter(20, sampleInput)).isEqualTo(10605)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day11::class.java.getResourceAsStream("/year22/day11.txt")!!.bufferedReader().readText()
        assertThat(Day11.monkeyBusinessAfter(20, input)).isEqualTo(54253)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day11.monkeyBusinessAfter(10000, sampleInput, true)).isEqualTo(2713310158)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day11::class.java.getResourceAsStream("/year22/day11.txt")!!.bufferedReader().readText()
        assertThat(Day11.monkeyBusinessAfter(10000, input, true)).isEqualTo(13119526120)
    }

    private val sampleInput = """
        Monkey 0:
          Starting items: 79, 98
          Operation: new = old * 19
          Test: divisible by 23
            If true: throw to monkey 2
            If false: throw to monkey 3
        
        Monkey 1:
          Starting items: 54, 65, 75, 74
          Operation: new = old + 6
          Test: divisible by 19
            If true: throw to monkey 2
            If false: throw to monkey 0
        
        Monkey 2:
          Starting items: 79, 60, 97
          Operation: new = old * old
          Test: divisible by 13
            If true: throw to monkey 1
            If false: throw to monkey 3
        
        Monkey 3:
          Starting items: 74
          Operation: new = old + 3
          Test: divisible by 17
            If true: throw to monkey 0
            If false: throw to monkey 1
    """.trimIndent()

}