package year23

class Day1 {
    companion object {
        fun sumOfCalibrationValues(input: String): Int {
            return input.lines()
                .map { line -> "${line.find { it.isDigit() }}${line.reversed().find { it.isDigit() }}" }
                .sumOf { it.toInt() }
        }

        fun sumOfCalibrationValuesWithWords(input: String): Int {
            return input.lines().sumOf { calibrationValueWithWords(it) }
        }

        private fun calibrationValueWithWords(line: String): Int {
            val numbers = line.mapIndexedNotNull { index, c ->
                if (c.isDigit()) c
                else {
                    val subString = line.substring(index)
                    when {
                        subString.startsWith("one") -> '1'
                        subString.startsWith("two") -> '2'
                        subString.startsWith("three") -> '3'
                        subString.startsWith("four") -> '4'
                        subString.startsWith("five") -> '5'
                        subString.startsWith("six") -> '6'
                        subString.startsWith("seven") -> '7'
                        subString.startsWith("eight") -> '8'
                        subString.startsWith("nine") -> '9'
                        else -> null
                    }
                }
            }
            return "${numbers.first()}${numbers.last()}".toInt()
        }
    }
}