package year22

class Day18 {
    data class Pos(val x: Int, val y: Int, val z: Int) {
        fun adjacentPositions() = setOf(
            Pos(x-1,y,z),
            Pos(x+1,y,z),
            Pos(x,y-1,z),
            Pos(x,y+1,z),
            Pos(x,y,z-1),
            Pos(x,y,z+1),
        )
    }

    companion object {
        fun parseInput(input: String): Set<Pos> = input
            .lines()
            .asSequence()
            .map { it.replace("\n", "") }
            .map { it.replace("\r", "") }
            .map { it.split(',') }
            .map { (x, y, z) -> Pos(x.toInt(), y.toInt(), z.toInt()) }
            .toSet()

        fun surfaceArea(input: String): Int {
            val cubes = parseInput(input)
            return parseInput(input).flatMap { cube ->
                cube.adjacentPositions().filter { adjPos ->
                    adjPos !in cubes
                }
            }.size
        }

        fun exteriorSurfaceArea(input: String): Int {
            val cubes = parseInput(input)
            val surfaceCubes = parseInput(input).flatMap { cube ->
                cube.adjacentPositions().filter { adjPos ->
                    adjPos !in cubes
                }
            }.toSet()

            val neighbouringSurfaceCubes = getNeighbouringSurfaceCubes(surfaceCubes, cubes)
            val exteriorSurfaceCubes = getExteriorSurfaceCubes(neighbouringSurfaceCubes)

            return exteriorSurfaceCubes.flatMap { cube ->
                cube.adjacentPositions().filter { adjPos ->
                    adjPos in cubes
                }
            }.size
        }

        //two calls to adjacent positions, filtering droplet cubes after the first, prevents us from crossing diagonals between cubes edges
        private fun getNeighbouringSurfaceCubes(
            surfaceCubes: Set<Pos>,
            cubes: Set<Pos>
        ) = surfaceCubes.associateWith { cube ->
            cube.adjacentPositions()
                .filter { it !in cubes }
                .flatMap { it.adjacentPositions().plus(it) }
                .filter { it in surfaceCubes && it != cube }
                .toSet()
        }

        private fun getExteriorSurfaceCubes(cubesToNeighbours: Map<Pos, Set<Pos>>): Set<Pos> {
            val start = cubesToNeighbours.keys.maxBy { it.x }
            return traverseExterior(start, cubesToNeighbours)
        }

        private fun traverseExterior(cube: Pos, cubesToNeighbours: Map<Pos, Set<Pos>>, explored: MutableSet<Pos> = mutableSetOf()): Set<Pos> {
            if (cube in explored) return emptySet()
            explored.add(cube)
            return setOf(
                cube,
                *cubesToNeighbours[cube]!!
                    .flatMap { traverseExterior(it, cubesToNeighbours, explored) }
                    .toSet()
                    .toTypedArray()
            )
        }
    }
}