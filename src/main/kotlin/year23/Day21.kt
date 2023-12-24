package year23

class Day21 {

    data class Pos(val x: Long, val y: Long) {
        fun adjacent() = listOf(
            Pos(x - 1, y),
            Pos(x + 1, y),
            Pos(x, y - 1),
            Pos(x, y + 1),
        )
        operator fun minus(other: Pos) = Pos(x - other.x, y - other.y)
    }

    companion object {
        fun parseInput(input: String): Pair<Pos, Set<Pos>> {
            var start: Pos? = null
            val garden = input.lines().map { it.replace("\n", "").replace("\r", "") }
                .flatMapIndexed { y, line ->
                    line.mapIndexedNotNull { x, c ->
                        val pos = Pos(x.toLong(), y.toLong())
                        if (c == 'S') start = pos
                        if (c == '.' || c == 'S') pos else null
                    }
                }
            return start!! to garden.toSet()
        }

        fun reachableGardens(input: String, steps: Int): Int {
            val (start, gardens) = parseInput(input)
            var reachable = setOf(start)
            repeat(steps) {
                reachable = reachable.map { garden -> garden.adjacent().filter { it in gardens } }.flatten().toSet()
            }
            return reachable.size
        }

        fun wrapAroundReachableGardens(start: Pos, gardens: Set<Pos>, size: Long, steps: Int): Int { //assume square
            var reachable = setOf(start)
            repeat(steps) {
                reachable = reachable.map { garden ->
                    garden.adjacent().filter {
                        Pos(it.x.mod(size), it.y.mod(size)) in gardens
                    } }.flatten().toSet()
            }
            return reachable.size
        }

        fun infiniteReachableGardens(input: String, steps: Long): Long {
            val (start, gardens) = parseInput(input)
            val size = gardens.maxOf { it.x } + 1
            val offset = (size / 2).toInt()

            val y0 = wrapAroundReachableGardens(start, gardens, size, offset).toLong()
            val y1 = wrapAroundReachableGardens(start, gardens, size, size.toInt() + offset).toLong()
            val y2 = wrapAroundReachableGardens(start, gardens, size, size.toInt() * 2 + offset).toLong()

            val x = steps / size
            return y0 * (x - 1) * (x - 2) / 2 - y1 * x * (x - 2) + y2 * x * (x - 1) / 2
        }
    }
}