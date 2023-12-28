package year23

class Day25 {

    companion object {
        fun parseInput(input: String): Map<String, Set<String>> {
            val regex = "[a-z]+".toRegex()
            val nodeToNeighbours = mutableMapOf<String, Set<String>>()

            input.lines().forEach { line ->
                val node = line.substringBefore(':')
                val neighbours = regex.findAll(line.substringAfter(':')).map { it.value }.toSet()

                nodeToNeighbours[node] = (nodeToNeighbours[node] ?: emptySet()) + neighbours
                neighbours.forEach { neighbour ->
                    nodeToNeighbours[neighbour] = (nodeToNeighbours[neighbour] ?: emptySet()) + node
                }
            }

            return nodeToNeighbours
        }

        private fun externalNeighbours(
            component: Map<String, Set<String>>,
            graph: Map<String, Set<String>>,
            node: String
        ) = (graph[node]!! - component.keys).size

        fun componentProduct(input: String): Int {
            val graph = parseInput(input)
            val component = graph.toMutableMap()
            while (component.keys.sumOf { externalNeighbours(component, graph, it) } != 3) {
                component.remove(component.maxBy { (node, _) -> externalNeighbours(component, graph, node) }.key)
            }
            return component.size * (graph.keys - component.keys).size
        }
    }
}