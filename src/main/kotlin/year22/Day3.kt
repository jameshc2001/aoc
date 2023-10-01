package year22

class Day3 {

    @JvmInline
    value class Item(private val value: Char) {
        fun priority(): Int {
            return if (this.value.isUpperCase()) this.value.code - 38
            else this.value.code - 96
        }
    }

    data class Rucksack(val firstCompartment: List<Item>, val secondCompartment: List<Item>) {
        fun itemsInBothCompartments(): Set<Item> = firstCompartment.toSet() intersect secondCompartment.toSet()

        fun allItemTypes(): Set<Item> = firstCompartment.toSet() union secondCompartment.toSet()

        companion object {
            fun fromString(input: String): Rucksack {
                val firstHalf = input.subSequence(0, input.length / 2)
                val secondHalf = input.subSequence(input.length / 2, input.length)
                return Rucksack(firstHalf.map { Item(it) }, secondHalf.map { Item(it) })
            }
        }
    }

    companion object {
        fun sumOfItemPrioritiesInBothCompartments(input: String): Int = input
            .lines()
            .map { Rucksack.fromString(it) }
            .flatMap { it.itemsInBothCompartments() }
            .sumOf { it.priority() }

        fun commonItem(rucksacks: List<Rucksack>): Item = rucksacks
            .map { it.allItemTypes() }
            .reduce { acc, rucksackItemTypes ->
                acc intersect rucksackItemTypes
            }.single()

        fun sumOfItemPrioritiesOfBadges(input: String): Int = input
            .lines()
            .map { Rucksack.fromString(it) }
            .chunked(3)
            .map { commonItem(it) }
            .sumOf { it.priority() }
    }
}