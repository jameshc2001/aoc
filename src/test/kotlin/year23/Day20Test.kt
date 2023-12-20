package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year23.Day20.Pulse

class Day20Test {

    @Test
    fun `can parse hard sample input`() {
        val configuration = Day20.parseInput(hardSampleInput)
        assertThat(configuration.modules).hasSize(7)
        assertThat(configuration.modules.first { it.name == "output" }).isNotNull()
        assertThat(configuration.modules.first { it.name == "button" }).isNotNull()
        assertThat(configuration.modules.first { it.name == "broadcaster" }).isNotNull()

        val aFlipFlop = configuration.modules.first { it.name == "a" }
        assertThat(aFlipFlop.outputs).hasSize(2)
        assertThat(aFlipFlop.outputs.first { it.name == "inv" }).isNotNull()
        assertThat(aFlipFlop.outputs.first { it.name == "con" }).isNotNull()

        val conConjunction = configuration.modules.first { it.name == "con" } as Day20.Conjunction
        assertThat(conConjunction.memory).hasSize(2)
        assertThat(conConjunction.memory).containsEntry("a", Pulse.Low)
        assertThat(conConjunction.memory).containsEntry("b", Pulse.Low)
    }

    @Test
    fun `flip flop works as expected`() {
        val flipFlop = Day20.FlipFlop("ff", mutableListOf(Day20.Terminate("terminator")))

        flipFlop.process(Pulse.High, "user")
        assertThat(flipFlop.pulsesSent.values.sum()).isEqualTo(0)

        flipFlop.process(Pulse.Low, "user")
        assertThat(flipFlop.pulsesSent[Pulse.High]).isEqualTo(1)
        assertThat(flipFlop.pulsesSent[Pulse.Low]).isEqualTo(0)

        flipFlop.process(Pulse.Low, "user")
        assertThat(flipFlop.pulsesSent[Pulse.High]).isEqualTo(1)
        assertThat(flipFlop.pulsesSent[Pulse.Low]).isEqualTo(1)
    }

    @Test
    fun `conjunction works as expected`() {
        val conjunction = Day20.Conjunction("c", mutableListOf(Day20.Terminate("terminator")), mutableMapOf(
            "a" to Pulse.Low, "b" to Pulse.Low
        ))
        assertThat(conjunction.pulsesSent.values.sum()).isEqualTo(0)

        conjunction.process(Pulse.Low, "a")
        assertThat(conjunction.pulsesSent[Pulse.High]).isEqualTo(1)
        assertThat(conjunction.pulsesSent[Pulse.Low]).isEqualTo(0)

        conjunction.process(Pulse.High, "b")
        assertThat(conjunction.pulsesSent[Pulse.High]).isEqualTo(2)
        assertThat(conjunction.pulsesSent[Pulse.Low]).isEqualTo(0)

        conjunction.process(Pulse.High, "a")
        assertThat(conjunction.pulsesSent[Pulse.High]).isEqualTo(2)
        assertThat(conjunction.pulsesSent[Pulse.Low]).isEqualTo(1)
    }

    @Test
    fun `can get answer for part 1 using easy sample input`() {
        assertThat(Day20.pulsesProductAfter(easySampleInput, 1000)).isEqualTo(32000000)
    }

    @Test
    fun `can get answer for part 1 using hard sample input`() {
        assertThat(Day20.pulsesProductAfter(hardSampleInput, 1000)).isEqualTo(11687500)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day20::class.java.getResourceAsStream("/year23/day20.txt")!!.bufferedReader().readText()
        assertThat(Day20.pulsesProductAfter(input, 1000)).isEqualTo(896998430)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day20::class.java.getResourceAsStream("/year23/day20.txt")!!.bufferedReader().readText()
        assertThat(Day20.pressesToActivateTerminator(input)).isEqualTo(896998430)
    }

    private val easySampleInput = """
        broadcaster -> a, b, c
        %a -> b
        %b -> c
        %c -> inv
        &inv -> a
    """.trimIndent()

    private val hardSampleInput = """
        broadcaster -> a
        %a -> inv, con
        &inv -> b
        %b -> con
        &con -> output
    """.trimIndent()

}