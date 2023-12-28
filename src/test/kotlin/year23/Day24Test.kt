package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day24Test {

    @Test
    fun `can parse the sample input`() {
        val hailstones = Day24.parseInput(sampleInput)
        assertThat(hailstones).hasSize(5)
        assertThat(hailstones).contains(Day24.Hailstone(
            Day24.Pos(20.0, 25.0, 34.0), Day24.Pos(-2.0, -2.0, -4.0)
        ))
    }

    @Test
    fun `can detect parallel lines in x and y axis`() {
        val hailstones = Day24.parseInput(sampleInput)
        assertThat(Day24.parallelXY(hailstones[1], hailstones[2])).isTrue()
        assertThat(Day24.parallelXY(hailstones[1], hailstones[3])).isFalse()
    }

    @Test
    fun `can determine time of intersection`() {
        val hailstones = Day24.parseInput(sampleInput)
        assertThat(Day24.timeOfIntersection(hailstones[1], hailstones[2])).isNull()
        assertThat(Day24.timeOfIntersection(hailstones[0], hailstones[1])).isEqualTo(2.333)
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day24.intersectionsIn(sampleInput, 7.0, 27.0)).isEqualTo(2)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day24::class.java.getResourceAsStream("/year23/day24.txt")!!.bufferedReader().readText()
        assertThat(Day24.intersectionsIn(input, 200000000000000.0, 400000000000000.0)).isEqualTo(16665)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day24.startingSum(sampleInput)).isEqualTo(47)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day24::class.java.getResourceAsStream("/year23/day24.txt")!!.bufferedReader().readText()
        assertThat(Day24.startingSum(input)).isEqualTo(769840447420960)
    }

    private val sampleInput = """
        19, 13, 30 @ -2,  1, -2
        18, 19, 22 @ -1, -1, -2
        20, 25, 34 @ -2, -2, -4
        12, 31, 28 @ -1, -2, -1
        20, 19, 15 @  1, -5, -3
    """.trimIndent()

}