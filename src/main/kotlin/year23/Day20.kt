package year23

class Day20 {

    enum class Pulse { Low, High }

    sealed class Module(val name: String, val outputs: MutableList<Module>) {
        val pulsesSent = mutableMapOf(Pulse.Low to 0L, Pulse.High to 0L)

        private val pulseQueue = ArrayDeque<Pair<Pulse, String>>()
        private fun addPulse(pulse: Pulse, sentBy: String) = pulseQueue.add(pulse to sentBy)

        abstract fun process(pulse: Pulse, sentBy: String)
        private fun processQueue() {
            while (pulseQueue.isNotEmpty()) {
                val (pulse, sentBy) = pulseQueue.removeFirst()
                process(pulse, sentBy)
            }
        }

        fun sendPulse(pulse: Pulse) {
            outputs.forEach { module ->
                pulsesSent[pulse] = pulsesSent[pulse]!! + 1
                module.addPulse(pulse, name)
            }
            outputs.forEach { it.processQueue() }
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

    class Terminate(name: String): Module(name, mutableListOf()) {
        var activated = false
        override fun process(pulse: Pulse, sentBy: String) { if (pulse == Pulse.Low) activated = true }
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
                }.associateBy { it.name }.toMutableMap()

                //setup terminating modules
                val terminators = namesToOutputNames.values.flatten().toSet() - namesToOutputNames.keys.toSet()
                terminators.forEach { terminatorName -> namesToModules[terminatorName] = Terminate(terminatorName) }

                //set outputs, terminators do not have outputs so filter them out
                namesToModules.values.filter { it.name !in terminators }.forEach { module ->
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

        private fun findLCM(a: Long, b: Long): Long {
            val larger = if (a > b) a else b
            val maxLcm = a * b
            var lcm = larger
            while (lcm <= maxLcm) {
                if (lcm % a == 0L && lcm % b == 0L) {
                    return lcm
                }
                lcm += larger
            }
            return maxLcm
        }

        fun pressesToActivateTerminator(input: String): Long {
            val configuration = parseInput(input)
            val terminator = configuration.modules.filterIsInstance<Terminate>().single()
            val conjunction = configuration.modules.single { terminator in it.outputs } as Conjunction
            val conjunctionFeeders = configuration.modules.filterIsInstance<Conjunction>().filter { conjunction in it.outputs }
            val cfToCycle = mutableMapOf<String, Long>()

            var presses = 0L
            while (cfToCycle.size != conjunctionFeeders.size) {
                configuration.pushButton()
                presses++
                conjunctionFeeders.forEach { cf ->
                    if (cf.pulsesSent[Pulse.High]!! > 0 && cfToCycle[cf.name] == null) {
                        cfToCycle[cf.name] = presses
                    }
                }
            }

            return cfToCycle.values.reduce { acc, l -> findLCM(acc, l) }
        }
    }


}