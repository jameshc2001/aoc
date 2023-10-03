package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year22.Day7.Companion.smallestDeleteToAchieveSize
import year22.Day7.Companion.sumOfDirsAtMostSize
import year22.Day7.Directory
import year22.Day7.File

class Day7Test {

    @Test
    fun `can parse sample input to get root directory`() {
        val rootDirectory = Day7.generateRootDirectory(sampleInput)
        val expectedDirectory = Directory("/", 48381165,
            mutableListOf(
                Directory("a", 94853,
                    mutableListOf(
                        Directory("e", 584,
                            mutableListOf(File("i", 584))
                        ),
                        File("f", 29116),
                        File("g", 2557),
                        File("h.lst", 62596)
                    )
                ),
                File("b.txt", 14848514),
                File("c.dat", 8504156),
                Directory("d", 24933642,
                    mutableListOf(
                        File("j", 4060174),
                        File("d.log", 8033020),
                        File("d.ext", 5626152),
                        File("k", 7214296)
                    )
                )
            )
        )
        assertThat(rootDirectory).isEqualTo(expectedDirectory)
    }

    @Test
    fun `can find sum of sizes of all directories with size less than 100000`() {
        val rootDir = Day7.generateRootDirectory(sampleInput)
        assertThat(rootDir.sumOfDirsAtMostSize(100000)).isEqualTo(95437)
    }

    @Test
    fun `can get answer for part 1 using question data`() {
        val input = Day7::class.java.getResourceAsStream("/year22/day7.txt")!!.bufferedReader().readText()
        assertThat(sumOfDirsAtMostSize(input, 100000)).isEqualTo(1543140)
    }

    @Test
    fun `find size of smallest directory to delete to achieve at least 30000000 free space`() {
        val rootDir = Day7.generateRootDirectory(sampleInput)
        assertThat(rootDir.smallestDeleteToAchieveSize(30000000)).isEqualTo(24933642)
    }

    @Test
    fun `can get answer for part 2 using question data`() {
        val input = Day7::class.java.getResourceAsStream("/year22/day7.txt")!!.bufferedReader().readText()
        assertThat(smallestDeleteToAchieveSize(input, 30000000)).isEqualTo(1117448)
    }

    private val sampleInput = """
        ${'$'} cd /
        ${'$'} ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        ${'$'} cd a
        ${'$'} ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        ${'$'} cd e
        ${'$'} ls
        584 i
        ${'$'} cd ..
        ${'$'} cd ..
        ${'$'} cd d
        ${'$'} ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent()

}