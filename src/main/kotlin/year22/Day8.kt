package year22

class Day8 {
    data class Coordinate(val x: Int, val y: Int)

    companion object {
        fun createTreeGrid(input: String): Map<Coordinate, Int> {
            val lines = input.lines()
            val rows = lines.size
            val columns = lines.first().length
            return (0 ..< columns).flatMap { x ->
                (0 ..< rows).map { y ->
                    Coordinate(x, y) to lines[y][x].digitToInt()
                }
            }.toMap()
        }

        fun Map<Coordinate, Int>.treeIsVisible(coordinate: Coordinate): Boolean {
            val treeHeight = this[coordinate]!!
            val importantTrees = this.filter { it.key.x == coordinate.x || it.key.y == coordinate.y }

            val above = importantTrees.asSequence()
                .filter { it.key.x == coordinate.x && it.key.y > coordinate.y }
                .filter { it.value >= treeHeight }
            val below = importantTrees.asSequence()
                .filter { it.key.x == coordinate.x && it.key.y < coordinate.y }
                .filter { it.value >= treeHeight }
            val right = importantTrees.asSequence()
                .filter { it.key.y == coordinate.y && it.key.x > coordinate.x }
                .filter { it.value >= treeHeight }
            val left = importantTrees.asSequence()
                .filter { it.key.y == coordinate.y && it.key.x < coordinate.x }
                .filter { it.value >= treeHeight }

            return above.toList().isEmpty() ||
                below.toList().isEmpty() ||
                left.toList().isEmpty() ||
                right.toList().isEmpty()
        }

        private fun Map<Coordinate, Int>.totalVisibleTrees(): Int = this.filter { this.treeIsVisible(it.key) }.size

        fun totalVisibleTrees(input: String): Int = createTreeGrid(input).totalVisibleTrees()
    }
}