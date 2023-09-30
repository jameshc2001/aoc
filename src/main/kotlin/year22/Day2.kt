package year22

import year22.Day2.Companion.Move.entries

class Day2 {
    companion object {

        enum class Move {
            ROCK, PAPER, SCISSORS;

            fun score(): Int = this.ordinal + 1

            companion object {
                fun fromString(value: String) = when (value) {
                    "A", "X" -> ROCK
                    "B", "Y" -> PAPER
                    "C", "Z" -> SCISSORS
                    else -> throw RuntimeException("$value does not correspond to any move")
                }
            }
        }

        enum class Outcome {
            LOSE, DRAW, WIN;

            companion object {
                fun fromString(value: String): Outcome = when (value) {
                    "X" -> LOSE
                    "Y" -> DRAW
                    "Z" -> WIN
                    else -> throw RuntimeException("$value does not correspond to any outcome")
                }
            }
        }

        fun calculateScoreForPart1(input: String): Int = input
            .lines()
            .map { it.split(" ") }.sumOf { (opponent, you) ->
                getYourScore(Move.fromString(opponent), Move.fromString(you))
            }

        fun getYourScore(opponentsMove: Move, yourMove: Move): Int {
            val moveScore = yourMove.score()
            val outcomeScore = getOutcomeScore(opponentsMove, yourMove)
            return moveScore + outcomeScore
        }

        private fun getOutcomeScore(opponentsMove: Move, yourMove: Move): Int = when {
            opponentsMove == yourMove -> 3
            (opponentsMove.ordinal + 1) % 3 == yourMove.ordinal -> 6
            else -> 0
        }

        fun getRequiredMove(opponentsMove: Move, outcome: Outcome): Move = when (outcome) {
            Outcome.DRAW -> opponentsMove
            Outcome.WIN -> entries[(opponentsMove.ordinal + 1) % 3]
            Outcome.LOSE -> entries[(opponentsMove.ordinal + 2) % 3]
        }

        fun calculateScoreForPart2(input: String): Int = input.lines()
            .map { it.split(" ") }
            .sumOf { (opponent, outcome) ->
                val opponentsMove = Move.fromString(opponent)
                getYourScore(opponentsMove, getRequiredMove(opponentsMove, Outcome.fromString(outcome)))
            }
    }

}