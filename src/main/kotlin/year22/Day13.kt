package year22

import year22.Day13.PacketValue.Integer
import year22.Day13.PacketValue.Packet
import kotlin.math.min

class Day13 {

    sealed class PacketValue {
        data class Integer(val value: Int): PacketValue()
        data class Packet(val list: MutableList<PacketValue>): PacketValue() {
            constructor(vararg values: PacketValue): this(values.toMutableList())
            fun add(value: PacketValue) = list.add(value)
        }
    }

    companion object {
        fun parsePacket(input: String): Packet {
            var currentList = Packet()
            val stack = ArrayDeque<Packet>()
            val trimmed = input.drop(1).dropLast(1)

            trimmed.forEachIndexed { index, c ->
                when {
                    c == '[' -> {
                        stack.addLast(currentList)
                        currentList = Packet()
                    }
                    c == ']' -> {
                        val newList = currentList
                        currentList = stack.removeLast()
                        currentList.add(newList)
                    }
                    c.isDigit() && (index == 0 || (trimmed[index - 1] in listOf('[', ','))) -> {
                        currentList.add(trimmed.getIntegerStartingAt(index))
                    }
                }
            }

            return currentList
        }

        private fun String.getIntegerStartingAt(index: Int): Integer {
            var end = index
            while (end < this.length && this[end].isDigit()) end++
            return Integer(substring(index, end).toInt())
        }

        fun packetsAreInOrder(left: Packet, right: Packet): Boolean = packetsAreInOrderRecursive(left, right)!!

        private fun packetsAreInOrderRecursive(left: Packet, right: Packet): Boolean? {
            val lList = left.list
            val rList = right.list
            val smallestLength = min(lList.size, rList.size)
            (0..<smallestLength).forEach { index ->
                val lValue = lList[index]
                val rValue = rList[index]
                when {
                    lValue is Integer && rValue is Integer -> {
                        if (lValue.value < rValue.value) return true
                        if (lValue.value > rValue.value) return false
                    }
                    lValue is Packet && rValue is Packet -> packetsAreInOrderRecursive(lValue, rValue)?.let { return it }
                    lValue is Packet && rValue is Integer -> packetsAreInOrderRecursive(lValue, Packet(rValue))?.let { return it }
                    lValue is Integer && rValue is Packet -> packetsAreInOrderRecursive(Packet(lValue), rValue)?.let { return it }
                }
            }
            if (lList.size < rList.size) return true
            if (lList.size > rList.size) return false
            return null
        }

        fun sumOfIndicesOfInOrderPackets(input: String): Int = input
            .replace("\r", "")
            .split("\n\n")
            .asSequence()
            .map { it.split("\n") }
            .map { (left, right) -> parsePacket(left) to parsePacket(right) }
            .mapIndexed { index, (left, right) -> index to packetsAreInOrder(left, right) }
            .filter { (_, inOrder) -> inOrder }
            .sumOf { (index, _) -> index + 1 }
    }

}