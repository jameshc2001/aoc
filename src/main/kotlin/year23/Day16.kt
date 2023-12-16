package year23

class Day16 {
    data class Pos(val x: Int, val y: Int) {
        operator fun plus(other: Pos) = Pos(x + other.x, y + other.y)
    }
    data class BeamHead(val pos: Pos, val direction: Pos)

    @JvmInline
    value class Layout(val map: Map<Pos, Char>)

    companion object {
        fun parseInput(input: String): Layout = Layout(
            input.lines()
                .map { it.replace("\n", "").replace("\r", "") }
                .flatMapIndexed { y, line ->
                    line.mapIndexed { x, c -> Pos(x, y) to c }
                }.toMap()
        )

        private fun beamHeadStep(beamHead: BeamHead, layout: Layout): List<BeamHead> {
            val (pos, direction) = beamHead
            val nextPos = pos + direction
            val nextTile = layout.map[nextPos] ?: return emptyList()

            val resultingBeamHeads = when (nextTile) {
                '.' -> listOf(beamHead.copy(pos = nextPos))
                '-' -> if (direction.y == 0) {
                    listOf(beamHead.copy(pos = nextPos))
                } else {
                    listOf(BeamHead(nextPos, Pos(1, 0)), BeamHead(nextPos, Pos(-1, 0)))
                }
                '|' -> if (direction.x == 0) {
                    listOf(beamHead.copy(pos = nextPos))
                } else {
                    listOf(BeamHead(nextPos, Pos(0, 1)), BeamHead(nextPos, Pos(0, -1)))
                }
                '\\' -> when(direction) {
                    Pos(1, 0) -> listOf(BeamHead(nextPos, Pos(0, 1)))
                    Pos(-1, 0) -> listOf(BeamHead(nextPos, Pos(0, -1)))
                    Pos(0, 1) -> listOf(BeamHead(nextPos, Pos(1, 0)))
                    Pos(0, -1) -> listOf(BeamHead(nextPos, Pos(-1, 0)))
                    else -> null
                }
                '/' -> when(direction) {
                    Pos(1, 0) -> listOf(BeamHead(nextPos, Pos(0, -1)))
                    Pos(-1, 0) -> listOf(BeamHead(nextPos, Pos(0, 1)))
                    Pos(0, 1) -> listOf(BeamHead(nextPos, Pos(-1, 0)))
                    Pos(0, -1) -> listOf(BeamHead(nextPos, Pos(1, 0)))
                    else -> null
                }
                else -> null
            }

            return resultingBeamHeads ?: throw RuntimeException("could not calculate resulting beam heads")
        }

        fun energisedTiles(input: String): Int {
            val layout = parseInput(input)
            var beamHeads = beamHeadStep(BeamHead(Pos(-1, 0), Pos(1, 0)), layout)
            val historicBeamHeads = mutableSetOf<BeamHead>()

            while (beamHeads.isNotEmpty()) {
                historicBeamHeads.addAll(beamHeads)
                beamHeads = beamHeads.flatMap { beamHead ->
                    beamHeadStep(beamHead, layout).filter { it !in historicBeamHeads }
                }
            }

            return historicBeamHeads.map { it.pos }.toSet().size
        }

        fun print(layout: Layout, energisedTiles: Set<Pos>) {
            val max = Pos(layout.map.keys.maxOf { it.x }, layout.map.keys.maxOf { it.y })
            println()
            (0 .. max.y).forEach { y ->
                (0 .. max.x).forEach { x ->
                    val pos = Pos(x, y)
                    val char = if (pos in energisedTiles) '#' else layout.map[pos]!!
                    print(char)
                }
                println()
            }
        }
    }


}