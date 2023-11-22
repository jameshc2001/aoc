package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year22.Day15.Companion.locationOfUndetectedBeacon
import year22.Day15.Companion.parseInput
import year22.Day15.Companion.perimeter
import year22.Day15.Coordinate
import year22.Day15.Sensor

class Day15Test {

    @Test
    fun `can parse line of input to a sensor`() {
        val input = "Sensor at x=2, y=18: closest beacon is at x=-2, y=15"
        assertThat(Day15.parseLine(input)).isEqualTo(
            Sensor(
                location = Coordinate(2, 18),
                nearestBeacon = Coordinate(-2, 15),
                distanceToNearestBeacon = 7
            )
        )
    }

    @Test
    fun `can parse input to list of sensors`() {
        val sensorsToBeacons = parseInput(sampleInput)
        assertThat(sensorsToBeacons).hasSize(14)
        assertThat(sensorsToBeacons).contains(
            Sensor(
                location = Coordinate(2, 18),
                nearestBeacon = Coordinate(-2, 15),
                distanceToNearestBeacon = 7
            )
        )
        assertThat(sensorsToBeacons).contains(
            Sensor(
                location = Coordinate(20, 1),
                nearestBeacon = Coordinate(15, 3),
                distanceToNearestBeacon = 7
            )
        )
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day15.positionsWithNoBeaconOnRow(sampleInput, 10)).isEqualTo(26)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day15::class.java.getResourceAsStream("/year22/day15.txt")!!.bufferedReader().readText()
        assertThat(Day15.positionsWithNoBeaconOnRow(input, 2000000)).isEqualTo(6275922)
    }

    @Test
    fun `can get perimeter coordinates of sensor`() {
        val sensor = Sensor(Coordinate(5, 5), Coordinate(7, 7), 4)
        assertThat(sensor.perimeter().toSet()).hasSize(20)
    }

    @Test
    fun `can get perimeter coordinates of smaller sensor`() {
        val sensor = Sensor(Coordinate(3, 3), Coordinate(4, 4), 2)
        assertThat(sensor.perimeter().toSet()).isEqualTo(
            setOf(
                Coordinate(x = 3, y = 6),
                Coordinate(x = 3, y = 0),
                Coordinate(x = 4, y = 5),
                Coordinate(x = 2, y = 5),
                Coordinate(x = 4, y = 1),
                Coordinate(x = 2, y = 1),
                Coordinate(x = 5, y = 4),
                Coordinate(x = 1, y = 4),
                Coordinate(x = 5, y = 2),
                Coordinate(x = 1, y = 2),
                Coordinate(x = 6, y = 3),
                Coordinate(x = 0, y = 3),
            )
        )
    }

    @Test
    fun `can find location of undetected beacon`() {
        val sensors = parseInput(sampleInput)
        assertThat(locationOfUndetectedBeacon(sensors, 20)).isEqualTo(Coordinate(14, 11))
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        assertThat(Day15.tuningFrequency(sampleInput, 20)).isEqualTo(56000011)
    }

    @Test
    fun `can get answer for part 2 using question input`() {
        val input = Day15::class.java.getResourceAsStream("/year22/day15.txt")!!.bufferedReader().readText()
        assertThat(Day15.tuningFrequency(input, 4000000)).isEqualTo(11747175442119)
    }

    private val sampleInput = """
        Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        Sensor at x=9, y=16: closest beacon is at x=10, y=16
        Sensor at x=13, y=2: closest beacon is at x=15, y=3
        Sensor at x=12, y=14: closest beacon is at x=10, y=16
        Sensor at x=10, y=20: closest beacon is at x=10, y=16
        Sensor at x=14, y=17: closest beacon is at x=10, y=16
        Sensor at x=8, y=7: closest beacon is at x=2, y=10
        Sensor at x=2, y=0: closest beacon is at x=2, y=10
        Sensor at x=0, y=11: closest beacon is at x=2, y=10
        Sensor at x=20, y=14: closest beacon is at x=25, y=17
        Sensor at x=17, y=20: closest beacon is at x=21, y=22
        Sensor at x=16, y=7: closest beacon is at x=15, y=3
        Sensor at x=14, y=3: closest beacon is at x=15, y=3
        Sensor at x=20, y=1: closest beacon is at x=15, y=3
    """.trimIndent()

}