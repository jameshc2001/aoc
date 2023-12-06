package year23

import kotlin.math.pow

class Day4 {
    data class Card(val id: Int, val myNumbers: Set<Int>, val winningNumbers: Set<Int>) {
        fun totalWins() = (winningNumbers intersect myNumbers).size
    }

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
            val totalWins = card.totalWins()
            if (totalWins == 0) 0
            else 2.0.pow((totalWins - 1).toDouble()).toInt()
        }

        fun totalScratchcards(input: String): Int {
            val cards = parseInput(input)
            val amounts = mutableMapOf<Int, Int>() //id to amount
            cards.forEach { amounts[it.id] = 1 }

            cards.forEach { card ->
                val amount = amounts[card.id]!!
                val idOfCardsToAdd = (card.id + 1 .. card.id + card.totalWins())
                idOfCardsToAdd.forEach { idOfCardToAdd ->
                    amounts[idOfCardToAdd] = amounts[idOfCardToAdd]!! + amount
                }
            }

            return amounts.values.sum()
        }
    }

}