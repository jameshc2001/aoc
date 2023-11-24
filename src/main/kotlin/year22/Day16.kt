package year22

import java.util.*

class Day16 {

    data class Valve(val name: String, val flowRate: Int)

    data class DijkstraResults(val distances: Map<Valve, Int>, val previous: Map<Valve, Valve>)

    companion object {

        fun parseInput(input: String): Map<Valve, List<Valve>> {
            val valvesToNeighbours = input.lines()
                .map { it.replace("\n", "") }
                .map { it.replace("\r", "") }
                .associate { line -> parseLine(line) }
            val namesToValves = valvesToNeighbours.keys.associateBy { it.name }
            return valvesToNeighbours.mapValues { it.value.map { name -> namesToValves[name]!! } }
        }

        private fun parseLine(line: String): Pair<Valve, List<String>> {
            val name = line.substringAfter("Valve ").substringBefore(' ')
            val flowRate = line.substringAfter("rate=").substringBefore(';').toInt()
            val valve = Valve(name, flowRate)
            val neighbours = line
                .substringAfter("valve")
                .drop(1)
                .replace(" ", "")
                .split(",")
            return valve to neighbours
        }

        private fun dijkstra(graph: Map<Valve, List<Valve>>, start: Valve, end: Valve? = null): DijkstraResults {
            val distances: MutableMap<Valve, Int> = graph.keys.associateWith { Int.MAX_VALUE - 1 }.toMutableMap()
            val previous: MutableMap<Valve, Valve?> = graph.keys.associateWith { null }.toMutableMap()

            distances[start] = 0
            val unexploredVertices = PriorityQueue(
                graph.keys.size,
                compareBy { coordinate: Valve -> distances[coordinate]!! }
            )
            graph.keys.forEach { unexploredVertices.add(it) }

            while (unexploredVertices.isNotEmpty()) {
                val currentVertex = unexploredVertices.poll()
                if (currentVertex == end) break

                graph[currentVertex]!!
                    .filter { it in unexploredVertices }
                    .forEach { neighbour ->
                        val distance = distances[currentVertex]!! + 1
                        if (distance < distances[neighbour]!!) {
                            distances[neighbour] = distance
                            previous[neighbour] = currentVertex
                            unexploredVertices.remove(neighbour)
                            unexploredVertices.add(neighbour)
                        }
                    }
            }

            val previousWithoutNulls = previous
                .map { entry -> entry.value?.let { entry.key to it } }
                .filterNotNull()
                .toMap()
            return DijkstraResults(distances, previousWithoutNulls)
        }

        private var maxPressureReleased = 0

        fun findMaxPressureRelease(input: String): Int {
            val map = parseInput(input)
            val valvesWithFlow = map.keys.filter { it.flowRate > 0 }.toSet()
            val startValve = map.keys.find { it.name == "AA" }!!
            val valvesToDistances = valvesWithFlow.plus(startValve).associateWith { dijkstra(map, it).distances }

            maxPressureReleased = 0
            search(startValve, 30, 0, valvesWithFlow, valvesToDistances)
            return maxPressureReleased
        }

        private fun search(
            currentValve: Valve,
            remainingMinutes: Int,
            currentPressureReleased: Int,
            unopenedValvesWithFlow: Set<Valve>,
            allDistances: Map<Valve, Map<Valve, Int>>
        ) {
            if (currentPressureReleased > maxPressureReleased) maxPressureReleased = currentPressureReleased
            if (remainingMinutes == 0 || unopenedValvesWithFlow.isEmpty()) return

            val distances = allDistances[currentValve]!!
            unopenedValvesWithFlow.forEach { valve ->
                val travelAndOpenTime = distances[valve]!! + 1
                val remainingMinutesIfOpened = remainingMinutes - travelAndOpenTime
                val pressureReleasedFromOpening = remainingMinutesIfOpened * valve.flowRate

                if (remainingMinutesIfOpened >= 0) {
                    search(
                        valve,
                        remainingMinutesIfOpened,
                        currentPressureReleased + pressureReleasedFromOpening,
                        unopenedValvesWithFlow.minus(valve),
                        allDistances
                    )
                }
            }
        }
    }

}