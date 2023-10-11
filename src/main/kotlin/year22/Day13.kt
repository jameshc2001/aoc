package year22

import year22.Day13.PacketValue.Integer
import year22.Day13.PacketValue.PacketList

class Day13 {

    sealed class PacketValue {
        data class Integer(val value: Int): PacketValue()
        data class PacketList(val list: MutableList<PacketValue>): PacketValue() {
            constructor(vararg values: PacketValue): this(values.toMutableList())
            fun add(value: PacketValue) = list.add(value)
        }
    }

    companion object {
        fun parseList(input: String): PacketList {
            var currentList = PacketList()
            val stack = ArrayDeque<PacketList>()
            val trimmed = input.drop(1).dropLast(1)

            trimmed.forEachIndexed { index, c ->
                when {
                    c == '[' -> {
                        stack.addLast(currentList)
                        currentList = PacketList()
                    }
                    c == ']' -> {
                        val newList = currentList
                        currentList = stack.removeLast()
                        currentList.add(newList)
                    }
                    c.isDigit() && (index == 0 || (trimmed[index - 1] in listOf('[', ','))) -> {
                        currentList.add(getInteger(index, trimmed))
                    }
                }
            }

            return currentList
        }

        private fun getInteger(index: Int, input: String): Integer {
            var end = index
            while (input[end].isDigit()) end++
            return Integer(input.substring(index, end).toInt())
        }
    }

}