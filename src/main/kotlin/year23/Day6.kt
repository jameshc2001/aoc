package year23

class Day6 {

    data class Race(val time: Int, val distance: Int)

    companion object {

        fun parseInput(input: String): List<Race> {
            val lines = input.lines()
            val regex = "[0-9]+".toRegex()
            val times = regex.findAll(lines[0]).map { it.value.toInt() }
            val distances = regex.findAll(lines[1]).map { it.value.toInt() }
            return times.zip(distances).map { Race(it.first, it.second) }.toList()
        }

        fun waysToWin(input: String): Int {
            val races = parseInput(input)
            val thing = races.map { i }
        }


    }

}