package year23

class Day25 {

    @JvmInline
    value class Node(val value: String)

    data class Graph(val nodes: Set<Node>, val neighbours: Map<Node, Set<Node>>)

    companion object {
        fun parseInput(input: String): Graph {
            val regex = "[a-z]+".toRegex()
            val nodes = mutableSetOf<Node>()
            val nodeToNeighbours = mutableMapOf<Node, Set<Node>>()

            input.lines().forEach { line ->
                val node = Node(line.substringBefore(':'))
                val neighbours = regex.findAll(line.substringAfter(':')).map { Node(it.value) }.toSet()

                nodes.add(node)
                nodeToNeighbours[node] = (nodeToNeighbours[node] ?: emptySet()) + neighbours
                neighbours.forEach { neighbour ->
                    nodeToNeighbours[neighbour] = (nodeToNeighbours[neighbour] ?: emptySet()) + node
                }
            }

            return Graph(nodes, nodeToNeighbours)
        }
    }

}