package year23

class Day20 {

    enum class Pulse { Low, High }

    sealed class Module(val name: String, val outputs: MutableList<Module>) {
        val pulsesSent = mutableMapOf(Pulse.Low to 0L, Pulse.High to 0L)

        abstract fun process(pulse: Pulse, sentBy: String)


        fun sendPulse(pulse: Pulse) {
            outputs.forEach {
                pulsesSent[pulse] = pulsesSent[pulse]!! + 1
                it.process(pulse, name)
            }
        }
    }

    class Button(outputs: MutableList<Module> = mutableListOf()): Module("button", outputs) {
        override fun process(pulse: Pulse, sentBy: String) {
            if (pulse == Pulse.High) throw RuntimeException("button can only send low pulses")
            sendPulse(pulse)
        }
    }

    class FlipFlop(name: String, outputs: MutableList<Module> = mutableListOf()): Module(name, outputs) {
        private var on = false //off
        override fun process(pulse: Pulse, sentBy: String) {
            val previouslyOn = on
            if (pulse == Pulse.Low) {
                on = !on
                if (previouslyOn) sendPulse(Pulse.Low)
                else sendPulse(Pulse.High)
            }
        }
    }

    class Conjunction(
        name: String,
        outputs: MutableList<Module> = mutableListOf(),
        val memory: MutableMap<String, Pulse> = mutableMapOf()
    ) : Module(name, outputs) {
        //memory must be set up correctly first
        override fun process(pulse: Pulse, sentBy: String) {
            memory[sentBy] = pulse
            if (memory.values.toSet().singleOrNull() == Pulse.High) sendPulse(Pulse.Low)
            else sendPulse(Pulse.High)
        }
    }

    class Broadcast(name: String, outputs: MutableList<Module> = mutableListOf()): Module(name, outputs) {
        override fun process(pulse: Pulse, sentBy: String) = sendPulse(pulse)
    }

    class Output: Module("output", mutableListOf()) {
        override fun process(pulse: Pulse, sentBy: String) {}
    }

    data class Configuration(val modules: List<Module>) {
        private val button: Module = modules.filterIsInstance<Button>().single() //throw exception > or < 1 button
        fun pushButton() = button.process(Pulse.Low, "user")

        companion object {
            fun fromString(input: String): Configuration {
                val lines = input.lines().map { it.replace("\r", "").replace("\n", "") }

                val namesToOutputNames = mutableMapOf<String, List<String>>()
                val namesToModules = lines.map { line ->
                    val name = line.substringBefore(" ->")
                    val nameWithoutType = name.let { if (it.first() != 'b') it.drop(1) else it }
                    val outputNames = line.substringAfter("-> ").split(", ")
                    namesToOutputNames[nameWithoutType] = outputNames
                    when(name.first()) {
                        'b' -> Broadcast(nameWithoutType)
                        '%' -> FlipFlop(nameWithoutType)
                        '&' -> Conjunction(nameWithoutType)
                        else -> throw RuntimeException("unknown module type")
                    }
                }.associateBy { it.name }
                    .plus("output" to Output())
                    .also { namesToOutputNames["output"] = emptyList() }

                //set outputs
                namesToModules.values.forEach { module ->
                    val outputs = namesToOutputNames[module.name]!!
                        .map { namesToModules[it]!! }
                    module.outputs.addAll(outputs)
                }

                //set conjunction memories
                namesToModules.values.filterIsInstance<Conjunction>().forEach { conjunction ->
                    val inputNames = namesToModules.values.filter { conjunction in it.outputs }.map { it.name }
                    inputNames.forEach { name -> conjunction.memory[name] = Pulse.Low }
                }

                val broadcaster = namesToModules.values.filterIsInstance<Broadcast>().single()
                val button = Button(mutableListOf(broadcaster))

                return Configuration(namesToModules.values.toList() + button)
            }
        }
    }

    companion object {
        fun parseInput(input: String) = Configuration.fromString(input)

        fun pulsesProductAfter(input: String, presses: Int): Long {
            val configuration = parseInput(input)
            repeat(presses) { configuration.pushButton() }
            var low = 0L
            var high = 0L
            configuration.modules.forEach { module ->
                low += module.pulsesSent[Pulse.Low]!!
                high += module.pulsesSent[Pulse.High]!!
            }
            return low * high
        }
    }


}