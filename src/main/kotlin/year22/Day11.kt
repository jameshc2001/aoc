package year22

import year22.Day11.Monkey.MonkeyId
import kotlin.math.floor

@Suppress("EqualsOrHashCode")
class Day11 {

    class Monkey(
        val id: MonkeyId,
        startingItems: List<Long>,
        private val operation: (Long) -> Long,
        val testDivider: Long,
        private val successThrowTo: MonkeyId,
        private val failureThrowTo: MonkeyId,
    ) {
        @JvmInline
        value class MonkeyId(val value: Int)

        private val items = ArrayDeque(startingItems)
        fun currentItems() = items.toList()

        private var totalInspectedItems = 0
        fun getTotalInspectedItems() = totalInspectedItems

        override fun equals(other: Any?): Boolean {
            if (other !is Monkey) return false
            return id == other.id &&
                operation(1) == other.operation(1) &&
                testDivider == other.testDivider &&
                successThrowTo == other.successThrowTo &&
                failureThrowTo == other.failureThrowTo &&
                items.toList() == other.items.toList()
        }

        private fun catch(item: Long) = items.addLast(item)

        fun inspectAndThrowItems(monkeys: Map<MonkeyId, Monkey>, worryModulus: Long? = null) {
            while (items.isNotEmpty()) {
                val itemWorry = items.removeFirst()
                val inspectionWorry = operation(itemWorry)
                val reliefWorry = worryModulus?.let { inspectionWorry % it }
                    ?: floor(inspectionWorry.toFloat() / 3f).toLong()

                val catchingMonkey = if (reliefWorry % testDivider == 0L) monkeys[successThrowTo]
                else monkeys[failureThrowTo]

                catchingMonkey!!.catch(reliefWorry)
                totalInspectedItems++
            }
        }

        companion object {
            fun fromString(input: String): Monkey {
                val lines = input
                    .lines()
                    .map { it.replace("\n", "") }

                return Monkey(
                    id = MonkeyId(lines[0].split(" ").last().dropLast(1).toInt()),
                    startingItems = getStartingItems(lines[1]),
                    operation = getOperation(lines[2]),
                    testDivider = lines[3].split(" ").last().toLong(),
                    successThrowTo = MonkeyId(lines[4].split(" ").last().toInt()),
                    failureThrowTo = MonkeyId(lines[5].split(" ").last().toInt())
                )
            }

            private fun getOperation(line: String): (Long) -> Long {
                if (line.contains("old * old")) return { old: Long -> old * old}
                if (line.contains("old + old")) return { old: Long -> old + old}

                val operationNumber = line.split(" ").last().toLong()
                val operationOperator = line.split(" ").dropLast(1).last()
                return if (operationOperator == "*") { old: Long -> old * operationNumber }
                else { old: Long -> old + operationNumber }
            }

            private fun getStartingItems(line: String): List<Long> = line
                .substringAfter("Starting items: ")
                .split(", ")
                .map { it.toLong() }
        }
    }


    companion object {
        fun parseInput(input: String): Map<MonkeyId, Monkey> = input
            .replace("\r", "")
            .split("\n\n")
            .map { Monkey.fromString(it) }
            .associateBy { it.id }

        private fun Map<MonkeyId, Monkey>.performRounds(rounds: Int, worryModulus: Long?) = this
            .values
            .sortedBy { it.id.value }
            .also { sortedMonkeys ->
                repeat(rounds) { sortedMonkeys.forEach { it.inspectAndThrowItems(this, worryModulus) } }
            }

        private fun Map<MonkeyId, Monkey>.getWorryModulus(): Long =
            this.values.map { it.testDivider }.reduce { acc, l -> acc * l }

        fun monkeyBusinessAfter(rounds: Int, input: String, useWorryModulus: Boolean = false): Long {
            val monkeys = parseInput(input)
            val worryModulus = if (useWorryModulus) monkeys.getWorryModulus() else null
            monkeys.performRounds(rounds, worryModulus)
            return monkeys
                .values
                .asSequence()
                .map { it.getTotalInspectedItems() }
                .sortedDescending()
                .take(2)
                .map { it.toLong() }
                .reduce { acc, l -> acc * l }
        }
    }
}