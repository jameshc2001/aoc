package year23

import year23.Day8.DirectionsAndNodes.Direction.Left
import year23.Day8.DirectionsAndNodes.Direction.Right

class Day8 {

    data class Node(val label: String, val left: String, val right: String)
    data class DirectionsAndNodes(private val directions: String, val nodes: List<Node>) {
        enum class Direction { Left, Right }
        private var directionIndex = 0
        fun nextDirection(): Direction = (if (directions[directionIndex] == 'R') Right else Left)
            .also { directionIndex = (directionIndex + 1) % directions.length }
    }

    companion object {
        fun parseInput(input: String): DirectionsAndNodes {
            val regex = "[A-Z]+".toRegex()
            val lines = input.lines().map { it.replace("\n", "") }.map { it.replace("\r", "") }
            val directions = lines.first()
            val nodes = lines
                .drop(2)
                .map { line -> regex.findAll(line).toList().map { it.value } }
                .map { (label, left, right) -> Node(label, left, right) }
            return DirectionsAndNodes(directions, nodes)
        }

        fun stepsToReach(input: String, destination: String): Int {
            val directionsAndNodes = parseInput(input)
            val labelToNodes = directionsAndNodes.nodes.associateBy { it.label }
            val destinationNode = labelToNodes[destination]!!
            var currentNode = labelToNodes["AAA"]!!
            var steps = 0
            while (currentNode != destinationNode) {
                currentNode = when (directionsAndNodes.nextDirection()) {
                    Left -> labelToNodes[currentNode.left]!!
                    Right -> labelToNodes[currentNode.right]!!
                }
                steps += 1
            }
            return steps
        }
    }


}