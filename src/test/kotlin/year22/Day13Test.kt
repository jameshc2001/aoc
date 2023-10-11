package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year22.Day13.PacketValue.Integer
import year22.Day13.PacketValue.Packet

class Day13Test {

    @Test
    fun `can parse a single packet`() {
        val list = Day13.parsePacket("[1,[2,[3,[4,[5,6,7]]]],8,9,[10]]")
        val expected = Packet(
            Integer(1),
            Packet(
                Integer(2),
                Packet(
                    Integer(3),
                    Packet(
                        Integer(4),
                        Packet(
                            Integer(5),
                            Integer(6),
                            Integer(7),
                        )
                    )
                )
            ),
            Integer(8),
            Integer(9),
            Packet(
                Integer(10)
            )
        )
        assertThat(list).isEqualTo(expected)
    }

    @Test
    fun `can compare simple packets`() {
        val left = Day13.parsePacket("[1,1,3,1,1]")
        val right = Day13.parsePacket("[1,1,5,1,1]")
        assertThat(Day13.packetsAreInOrder(left, right)).isTrue()
        assertThat(Day13.packetsAreInOrder(right, left)).isFalse()
    }

    @Test
    fun `can compare packets with sublists`() {
        val left = Day13.parsePacket("[[1],[2,3,4]]")
        val right = Day13.parsePacket("[[1],4]")
        assertThat(Day13.packetsAreInOrder(left, right)).isTrue()
        assertThat(Day13.packetsAreInOrder(right, left)).isFalse()
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day13.sumOfIndicesOfInOrderPackets(sampleInput)).isEqualTo(13)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day13::class.java.getResourceAsStream("/year22/day13.txt")!!.bufferedReader().readText()
        assertThat(Day13.sumOfIndicesOfInOrderPackets(input)).isEqualTo(4734)
    }

    private val sampleInput = """
        [1,1,3,1,1]
        [1,1,5,1,1]

        [[1],[2,3,4]]
        [[1],4]

        [9]
        [[8,7,6]]

        [[4,4],4,4]
        [[4,4],4,4,4]

        [7,7,7,7]
        [7,7,7]

        []
        [3]

        [[[]]]
        [[]]

        [1,[2,[3,[4,[5,6,7]]]],8,9]
        [1,[2,[3,[4,[5,6,0]]]],8,9]
    """.trimIndent()
}