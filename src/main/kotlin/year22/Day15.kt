package year22

import kotlin.math.abs

class Day15 {

    data class Coordinate(val x: Int, val y: Int)
    data class Sensor(val location: Coordinate, val nearestBeacon: Coordinate, val distanceToNearestBeacon: Int)

    companion object {
        fun parseLine(input: String): Sensor {
            val matchResults = "-?[0-9]+".toRegex().findAll(input).toList()
            val location = Coordinate(matchResults[0].value.toInt(), matchResults[1].value.toInt())
            val nearestBeacon = Coordinate(matchResults[2].value.toInt(), matchResults[3].value.toInt())
            val distance = distance(location, nearestBeacon)
            return Sensor(location, nearestBeacon, distance)
        }

        fun parseInput(input: String): Set<Sensor> = input
            .replace("\r", "")
            .lines()
            .map { parseLine(it) }
            .toSet()

        private fun distance(c1: Coordinate, c2: Coordinate) = abs(c1.x - c2.x) + abs(c1.y - c2.y)

        fun positionsWithNoBeaconOnRow(input: String, row: Int): Int {
            val sensors = parseInput(input)
            val beacons = sensors.map { it.nearestBeacon }.toSet()

            val sensorsInRange = sensors.filter {
                row in (it.location.y - it.distanceToNearestBeacon..it.location.y + it.distanceToNearestBeacon)
            }

            val minX = sensorsInRange.minOf { it.location.x - it.distanceToNearestBeacon }
            val maxX = sensorsInRange.maxOf { it.location.x + it.distanceToNearestBeacon }

            return (minX..maxX)
                .map { x -> Coordinate(x, row) }
                .filter { location ->
                    sensorsInRange.any { sensor -> distance(location, sensor.location) <= sensor.distanceToNearestBeacon }
                            && location !in beacons
                }
                .size
        }
    }
}