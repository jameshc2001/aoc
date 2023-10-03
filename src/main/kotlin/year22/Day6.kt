package year22

import java.lang.RuntimeException

class Day6 {
    companion object {
        fun firstStartMarker(input: String): Int = firstMarkerDetector(input, 4)
        fun firstMessageMarker(input: String): Int = firstMarkerDetector(input, 14)

        private fun firstMarkerDetector(input: String, markerLength: Int): Int {
            input.drop(markerLength - 1).mapIndexed { i, _ ->
                val section = input.substring(i, i + markerLength).toSet()
                if (section.size == markerLength) return i + markerLength
            }
            throw RuntimeException("No marker of length $markerLength found in $input")
        }
    }
}