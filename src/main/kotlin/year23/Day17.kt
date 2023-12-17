package year23

class Day17 {

    data class Pos(val x: Int, val y: Int) {
        operator fun minus(other: Pos) = Pos(x - other.x, y - other.y)
    }

    data class Graph(
        val nodes: List<Pos>,
        val neighbours: Map<Pos, List<Pos>>,
        val weights: Map<Pos, Int> //all edges into a given node will have the same weight
    )

    data class DijkstraResult(val distances: Map<Pos, Int>, val previous: Map<Pos, Pos>)

    companion object {
        fun parseInput(input: String): Graph {
            val weights = input
                .lines()
                .flatMapIndexed { y, line ->
                    line.filter { it.isDigit() }
                        .mapIndexed { x, c ->
                            Pos(x, y) to c.digitToInt()
                        }
                }.toMap()
            val nodes = weights.keys
            val neighbours = nodes.associateWith { pos ->
                listOf(
                    Pos(pos.x - 1, pos.y),
                    Pos(pos.x + 1, pos.y),
                    Pos(pos.x, pos.y - 1),
                    Pos(pos.x, pos.y + 1),
                ).filter { it in nodes }
            }

            return Graph(nodes.toList(), neighbours, weights)
        }

        private fun tooFast(previous: Map<Pos, Pos>, currentNode: Pos, nextNode: Pos): Boolean {
            val a = nextNode
            val b = currentNode
            val c = previous[currentNode]
            val d = previous[c]
            val e = previous[d]

            val path = listOf(a, b, c, d, e).reversed().filterNotNull().zipWithNext()
            return if (path.size < 4) false
            else path.map { (l, r) -> l - r }.toSet().size == 1
        }

        private fun dijkstra(graph: Graph, start: Pos, end: Pos): DijkstraResult {
            val distances = graph.nodes.associateWith { 100_000 }.toMutableMap()
            val previous = mutableMapOf<Pos, Pos>()
            distances[start] = 0

            val queue = graph.nodes.toMutableList()
            while (queue.isNotEmpty()) {
                queue.sortBy { distances[it] }
                val u = queue.removeFirst()
                if (u == end) break

                graph.neighbours[u]!!.filter { it in queue && !tooFast(previous, u, it) }.forEach { v ->
                    val newDistance = distances[u]!! + graph.weights[v]!!
                    if (newDistance < distances[v]!!) {
                        distances[v] = newDistance
                        previous[v] = u
                    }
                }
            }

            return DijkstraResult(distances, previous)
        }

        private fun pathFromDijkstra(dijkstraResult: DijkstraResult, start: Pos, end: Pos): List<Pos> {
            val path = mutableListOf(end)
            while (path.last() != start) {
                path.add(dijkstraResult.previous[path.last()]!!)
            }
            return path.reversed()
        }

        private fun edgeToRemove(path: List<Pos>) = path
            .windowed(5).firstOrNull { nodes ->
                nodes.zipWithNext().map { (a, b) -> a - b }.toSet().size == 1
            }?.let { (_, _, _, a, b) -> a to b }

        fun leastHeatLoss(input: String): Int {
            val graph = parseInput(input)
            val start = Pos(0, 0)
            val end = graph.nodes.maxBy { it.x + it.y } //bottom right
            return dijkstra(graph, start, end).distances[end]!!
        }

//        fun oldLeastHeatLoss(input: String): Int {
//            var graph = parseInput(input)
//            val start = Pos(0, 0)
//            val end = graph.nodes.maxBy { it.x + it.y } //bottom right
//            var dijkstraResult: DijkstraResult
//
//            do {
//                dijkstraResult = dijkstra(graph, start, end)
//                val path = pathFromDijkstra(dijkstraResult, start, end)
//                val edgeToRemove = edgeToRemove(path)
//                printPath(graph, path)
//
//                if (edgeToRemove != null) {
//                    val (a, b) = edgeToRemove
//                    val newNeighbours = graph.neighbours.toMutableMap()
//                    newNeighbours[a] = graph.neighbours[a]!!.filter { it != b }
//                    graph = graph.copy(neighbours = newNeighbours)
//                }
//            } while (edgeToRemove != null)
//
//            return dijkstraResult.distances[end]!!
//        }

        fun printPath(graph: Graph, path: List<Pos>): String {
            val max = graph.nodes.maxBy { it.x + it.y }
            var output = "\n"
            (0 .. max.y).forEach { y ->
                (0 .. max.x).forEach { x ->
                    val pos = Pos(x, y)
                    output += if (pos in path) '#' else graph.weights[pos]!!.toString()
                }
                output += "\n"
            }
            return output
        }

        fun leastHeatLossDFS(input: String): Int {
            val graph = parseInput(input)
            val start = Pos(0, 0)
            val end = graph.nodes.maxBy { it.x + it.y } //bottom right
            val dfs = dfs(graph, start, end)
            return dfs
        }

        private val cache = mutableMapOf<Pos, Int>()

        private fun dfs(graph: Graph, start: Pos, end: Pos, path: List<Pos> = emptyList()): Int {
            val newPath = path.plus(start)
            if (newPath.size >= 5) {
                if (newPath.takeLast(5).zipWithNext().map { (a, b) -> a - b }.toSet().size == 1) return 100_000 //failure
            }
            if (start == end) return 0 //we have arrived
            cache[start]?.let { return it }

            val neighbours = graph.neighbours[start]!!.filter { it !in newPath }
            if (neighbours.isEmpty()) return 100_000
            val shortestDistance = neighbours.minOf { graph.weights[it]!! + dfs(graph, it, end, newPath) }

            cache[start] = shortestDistance
            return shortestDistance
        }
    }
}