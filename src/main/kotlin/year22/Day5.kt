package year22

class Day5 {

    data class SupplyStacks(val stacks: Map<Int, ArrayDeque<Char>>) {
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
                    .forEach { line ->
                        stackToIndex.forEach { (stack, index) ->
                            val maybeCrate = line.getOrNull(index)
                            if (maybeCrate?.isLetter() == true) {
                                supplyStack.stacks[stack]!!.add(maybeCrate)
                            }
                        }
                    }

                return supplyStack
            }
        }
    }

}