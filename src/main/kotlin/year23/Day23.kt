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
            val graph = parseInput(input, true).simplified()
            val start = graph.nodes.minBy { it.y }
            val end = graph.nodes.maxBy { it.y }
            return dfsLongestPathLength(graph, emptyList(), start, end)
        }

        private fun Graph.simplified(): Graph {
            var updatedNodes = nodes
            var updatedNeighbours = neighbours
            var updatedWeights = weights

            var change = true
            do {
                val node = updatedNodes.firstOrNull { updatedNeighbours[it]!!.size == 2 }
                if (node == null) {
                    change = false
                } else {
                    val (first, last) = updatedNeighbours[node]!!
                    val section = ArrayDeque(listOf(first, node, last))

                    do {
                        val next = updatedNeighbours[section.first()]!!.singleOrNull { it !in section }
                        next?.let { section.addFirst(next) }
                    } while (next != null && updatedNeighbours[next]!!.size == 2)

                    do {
                        val next = updatedNeighbours[section.last()]!!.singleOrNull { it !in section }
                        next?.let { section.addLast(next) }
                    } while (next != null && updatedNeighbours[next]!!.size == 2)

                    val nodesToRemove = section.drop(1).dropLast(1)
                    updatedNodes = updatedNodes.filter { it !in nodesToRemove }.toSet()

                    updatedNeighbours = updatedNeighbours.filter { it.key !in nodesToRemove }
                        .minus(section.first())
                        .plus(section.first() to updatedNeighbours[section.first()]!!.filter { it !in section }.plus(section.last()))
                        .minus(section.last())
                        .plus(section.last() to updatedNeighbours[section.last()]!!.filter { it !in section }.plus(section.first()))

                    val edgeCost = section.size - 1
                    updatedWeights = updatedWeights.filter { it.key.first !in nodesToRemove && it.key.second !in nodesToRemove }
                        .plus((section.first() to section.last()) to edgeCost)
                        .plus((section.last() to section.first()) to edgeCost)
                }
            } while (change)

            return Graph(updatedNodes, updatedNeighbours, updatedWeights)
        }
    }
}