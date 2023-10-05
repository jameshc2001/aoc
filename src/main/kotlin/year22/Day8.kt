package year22

class Day8 {
    data class Coordinate(val x: Int, val y: Int)

    data class TreeGrid(val grid: Map<Coordinate, Int>, val width: Int, val height: Int)

    companion object {
        fun createTreeGrid(input: String): TreeGrid {
            val lines = input.lines()
            val height = lines.size
            val width = lines.first().length
            val grid = (0 ..< width).flatMap { x ->
                (0 ..< height).map { y ->
                    Coordinate(x, y) to lines[y][x].digitToInt()
                }
            }.toMap()
            return TreeGrid(grid, width, height)
        }

        fun TreeGrid.treeIsVisible(coordinate: Coordinate): Boolean {
            val treeHeight = this.grid[coordinate]!!
            val importantTrees = ((0..<this.width).map { x -> Coordinate(x, coordinate.y) } +
                    (0..<this.height).map { y -> Coordinate(coordinate.x, y) })
                .associateWith { this.grid[it]!! }

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

        private fun TreeGrid.totalVisibleTrees(): Int = this.grid.filter { this.treeIsVisible(it.key) }.size

        fun totalVisibleTrees(input: String): Int = createTreeGrid(input).totalVisibleTrees()
    }
}