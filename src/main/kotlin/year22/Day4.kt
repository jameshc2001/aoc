package year22

class Day4 {
    data class AssignmentPair(val left: IntRange, val right: IntRange) {
        fun oneContainsAnother(): Boolean {
            val unionSize = (left.toSet() union right.toSet()).size
            return unionSize == left.count() || unionSize == right.count()
        }

        fun overlap(): Boolean = (left.toSet() intersect right.toSet()).isNotEmpty()

        companion object {
            fun fromString(input: String): AssignmentPair {
                val (left, right) = input.split(",")
                val (leftStart, leftEnd) = left.split("-").map { it.toInt() }
                val (rightStart, rightEnd) = right.split("-").map { it.toInt() }
                return AssignmentPair(
                    leftStart..leftEnd,
                    rightStart..rightEnd
                )
            }
        }
    }

    companion object {
        fun numberOfPairsWhereOneContainsAnother(input: String): Int = input
            .lines()
            .map { AssignmentPair.fromString(it) }
            .filter { it.oneContainsAnother() }
            .size

        fun numberOfPairsWithOverlap(input: String): Int = input
            .lines()
            .map { AssignmentPair.fromString(it) }
            .filter { it.overlap() }
            .size
    }
}