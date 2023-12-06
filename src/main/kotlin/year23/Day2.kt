package year23

class Day2 {

    data class Amounts(val red: Int, val green: Int, val blue: Int)

    data class Game(val id: Int, val reveals: List<Amounts>) {
        fun possibleWithConfiguration(configuration: Amounts): Boolean {
            reveals.forEach { reveal ->
                if (reveal.red > configuration.red) return false
                if (reveal.green > configuration.green) return false
                if (reveal.blue > configuration.blue) return false
            }
            return true
        }

        fun powerOfMinimumSet(): Int {
            val red = reveals.maxOf { it.red }
            val green = reveals.maxOf { it.green }
            val blue = reveals.maxOf { it.blue }
            return red * green * blue
        }
    }

    companion object {
        fun parseInput(input: String) = input.lines()
            .map { it.replace("\n", "") }
            .map { it.replace("\r", "") }
            .map { parseInputLine(it) }

        private fun parseInputLine(line: String): Game {
            val idSection = line.substringBefore(':')
            val revealSections = line.substringAfter(':').split(';')

            val id = idSection.substringAfter(' ').toInt()
            val reveals = revealSections.map { reveal ->
                val red = findNumberForColour(reveal, "red")
                val green = findNumberForColour(reveal, "green")
                val blue = findNumberForColour(reveal, "blue")
                Amounts(red, green, blue)
            }

            return Game(id, reveals)
        }

        private fun findNumberForColour(reveal: String, colour: String) =
            "[0-9]+\\s$colour".toRegex().find(reveal)?.value?.substringBefore(' ')?.toInt() ?: 0

        fun possibleGames(input: String, configuration: Amounts) = parseInput(input)
            .filter { it.possibleWithConfiguration(configuration) }
            .sumOf { it.id }

        fun minimumSetPowers(input: String) = parseInput(input)
            .sumOf { it.powerOfMinimumSet() }
    }


}