package year23

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

class Day6 {

    data class Race(val time: Long, val distance: Long) {
        fun waysToWin(): Long {
            val temp = sqrt(((time * time) - (4 * (distance + 1))).toDouble()) //+1 to distance because we want to beat it
            val lowest = ceil((time.toDouble() - temp) / 2).toLong()
            val highest = floor((time.toDouble() + temp) / 2).toLong()
            return highest - lowest + 1
        }
    }

    companion object {

        fun parseInput(input: String): List<Race> {
            val lines = input.lines()
            val regex = "[0-9]+".toRegex()
            val times = regex.findAll(lines[0]).map { it.value.toLong() }
            val distances = regex.findAll(lines[1]).map { it.value.toLong() }
            return times.zip(distances).map { Race(it.first, it.second) }.toList()
        }

        fun waysToWin(input: String): Long {
            val races = parseInput(input)
            return races.map { race -> race.waysToWin() }.reduce { acc, i -> acc * i }
        }

        fun waysToWinSingleRace(input: String): Long {
            val races = parseInput(input)
            val race = Race(
                races.joinToString("") { it.time.toString() }.toLong(),
                races.joinToString("") { it.distance.toString() }.toLong()
            )
            return race.waysToWin()
        }


    }

}