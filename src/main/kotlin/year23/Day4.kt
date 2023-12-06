package year23

import kotlin.math.pow

class Day4 {
    data class Card(val id: Int, val myNumbers: Set<Int>, val winningNumbers: Set<Int>)

    companion object {
        fun parseInput(input: String)= input.lines()
            .map { it.replace("\n", "") }
            .map { it.replace("\r", "") }
            .map { line ->
                val id = "[0-9]+".toRegex().find(line.substringBefore(':'))!!.value.toInt()
                val myNumbers = line.substringAfter(':').substringBefore('|').split(' ').mapNotNull { it.toIntOrNull() }.toSet()
                val winningNumbers = line.substringAfter('|').split(' ').mapNotNull { it.toIntOrNull() }.toSet()
                Card(id, myNumbers, winningNumbers)
            }

        fun totalPoints(input: String) = parseInput(input).sumOf { card ->
            val totalWins = (card.winningNumbers intersect card.myNumbers).size
            if (totalWins == 0) 0
            else 2.0.pow((totalWins - 1).toDouble()).toInt()
        }
    }

}