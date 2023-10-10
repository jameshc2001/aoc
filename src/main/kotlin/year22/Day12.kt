package year22

import year22.Day12.Coordinate

typealias D12Graph = Map<Coordinate, Set<Coordinate>>

class Day12 {

    @JvmInline
    value class Height(val value: Int) {

        companion object {
            fun fromChar(c: Char): Height {
                if (c == 'S') return Height(0)
                if (c == 'E') return Height(25)
                return Height(c.code - 'a'.code)
            }
        }
    }

    data class Coordinate(val x: Int, val y: Int)

    data class HeightMap(val coordinatesToHeights: Map<Coordinate, Height>, val start: Coordinate, val end: Coordinate)

    data class DijkstraResults(val distances: Map<Coordinate, Int>, val previous: Map<Coordinate, Coordinate>)

    companion object {

        fun parseInput(input: String): HeightMap {
            var start: Coordinate? = null
            var end: Coordinate? = null
            val map = input
                .replace("\r", "")
                .lines()
                .mapIndexed { y, line ->
                    line.mapIndexed { x, c ->
                        val coordinate = Coordinate(x, y)
                        if (c == 'S') start = coordinate
                        if (c == 'E') end = coordinate
                        coordinate to Height.fromChar(c)

                    }
                }
                .flatten()
                .toMap()
            return HeightMap(map, start!!, end!!)
        }

        fun createDirectedGraph(heightMap: HeightMap): D12Graph = heightMap.coordinatesToHeights
            .map { (coordinate, height) ->
                val left = Coordinate(coordinate.x - 1, coordinate.y)
                val right = Coordinate(coordinate.x + 1, coordinate.y)
                val up = Coordinate(coordinate.x, coordinate.y + 1)
                val down = Coordinate(coordinate.x, coordinate.y - 1)

                val neighbours = listOf(left, right, up, down)
                    .associateWith { heightMap.coordinatesToHeights[it] }
                    .mapNotNull { (otherCoordinate, otherHeight) ->
                        if ((otherHeight?.value ?: 99) <= height.value + 1) otherCoordinate else null
                    }
                    .toSet()

                coordinate to neighbours
            }.toMap()


        //the minus 1 below is important to prevent integer overflow when adding one to the distance
        fun dijkstra(graph: D12Graph, start: Coordinate, end: Coordinate? = null): DijkstraResults {
            val distances: MutableMap<Coordinate, Int> = graph.keys.associateWith { Int.MAX_VALUE - 1 }.toMutableMap()
            val previous: MutableMap<Coordinate, Coordinate?> = graph.keys.associateWith { null }.toMutableMap()

            distances[start] = 0
            val unexploredVertices: MutableSet<Coordinate> = graph.keys.toMutableSet()

            while (unexploredVertices.isNotEmpty()) {
                val currentVertex = unexploredVertices.minBy { distances[it]!! }
                if (currentVertex == end) break
                unexploredVertices.remove(currentVertex)

                graph[currentVertex]!!
                    .filter { it in unexploredVertices }
                    .forEach { neighbour ->
                        val distance = distances[currentVertex]!! + 1
                        if (distance < distances[neighbour]!!) {
                            distances[neighbour] = distance
                            previous[neighbour] = currentVertex
                        }
                    }
            }

            val previousWithoutNulls = previous
                .map { entry -> entry.value?.let { entry.key to it } }
                .filterNotNull()
                .toMap()
            return DijkstraResults(distances, previousWithoutNulls)
        }

        fun lengthOfShortestPath(input: String): Int {
            val heightMap = parseInput(input)
            val directedGraph = createDirectedGraph(heightMap)
            val dijkstraResults = dijkstra(directedGraph, heightMap.start, heightMap.end)
            return dijkstraResults.distances[heightMap.end]!!
        }

        fun lengthOfAnyShortedPath(input: String): Int {
            val heightMap = parseInput(input)
            val directedGraph = createDirectedGraph(heightMap)
            val graphWithReversedEdges: D12Graph = directedGraph.keys.associateWith { vertex ->
                directedGraph.filter { vertex in it.value }.keys
            }
            val dijkstraResults = dijkstra(graphWithReversedEdges, heightMap.end)
            return dijkstraResults.distances
                .filterKeys { heightMap.coordinatesToHeights[it] == Height(0) }
                .values
                .min()
        }

    }
}
