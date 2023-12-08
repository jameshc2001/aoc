package year23

import year23.Day7.Hand.HandType.*

class Day7 {
    data class Card(val char: Char) {
        init { require(char in listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')) }

        fun value(jokerRule: Boolean = false) = if (char.isDigit()) char.digitToInt()
        else when (char) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> if (jokerRule) 1 else 11
            'T' -> 10
            else -> throw RuntimeException("unknown card $char")
        }
    }

    data class Hand(val cards: List<Card>, val jokerRule: Boolean = false) : Comparable<Hand> {
        init { require(cards.size == 5) }

        enum class HandType {
            FiveOfAKind, FourOfAKind, FullHouse, ThreeOfAKind, TwoPair, OnePair, HighCard
        }

        private fun handType(): HandType {
            val amounts = cards.groupingBy { it }.eachCount().values
            return when {
                5 in amounts -> FiveOfAKind
                4 in amounts -> FourOfAKind
                3 in amounts && 2 in amounts -> FullHouse
                3 in amounts -> ThreeOfAKind
                amounts.count { it == 2 } == 2 -> TwoPair
                2 in amounts -> OnePair
                else -> HighCard
            }
        }

        private fun handTypeWithJokerRule(): HandType {
            val handType = handType()
            if (Card('J') !in cards) return handType

            val cardToAmounts = cards.groupingBy { it }.eachCount()
            return when(handType) {
                FiveOfAKind -> FiveOfAKind
                FourOfAKind -> FiveOfAKind
                FullHouse -> FiveOfAKind
                ThreeOfAKind -> FourOfAKind
                TwoPair -> if (cardToAmounts[Card('J')] == 2) FourOfAKind else FullHouse
                OnePair -> ThreeOfAKind
                HighCard -> OnePair
            }
        }

        override fun compareTo(other: Hand): Int {
            val handTypeCompareResult = if (jokerRule) {
                this.handTypeWithJokerRule().compareTo(other.handTypeWithJokerRule())
            } else {
                this.handType().compareTo(other.handType())
            }
            if (handTypeCompareResult != 0) return -handTypeCompareResult

            this.cards.zip(other.cards).forEach {
                val cardCompareResult = it.first.value(jokerRule).compareTo(it.second.value(jokerRule))
                if (cardCompareResult != 0) return cardCompareResult
            }

            return 0
        }
    }

    data class HandAndBid(val hand: Hand, val bid: Int)

    companion object {
        fun parseInput(input: String, jokerRule: Boolean = false) = input.lines()
            .map { it.split(" ") }
            .map { (hand, bid) ->
                HandAndBid(
                    Hand(hand.map { Card(it) }, jokerRule),
                    bid.toInt()
                )
            }

        fun totalWinnings(input: String, jokerRule: Boolean = false) = parseInput(input, jokerRule)
            .sortedBy { it.hand }
            .mapIndexed { index, handAndBid -> (index + 1) * handAndBid.bid }
            .sum()
    }
}
