package year22

class Day7 {

    open class File(val name: String, var size: Int = 0)
    class Directory(name: String, size: Int = 0, val children: MutableList<File> = mutableListOf()) : File(name, size)
    companion object {
        fun generateRootDirectory(input: String): Directory {
            val lines = input.lines()
            var currentDirectory = Directory("/")
            val directoryStack = ArrayDeque(listOf(currentDirectory))
            lines.drop(1).forEach { line ->
                if (line.startsWith("$ cd ..")) { //moving up a directory
                    val sizeOfDirectory = currentDirectory.size
                    currentDirectory = directoryStack.removeLast()
                    currentDirectory.size += sizeOfDirectory
                } else if (line.startsWith("$ cd ")) { //moving down a directory
                    val newDirectory = Directory(line.substringAfter("$ cd "))
                    currentDirectory.children.add(newDirectory)
                    directoryStack.addLast(currentDirectory)
                    currentDirectory = newDirectory
                } else if (line.first().isDigit()) { //data file
                    val (size, name) = line.split(" ")
                    val newFile = File(name, size.toInt())
                    currentDirectory.children.add(newFile)
                    currentDirectory.size += newFile.size
                }
            }
            while (directoryStack.size > 1) {
                val sizeOfDirectory = currentDirectory.size
                currentDirectory = directoryStack.removeLast()
                currentDirectory.size += sizeOfDirectory
            }
            return currentDirectory
        }
    }


}