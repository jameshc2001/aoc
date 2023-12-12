package year23

import year23.Day12.Condition.*

class Day12 {

    enum class Condition {
        Operational, Damaged, Unknown;
        companion object {
            fun fromChar(c: Char) = when(c) {
                '?' -> Unknown
                '#' -> Damaged
                '.' -> Operational
                else -> throw RuntimeException("no condition for character $c")
            }
        }
    }

    data class Row(val conditions: List<Condition>, val groups: List<Int>) {
        companion object {
            fun fromString(row: String): Row {
                val (conditions, groups) = row.split(' ')
                return Row(
                    conditions.map { Condition.fromChar(it) },
                    groups.split(',').map { it.toInt() }
                )
            }
        }
    }

    companion object {
        fun parseInput(input: String) = input
            .lines()
            .map { it.replace("\n", "") }
            .map { it.replace("\r", "") }
            .map { line -> Row.fromString(line) }

        fun rowIsValid(row: Row): Boolean {
            if (Unknown in row.conditions) return false

            val groups = mutableListOf<Int>()
            var currentGroup = 0
            if (row.conditions.first() == Damaged) currentGroup = 1

            row.conditions.zipWithNext { previous, current ->
                if (current == Damaged) currentGroup += 1
                if (previous == Damaged && current == Operational) {
                    groups.add(currentGroup)
                    currentGroup = 0
                }
            }
            if (currentGroup != 0) groups.add(currentGroup)

            return groups == row.groups
        }

        fun validConfigurations(row: Row): Int {
            if (rowIsValid(row)) return 1
            if (Unknown !in row.conditions) return 0

            val indexOfFirstUnknown = row.conditions.indexOfFirst { it == Unknown }
            val mutableConditions = row.conditions.toMutableList()

            mutableConditions[indexOfFirstUnknown] = Operational
            val conditionsIfOperational = mutableConditions.toList()

            mutableConditions[indexOfFirstUnknown] = Damaged
            val conditionsIfDamaged = mutableConditions.toList()

            return validConfigurations(row.copy(conditions = conditionsIfDamaged)) +
                    validConfigurations(row.copy(conditions = conditionsIfOperational))
        }

        fun validConfigurationsForRows(input: String) = parseInput(input).sumOf { validConfigurations(it) }
    }


}