package year22

import kotlin.math.abs

class Day10 {
    class CPU {
        private var register = 1
        private val history = mutableListOf<Int>() //stores value of register DURING the cycle

        fun getHistory() = history.toList()

        fun noop(): CPU {
            history.add(register)
            return this
        }

        fun addx(x: Int): CPU {
            history.add(register)
            history.add(register)
            register += x
            return this
        }
    }

    companion object {
        fun runInputOnCPU(input: String): CPU {
            val cpu = CPU()
            input.lines()
                .forEach {
                    if (it.contains("noop")) cpu.noop()
                    else cpu.addx(it.split(" ").last().toInt())
                }
            return cpu
        }

        fun sumOfSignalStrengths(input: String): Int {
            val history = runInputOnCPU(input).getHistory()
            return (20..220 step 40).sumOf { it * history[it - 1] }
        }

        fun renderInput(input: String): String {
            val history = runInputOnCPU(input).getHistory()
            return history
                .chunked(40)
                .joinToString("\n") {
                    it.mapIndexed { pixel, register ->
                        if (abs(pixel - register) <= 1) '#'
                        else '.'
                    }.joinToString("")
                }
        }
    }
}