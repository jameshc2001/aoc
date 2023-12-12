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

        private val cache = mutableMapOf<Row, Long>()

        private fun fasterValidConfigurationsCached(conditions: List<Condition>, groups: List<Int>): Long {
            val row = Row(conditions, groups)
            cache[row]?.let { return it }
            val result = fasterValidConfigurations(conditions, groups)
            cache[row] = result
            return result
        }

        private fun fasterValidConfigurations(conditions: List<Condition>, groups: List<Int>): Long {
            if (conditions.isEmpty()) { //we reached the end
                return if (groups.isEmpty()) 1 else 0
            }

            val currentCondition = conditions.first()
            when (currentCondition) {
                Operational -> {
                    return fasterValidConfigurationsCached(conditions.drop(1), groups)
                }
                Unknown -> {
                    return fasterValidConfigurationsCached(listOf(Operational).plus(conditions.drop(1)), groups) +
                            fasterValidConfigurationsCached(listOf(Damaged).plus(conditions.drop(1)), groups)
                }
                Damaged -> {
                    if (groups.isEmpty()) return 0

                    val groupSize = groups.first()
                    val possibleGroupSection = conditions.take(groupSize) //returns list with size <= original size
                    val conditionAfterGroup = conditions.getOrNull(groupSize) //null if at end

                    if (possibleGroupSection.size < groupSize) return 0 //happens at end of conditions
                    if (possibleGroupSection.size == groupSize) {
                        val validGroup = Operational !in possibleGroupSection
                        if (!validGroup) return 0

                        //group must be valid
                        return when (conditionAfterGroup) {
                            Damaged -> 0
                            Operational, null -> {
                                fasterValidConfigurationsCached(conditions.drop(groupSize), groups.drop(1))
                            }
                            Unknown -> {
                                fasterValidConfigurationsCached( //+1 to replace next condition
                                    listOf(Operational).plus(conditions.drop(groupSize + 1)),
                                    groups.drop(1)
                                )
                            }
                        }
                    }
                }
            }

            throw RuntimeException("something went very wrong")
        }

        private fun expandRow(row: Row) = Row(
            conditions = row.conditions + Unknown + row.conditions + Unknown +
                    row.conditions + Unknown + row.conditions + Unknown + row.conditions,
            groups = row.groups + row.groups + row.groups + row.groups + row.groups
        )

        fun validConfigurationsForRows(input: String) = parseInput(input).sumOf { fasterValidConfigurationsCached(it.conditions, it.groups) }
        fun validConfigurationsForExpandedRows(input: String) = parseInput(input)
            .map { expandRow(it) }
            .sumOf { fasterValidConfigurationsCached(it.conditions, it.groups) }
    }
}