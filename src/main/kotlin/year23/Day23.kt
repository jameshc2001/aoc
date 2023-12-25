package year23

class Day23 {
    data class Pos(val x: Int, val y: Int) {
        fun adjacent() = listOf(Pos(x - 1, y), Pos(x + 1, y), Pos(x, y - 1), Pos(x, y + 1))
    }
    data class Graph(
        val nodes: Set<Pos>,
        val neighbours: Map<Pos, List<Pos>>,
        val weights: Map<Pair<Pos, Pos>, Int>
    )

    companion object {
        fun parseInput(input: String, dry: Boolean = false): Graph {
            val allPos = input.lines().map { it.replace("\n", "").replace("\r", "") }
                .flatMapIndexed { y, line ->
                    line.mapIndexedNotNull { x, c ->
                        if (c == '#') null else Pos(x, y) to c
                    }
                }.toMap()
            val neighbours = allPos.entries.associate { (pos, type) ->
                val validAdjacent = pos.adjacent().filter { it in allPos.keys }
                pos to if (dry) validAdjacent else when (type) {
                    '.' -> validAdjacent
                    '>' -> listOf(Pos(pos.x + 1, pos.y))
                    '<' -> listOf(Pos(pos.x - 1, pos.y))
                    '^' -> listOf(Pos(pos.x, pos.y - 1))
                    'v' -> listOf(Pos(pos.x, pos.y + 1))
                    else -> throw RuntimeException("unknown type $type")
                }
            }
            val weights = neighbours.flatMap { (pos, posNeighbours) ->
                posNeighbours.map { neighbour -> (pos to neighbour) to 1 }
            }.toMap()
            return Graph(allPos.keys, neighbours, weights)
        }

        fun longestPathLength(input: String): Int {
            val graph = parseInput(input)
            val start = graph.nodes.minBy { it.y }
            val end = graph.nodes.maxBy { it.y }
            return dfsLongestPathLength(graph, emptyList(), start, end)
        }

        //change path to just be prev? depends on graph
        private fun dfsLongestPathLength(graph: Graph, path: List<Pos>, current: Pos, end: Pos): Int {
            if (current == end) return 0

            val options = graph.neighbours[current]!!.filter { it !in path }
            if (options.isEmpty()) return -10000000

            val newPath = path + current
            return options.maxOf { next ->
                graph.weights[current to next]!! + dfsLongestPathLength(graph, newPath, next, end)
            }
        }

        fun longestDryPathLength(input: String): Int {
            val graph = parseInput(input, true)
            val start = graph.nodes.minBy { it.y }
            val end = graph.nodes.maxBy { it.y }
            return dfsLongestPathLength(graph, emptyList(), start, end)
        }
    }
}