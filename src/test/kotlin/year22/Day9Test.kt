package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year22.Day9.Companion.getTailsMove
import year22.Day9.Coordinate
import year22.Day9.Move.*

class Day9Test {

    @Test
    fun `can parse input into list of moves`() {
        val moves = Day9.parseInput(sampleInputPart1)
        val expectedMoves = listOf(
            RIGHT, RIGHT, RIGHT, RIGHT,
            UP, UP, UP, UP,
            LEFT, LEFT, LEFT,
            DOWN,
            RIGHT, RIGHT, RIGHT, RIGHT,
            DOWN,
            LEFT, LEFT, LEFT, LEFT, LEFT,
            RIGHT, RIGHT
        )
        assertThat(moves).isEqualTo(expectedMoves)
    }

    @Test
    fun `can determine correct move for tail given location of head`() {
        assertThat(getTailsMove(Coordinate(0, 2), Coordinate(0, 0))).isEqualTo(UP)
        assertThat(getTailsMove(Coordinate(2, 0), Coordinate(0, 0))).isEqualTo(RIGHT)
        assertThat(getTailsMove(Coordinate(0, -2), Coordinate(0, 0))).isEqualTo(DOWN)
        assertThat(getTailsMove(Coordinate(-2, 0), Coordinate(0, 0))).isEqualTo(LEFT)
        assertThat(getTailsMove(Coordinate(1, 2), Coordinate(0, 0))).isEqualTo(RIGHT_UP)
        assertThat(getTailsMove(Coordinate(1, -2), Coordinate(0, 0))).isEqualTo(RIGHT_DOWN)
        assertThat(getTailsMove(Coordinate(-1, 2), Coordinate(0, 0))).isEqualTo(LEFT_UP)
        assertThat(getTailsMove(Coordinate(-1, -2), Coordinate(0, 0))).isEqualTo(LEFT_DOWN)
        assertThat(getTailsMove(Coordinate(2, 2), Coordinate(0, 0))).isEqualTo(RIGHT_UP)

        assertThat(getTailsMove(Coordinate(0, 0), Coordinate(0, 0))).isEqualTo(STAY)
        assertThat(getTailsMove(Coordinate(1, 1), Coordinate(0, 0))).isEqualTo(STAY)
    }

    @Test
    fun `can apply move to coordinate to get new coordinate`() {
        assertThat(Coordinate(0, 0).move(RIGHT)).isEqualTo(Coordinate(1, 0))
        assertThat(Coordinate(0, 0).move(RIGHT_UP)).isEqualTo(Coordinate(1, 1))
        assertThat(Coordinate(0, 0).move(DOWN)).isEqualTo(Coordinate(0, -1))
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day9.uniqueTailPositions(2, sampleInputPart1)).isEqualTo(13)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day9::class.java.getResourceAsStream("/year22/day9.txt")!!.bufferedReader().readText()
        assertThat(Day9.uniqueTailPositions(2, input)).isEqualTo(6367)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day9.uniqueTailPositions(10, sampleInputPart2)).isEqualTo(36)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day9::class.java.getResourceAsStream("/year22/day9.txt")!!.bufferedReader().readText()
        assertThat(Day9.uniqueTailPositions(10, input)).isEqualTo(2536)
    }

    private val sampleInputPart1 = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent()

    private val sampleInputPart2 = """
        R 5
        U 8
        L 8
        D 3
        R 17
        D 10
        L 25
        U 20
    """.trimIndent()

}