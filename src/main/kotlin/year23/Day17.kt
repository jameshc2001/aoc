package year23

import java.util.PriorityQueue

class Day17 {

    data class Pos(val x: Int, val y: Int) {
        operator fun minus(other: Pos) = Pos(x - other.x, y - other.y)
    }

    data class Graph(
        val nodes: List<Pos>,
        val neighbours: Map<Pos, List<Pos>>,
        val weights: Map<Pos, Int> //all edges into a given node will have the same weight
    )

    data class SearchState(val pos: Pos, val direction: Pos, val streak: Int)

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

        private fun dijkstra(graph: Graph, start: Pos, end: Pos, ultra: Boolean): Int {
            val startState = SearchState(start, Pos(0, 0), 0)
            val distances = mutableMapOf(startState to 0)
            val queue = PriorityQueue<SearchState>(compareBy { distances[it] })
            queue.add(startState)

            while (queue.isNotEmpty()) {
                val u = queue.poll()
                if (u.pos == end) return distances[u]!!

                graph.neighbours[u.pos]!!
                    .map { v ->
                        val direction = v - u.pos
                        val streak = if (u.direction == direction) u.streak + 1 else 1
                        SearchState(v, direction, streak)
                    }
                    .filter { v -> v.pos != u.pos - u.direction } //can't go backwards
                    .filter { v -> v.streak <= if (ultra) 10 else 3 } //can't go too fast
                    .filter { v -> if (ultra && v.streak == 1 && u != startState) u.streak >= 4 else true } //ultra turning rule
                    .forEach { v ->
                        val newDistance = distances[u]!! + graph.weights[v.pos]!!
                        if (newDistance < (distances[v] ?: 100_000)) {
                            distances[v] = newDistance
                            queue.add(v)
                        }
                    }
            }
            throw RuntimeException("failed to find path")
        }

        fun leastHeatLoss(input: String, ultra: Boolean = false): Int {
            val graph = parseInput(input)
            val start = Pos(0, 0)
            val end = graph.nodes.maxBy { it.x + it.y }
            return dijkstra(graph, start, end, ultra)
        }
    }
}