package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year22.Day1.Calories

class Day1Test {

    @Test
    fun `can parse sample input to get a list of calories`() {
        val calories = Day1.parseCalories(sample)
        assertThat(calories).isEqualTo(
            listOf(
                Calories(6000),
                Calories(4000),
                Calories(11000),
                Calories(24000),
                Calories(10000)
            )
        )
    }

    @Test
    fun `can get total calories carried by elf with most calories`() {
        val highestCaloriesCarried = Day1.findHighestCalories(sample)
        assertThat(highestCaloriesCarried).isEqualTo(Calories(24000))
    }

    @Test
    fun `can get answer for part 1`() {
        val input = Day1::class.java.getResourceAsStream("/year22/day1.txt")!!.bufferedReader().readText()
        assertThat(Day1.findHighestCalories(input)).isEqualTo(Calories(64929))
    }

    @Test
    fun `can get total calories of top 3 elves`() {
        val totalOfTopThree = Day1.totalOfTopThree(sample)
        assertThat(totalOfTopThree).isEqualTo(Calories(45000))
    }

    @Test
    fun `can get answer for part 2`() {
        val input = Day1::class.java.getResourceAsStream("/year22/day1.txt")!!.bufferedReader().readText()
        assertThat(Day1.totalOfTopThree(input)).isEqualTo(Calories(193697))
    }

    private val sample = """
            1000
            2000
            3000

            4000

            5000
            6000

            7000
            8000
            9000

            10000
        """.trimIndent()

}