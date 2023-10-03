package year22

@Suppress("EqualsOrHashCode")
class Day7 {

    open class File(private val name: String, var size: Int = 0) {
        override fun equals(other: Any?): Boolean = other is File && other.name == name && other.size == size
    }

    class Directory(name: String, size: Int = 0, val children: MutableList<File> = mutableListOf()) : File(name, size) {
        override fun equals(other: Any?): Boolean {
            if (other is Directory) {
                if (other.children.toList().sortedBy { it.size } != children.toList().sortedBy { it.size }) return false
            }
            return super.equals(other)
        }

        fun toDirectoriesList(): List<Directory> {
            return children
                .filterIsInstance<Directory>()
                .flatMap { it.toDirectoriesList() }
                .plus(this)
        }
    }

    companion object {
        private fun String.isChangeDirectoryCommand() = startsWith("$ cd ")
        private fun String.getCDFileName() = substringAfter("$ cd ")
        private fun String.isFileListing() = first().isDigit()

        private fun addFileToDirectory(line: String, currentDirectory: Directory) {
            val (size, name) = line.split(" ")
            val newFile = File(name, size.toInt())
            currentDirectory.children.add(newFile)
            currentDirectory.size += newFile.size
        }

        private fun handleChangeDirectoryCommand(line: String, directoryStack: ArrayDeque<Directory>, currentDirectory: Directory): Directory {
            val fileName = line.getCDFileName()
            return if (fileName == "..") {
                directoryStack.last().size += currentDirectory.size
                directoryStack.removeLast()
            } else {
                val newDirectory = Directory(fileName)
                currentDirectory.children.add(newDirectory)
                directoryStack.addLast(currentDirectory)
                newDirectory
            }
        }

        fun generateRootDirectory(input: String): Directory {
            val lines = input.lines()
            var currentDirectory = Directory("/")
            val directoryStack = ArrayDeque(listOf(currentDirectory))
            lines.drop(1).forEach { line ->
                if (line.isChangeDirectoryCommand()) {
                    currentDirectory = handleChangeDirectoryCommand(line, directoryStack, currentDirectory)
                } else if (line.isFileListing()) {
                    addFileToDirectory(line, currentDirectory)
                }
            }
            while (directoryStack.size > 1) {
                directoryStack.last().size += currentDirectory.size
                currentDirectory = directoryStack.removeLast()
            }
            return currentDirectory
        }

        fun Directory.sumOfDirsAtMostSize(size: Int): Int {
            return this
                .toDirectoriesList()
                .filter { it.size <= size }
                .sumOf { it.size }
        }

        fun sumOfDirsAtMostSize(input: String, size: Int): Int = generateRootDirectory(input).sumOfDirsAtMostSize(size)

        fun Directory.smallestDeleteToAchieveSize(size: Int): Int {
            val remainingSpace = 70000000 - this.size
            val requiredDeleteSize = size - remainingSpace
            return if (remainingSpace <= 0) 0
            else this
                .toDirectoriesList()
                .filter { it.size >= requiredDeleteSize }
                .minOf { it.size }
        }

        fun smallestDeleteToAchieveSize(input: String, size: Int): Int =
            generateRootDirectory(input).smallestDeleteToAchieveSize(size)
    }
}