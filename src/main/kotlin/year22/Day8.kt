package year22

class Day8 {
    data class Coordinate(val x: Int, val y: Int)

    data class TreeGrid(val grid: Map<Coordinate, Int>, val width: Int, val height: Int) {

        fun treeIsVisible(coordinate: Coordinate): Boolean {
            val treeHeight = grid[coordinate]!!
            val importantTrees = getAlignedTrees(coordinate)

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

        private fun getAlignedTrees(coordinate: Coordinate) =
            ((0..<width).map { x -> Coordinate(x, coordinate.y) } +
                    (0..<height).map { y -> Coordinate(coordinate.x, y) })
                .associateWith { grid[it]!! }

        fun totalVisibleTrees(): Int = grid.filter { treeIsVisible(it.key) }.size

        fun treeScenicScore(coordinate: Coordinate): Int {
            val treeHeight = grid[coordinate]!!
            val importantTrees = getAlignedTrees(coordinate)

            val above = importantTrees.filter { it.key.x == coordinate.x && it.key.y > coordinate.y }
                .viewableTrees(treeHeight, compareBy { it.y })
            val below = importantTrees.filter { it.key.x == coordinate.x && it.key.y < coordinate.y }
                .viewableTrees(treeHeight, compareByDescending { it.y })
            val right = importantTrees.filter { it.key.y == coordinate.y && it.key.x > coordinate.x }
                .viewableTrees(treeHeight, compareBy { it.x })
            val left = importantTrees.filter { it.key.y == coordinate.y && it.key.x < coordinate.x }
                .viewableTrees(treeHeight, compareByDescending { it.x })

            return above * below * right * left
        }

        private fun Map<Coordinate, Int>.viewableTrees(viewingHeight: Int, comparator: Comparator<Coordinate>): Int {
            val sorted = this.toSortedMap(comparator).toList()
            return sorted.fold(0) { acc, (_, height) ->
                if (height < viewingHeight) {
                    acc + 1
                } else {
                    return acc + 1
                }
            }
        }

        fun bestScenicScore(): Int = grid.maxOf { treeScenicScore(it.key) }

        companion object {
            fun fromString(input: String): TreeGrid {
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
        }
    }

    companion object {
        fun totalVisibleTrees(input: String): Int = TreeGrid.fromString(input).totalVisibleTrees()
        fun bestScenicScore(input: String): Int = TreeGrid.fromString(input).bestScenicScore()
    }
}