package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year22.Day13.PacketValue.Integer
import year22.Day13.PacketValue.PacketList

class Day13Test {

    @Test
    fun `can parse a single list`() {
        val list = Day13.parseList("[1,[2,[3,[4,[5,6,7]]]],8,9,[10]]")
        val expected = PacketList(
            Integer(1),
            PacketList(
                Integer(2),
                PacketList(
                    Integer(3),
                    PacketList(
                        Integer(4),
                        PacketList(
                            Integer(5),
                            Integer(6),
                            Integer(7),
                        )
                    )
                )
            ),
            Integer(8),
            Integer(9),
            PacketList(
                Integer(10)
            )
        )
        assertThat(list).isEqualTo(expected)
    }
}