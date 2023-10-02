package year22

class Day5 {

    data class SupplyStacks(val stacks: Map<Int, ArrayDeque<Char>>) {
        fun performMove(move: String, grouped: Boolean = false) {
            val (amount, from, to) = "[0-9]+".toRegex().findAll(move).toList().map { it.value.toInt() }
            val cratesToMove = (0..<amount).mapNotNull { stacks[from]?.removeLast() }
            val orderedCrates = if (grouped) cratesToMove.reversed() else cratesToMove
            orderedCrates.forEach { stacks[to]?.addLast(it) }
        }

        fun topWord(): String  = stacks.values.map { it.last() }.joinToString("")

        companion object {
            fun fromString(input: String): SupplyStacks {
                val lines = input.lines()
                val lastLine = lines.last()

                val stackToIndex = lastLine
                    .mapIndexed { index, c -> c to index }
                    .filter { (c, _) -> c.isDigit() }
                    .associate { (stack, index) -> stack.digitToInt() to index }

                val supplyStack = SupplyStacks(stackToIndex.keys.associateWith { ArrayDeque() })
                lines
                    .dropLast(1)
                    .reversed()
                    .forEach { line ->
                        stackToIndex.forEach { (stack, index) ->
                            val maybeCrate = line.getOrNull(index)
                            if (maybeCrate?.isLetter() == true) {
                                supplyStack.stacks[stack]!!.addLast(maybeCrate)
                            }
                        }
                    }

                return supplyStack
            }
        }
    }

    companion object {
        fun runMovesAndGetTopWord(input: String, grouped: Boolean = false): String {
            val (supplyStacksStart, moves) = input.replace("\r", "").split("\n\n")
            val supplyStacks = SupplyStacks.fromString(supplyStacksStart)
            moves
                .lines()
                .forEach { supplyStacks.performMove(it, grouped) }
            return supplyStacks.topWord()
        }
    }

}