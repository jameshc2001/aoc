package year23

class Day15 {
    companion object {
        fun verificationNumber(input: String) = input
            .split(',')
            .sumOf { section ->
                section.fold(0) { acc, c -> ((acc + c.code) * 17) % 256 }.toInt()
            }

        fun focusingPower(input: String): Int {
            val instructions = input.split(',')
            val boxesToLenses = mutableMapOf<Int, MutableList<String>>()

            instructions.forEach { instruction ->
                val remove = instruction.contains('-')
                val labelAndFocalLength = instruction.replace("-", " ").replace("=", " ")
                val label = labelAndFocalLength.substringBefore(' ')
                val box = boxesToLenses.getOrPut(verificationNumber(label)) { mutableListOf() }

                if (remove) {
                    box.removeIf { it.substringBefore(' ') == label }
                } else {
                    val index = box.indexOfFirst { it.substringBefore(' ') == label }
                    if (index == -1) box.add(labelAndFocalLength)
                    else box[index] = labelAndFocalLength
                }
            }

            return boxesToLenses.map { (boxNumber, lenses) ->
                lenses.mapIndexed { lensNumber, labelAndFocalLength ->
                    val focalLength = labelAndFocalLength.substringAfter(' ').toInt()
                    (boxNumber + 1) * (lensNumber + 1) * focalLength
                }.sum()
            }.sum()
        }
    }
}