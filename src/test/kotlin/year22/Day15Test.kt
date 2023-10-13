package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year22.Day15.Coordinate

class Day15Test {

    @Test
    fun `can parse line of input to a sensor`() {
        val input = "Sensor at x=2, y=18: closest beacon is at x=-2, y=15"
        assertThat(Day15.parseLine(input)).isEqualTo(Day15.Sensor(
            location = Coordinate(2, 18),
            nearestBeacon = Coordinate(-2, 15),
            distanceToNearestBeacon = 7
        ))
    }

    @Test
    fun `can parse input to list of sensors`() {
        val sensorsToBeacons = Day15.parseInput(sampleInput)
        assertThat(sensorsToBeacons).hasSize(14)
        assertThat(sensorsToBeacons).contains(Day15.Sensor(
            location = Coordinate(2, 18),
            nearestBeacon = Coordinate(-2, 15),
            distanceToNearestBeacon = 7
        ))
        assertThat(sensorsToBeacons).contains(Day15.Sensor(
            location = Coordinate(20, 1),
            nearestBeacon = Coordinate(15, 3),
            distanceToNearestBeacon = 7
        ))
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