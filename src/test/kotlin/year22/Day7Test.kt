package year22

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
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
        println(rootDirectory)
        assertThat(rootDirectory).isEqualTo(expectedDirectory)
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