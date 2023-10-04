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

            val above = this.filter { it.key.x == coordinate.x && it.key.y > coordinate.y }.filterNot { it.value < treeHeight }
            val below = this.filter { it.key.x == coordinate.x && it.key.y < coordinate.y }.filterNot { it.value < treeHeight }
            val right = this.filter { it.key.y == coordinate.y && it.key.x > coordinate.x }.filterNot { it.value < treeHeight }
            val left = this.filter { it.key.y == coordinate.y && it.key.x < coordinate.x }.filterNot { it.value < treeHeight }

            return above.isEmpty() || below.isEmpty() || right.isEmpty() || left.isEmpty()
        }

        private fun Map<Coordinate, Int>.totalVisibleTrees(): Int = this.filter { this.treeIsVisible(it.key) }.size

        fun totalVisibleTrees(input: String): Int = createTreeGrid(input).totalVisibleTrees()
    }
}