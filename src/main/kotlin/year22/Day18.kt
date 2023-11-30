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

            val surfaceGroups = mutableListOf<MutableSet<Pos>>()


            return 0
        }

        //part 2... keep surface areas connected together. Assume the largest group is the exterior surface area

        //starting from the furthest point from origin, march around the droplet

        //somehow solidify the droplet. Sand fill?
    }


}