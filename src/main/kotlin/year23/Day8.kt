package year23

import year23.Day8.DirectionsAndNodes.Direction.Left
import year23.Day8.DirectionsAndNodes.Direction.Right
import kotlin.math.max

class Day8 {

    data class Node(val label: String, val left: String, val right: String)
    data class DirectionsAndNodes(val directions: String, val nodes: List<Node>) {
        enum class Direction { Left, Right }
        private var directionIndex = 0
        fun nextDirection(): Direction = (if (directions[directionIndex] == 'R') Right else Left)
            .also { directionIndex = (directionIndex + 1) % directions.length }
    }

    companion object {
        fun parseInput(input: String): DirectionsAndNodes {
            val regex = "[A-Z0-9]+".toRegex()
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

        private fun findCycle(start: Node, directionsAndNodes: DirectionsAndNodes, labelToNodes: Map<String, Node>): Long {
            var currentNode = start
            var steps = 0L
            val visitedNodes = mutableMapOf<Node, Long>() //node to when we last visited it
            while (currentNode !in visitedNodes.keys || currentNode.label.last() != 'Z') {
                visitedNodes[currentNode] = steps
                currentNode = when (directionsAndNodes.nextDirection()) {
                    Left -> labelToNodes[currentNode.left]!!
                    Right -> labelToNodes[currentNode.right]!!
                }
                steps += 1
            }
            return visitedNodes[currentNode]!!
        }

        private fun findLCM(a: Long, b: Long): Long {
            val larger = max(a, b)
            val maxLcm = a * b
            var lcm = larger
            while (lcm <= maxLcm) {
                if (lcm % a == 0L && lcm % b == 0L) return lcm
                lcm += larger
            }
            return maxLcm
        }

        fun simultaneousStepsToReach(input: String): Long {
            val directionsAndNodes = parseInput(input)
            val labelToNodes = directionsAndNodes.nodes.associateBy { it.label }
            val startNodes = directionsAndNodes.nodes.filter { it.label.last() == 'A' }
            val cyclesLengths = startNodes.map { findCycle(it, directionsAndNodes.copy(), labelToNodes) }
            return cyclesLengths.reduce { acc, l -> findLCM(acc, l) }
        }

        //Interestingly the copy in cyclesLength initialization is not necessary.
        //This is because the cycles are in sync with the length of the directions.
        //i.e. (number of calls to nextDirection % directions.length) is always 0
    }
}