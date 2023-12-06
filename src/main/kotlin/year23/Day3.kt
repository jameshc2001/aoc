package year23

class Day3 {
    data class Pos(val x: Int, val y: Int) {
        fun surrounding() = listOf(
            Pos(x-1, y),
            Pos(x, y-1),
            Pos(x-1, y-1),
            Pos(x+1, y),
            Pos(x, y+1),
            Pos(x+1, y+1),
            Pos(x-1, y+1),
            Pos(x+1, y-1),
        )
    }

    data class NumberAtPosition(val number: Int, val positions: List<Pos>)

    companion object {
        fun parseInput(input: String) = input.lines()
            .map { it.replace("\n", "") }
            .map { it.replace("\r", "") }
            .flatMapIndexed { y, line ->
                line.mapIndexed { x, char ->
                    Pos(x, y) to char
                }
            }.toMap()

        fun sumOfPartNumbers(input: String): Int {
            val schematic = parseInput(input)
            val symbols = schematic.filterNot { it.value == '.' }.filterNot { it.value.isDigit() }

            return symbols.flatMap { (pos, _) ->
                pos.surrounding()
                    .filter { schematic[it]?.isDigit() == true }
                    .map { schematic.getNumberAtPosition(it) }
                    .toSet()
                    .map { it.number }
            }.sum()
        }

        private fun Map<Pos, Char>.getNumberAtPosition(pos: Pos): NumberAtPosition {
            var start = pos.x
            var end = pos.x
            var done = false

            do {
                if (this[Pos(start, pos.y)]?.isDigit() == true) start -= 1
                else if (this[Pos(end, pos.y)]?.isDigit() == true) end += 1
                else done = true
            } while (!done)

            val positions = (start + 1..< end).map { Pos(it, pos.y) }
            val number = positions.map { this[it] }.joinToString("").toInt()
            return NumberAtPosition(number, positions)
        }

        fun sumOfGearRatios(input: String): Int {
            val schematic = parseInput(input)
            val possibleGears = schematic.filter { it.value == '*' }

            return possibleGears.map { (pos, _) ->
                pos.surrounding()
                    .filter { schematic[it]?.isDigit() == true }
                    .map { schematic.getNumberAtPosition(it) }
                    .toSet()
            }
                .filter { it.size == 2 }
                .sumOf { it.first().number * it.last().number }
        }
    }


}