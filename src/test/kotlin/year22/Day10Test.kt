package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day10Test{

    @Test
    fun `can emulate a noop command on the cpu`() {
        val cpu = Day10.CPU().noop()
        assertThat(cpu.getHistory()).isEqualTo(listOf(1))
    }

    @Test
    fun `can emulate an addx command on the cpu`() {
        val cpu = Day10.CPU().addx(2).addx(24).noop()
        assertThat(cpu.getHistory()).isEqualTo(listOf(1, 1, 3, 3, 27))
    }

    @Test
    fun `can emulate fake input on cpu`() {
        val input = """
            addx 12
            addx 1
            noop
            addx -35
            noop
            noop
        """.trimIndent()

        val cpu = Day10.runInputOnCPU(input)
        assertThat(cpu.getHistory()).isEqualTo(listOf(1, 1, 13, 13, 14, 14, 14, -21, -21))
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day10.sumOfSignalStrengths(sampleInput)).isEqualTo(13140)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day10::class.java.getResourceAsStream("/year22/day10.txt")!!.bufferedReader().readText()
        assertThat(Day10.sumOfSignalStrengths(input)).isEqualTo(14780)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        val answer = """
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....
        """.trimIndent()
        assertThat(Day10.renderInput(sampleInput)).isEqualTo(answer)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day10::class.java.getResourceAsStream("/year22/day10.txt")!!.bufferedReader().readText()
        val answer = """
            ####.#....###..#....####..##..####.#....
            #....#....#..#.#.......#.#..#....#.#....
            ###..#....#..#.#......#..#......#..#....
            #....#....###..#.....#...#.##..#...#....
            #....#....#....#....#....#..#.#....#....
            ####.####.#....####.####..###.####.####.
        """.trimIndent()
        assertThat(Day10.renderInput(input)).isEqualTo(answer)
        //ELPLZGZL
    }

    private val sampleInput = """
        addx 15
        addx -11
        addx 6
        addx -3
        addx 5
        addx -1
        addx -8
        addx 13
        addx 4
        noop
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx -35
        addx 1
        addx 24
        addx -19
        addx 1
        addx 16
        addx -11
        noop
        noop
        addx 21
        addx -15
        noop
        noop
        addx -3
        addx 9
        addx 1
        addx -3
        addx 8
        addx 1
        addx 5
        noop
        noop
        noop
        noop
        noop
        addx -36
        noop
        addx 1
        addx 7
        noop
        noop
        noop
        addx 2
        addx 6
        noop
        noop
        noop
        noop
        noop
        addx 1
        noop
        noop
        addx 7
        addx 1
        noop
        addx -13
        addx 13
        addx 7
        noop
        addx 1
        addx -33
        noop
        noop
        noop
        addx 2
        noop
        noop
        noop
        addx 8
        noop
        addx -1
        addx 2
        addx 1
        noop
        addx 17
        addx -9
        addx 1
        addx 1
        addx -3
        addx 11
        noop
        noop
        addx 1
        noop
        addx 1
        noop
        noop
        addx -13
        addx -19
        addx 1
        addx 3
        addx 26
        addx -30
        addx 12
        addx -1
        addx 3
        addx 1
        noop
        noop
        noop
        addx -9
        addx 18
        addx 1
        addx 2
        noop
        noop
        addx 9
        noop
        noop
        noop
        addx -1
        addx 2
        addx -37
        addx 1
        addx 3
        noop
        addx 15
        addx -21
        addx 22
        addx -6
        addx 1
        noop
        addx 2
        addx 1
        noop
        addx -10
        noop
        noop
        addx 20
        addx 1
        addx 2
        addx 2
        addx -6
        addx -11
        noop
        noop
        noop
    """.trimIndent()
}