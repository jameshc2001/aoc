package year22

class Day1 {

    @JvmInline
    value class Calories(val value: Int)

    companion object {
        fun parseCalories(input: String): List<Calories> = input
            .replace("\r", "")
            .split("\n\n")
            .map { elf -> elf.lines().sumOf { it.toInt() } }
            .map { Calories(it) }

        fun findHighestCalories(input: String): Calories = parseCalories(input).maxBy { it.value }

        fun totalOfTopThree(input: String): Calories = Calories(
            parseCalories(input)
                .sortedByDescending { it.value }
                .take(3)
                .sumOf { it.value }
            )
    }


}