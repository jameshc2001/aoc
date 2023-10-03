package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day6Test {

    @Test
    fun `can find first detected start-of-packet marker`() {
        assertThat(Day6.firstStartMarker("mjqjpqmgbljsphdztnvjfqwrcgsmlb")).isEqualTo(7)
        assertThat(Day6.firstStartMarker("bvwbjplbgvbhsrlpgdmjqwftvncz")).isEqualTo(5)
        assertThat(Day6.firstStartMarker("nppdvjthqldpwncqszvftbrmjlhg")).isEqualTo(6)
        assertThat(Day6.firstStartMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")).isEqualTo(10)
        assertThat(Day6.firstStartMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")).isEqualTo(11)
    }

    @Test
    fun `can find first start-of-packet marker in question input`() {
        val input = Day6::class.java.getResourceAsStream("/year22/day6.txt")!!.bufferedReader().readText()
        assertThat(Day6.firstStartMarker(input)).isEqualTo(1848)
    }

    @Test
    fun `can find first detected start-of-message marker`() {
        assertThat(Day6.firstMessageMarker("mjqjpqmgbljsphdztnvjfqwrcgsmlb")).isEqualTo(19)
        assertThat(Day6.firstMessageMarker("bvwbjplbgvbhsrlpgdmjqwftvncz")).isEqualTo(23)
        assertThat(Day6.firstMessageMarker("nppdvjthqldpwncqszvftbrmjlhg")).isEqualTo(23)
        assertThat(Day6.firstMessageMarker("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")).isEqualTo(29)
        assertThat(Day6.firstMessageMarker("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")).isEqualTo(26)
    }

    @Test
    fun `can find first start-of-message marker in question input`() {
        val input = Day6::class.java.getResourceAsStream("/year22/day6.txt")!!.bufferedReader().readText()
        assertThat(Day6.firstMessageMarker(input)).isEqualTo(2308)
    }
}
