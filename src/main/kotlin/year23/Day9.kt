package year23

class Day9 {
    companion object {
        fun parseInput(input: String): List<List<Long>> {
            val regex = "-?[0-9]+".toRegex()
            return input
                .lines()
                .map { line -> regex.findAll(line) }
                .map { sequence -> sequence.toList().map { it.value.toLong() } }
        }

        fun nextValue(sequence: List<Long>): Long {
            if (sequence.toSet().singleOrNull() == 0L) return 0

            val differences = sequence.zipWithNext { a, b -> b - a }
            val nextDifference = nextValue(differences)
            return sequence.last() + nextDifference
        }

        fun previousValue(sequence: List<Long>) = nextValue(sequence.reversed())

        fun sumOfExtrapolatedValues(input: String) = parseInput(input).sumOf { nextValue(it) }
        fun sumOfExtrapolatedPreviousValues(input: String) = parseInput(input).sumOf { previousValue(it) }
    }


}