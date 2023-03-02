import java.util.Deque

fun main() {
    // Each directory node has a name, links to parent and children, and a size (if null, it's a dir)
    data class Node(val name: String, val parent: Node?, val size: Int?) {
        val children = mutableSetOf<Node>()
    }

    fun printTree(node: Node, indent: String = "") {
        println("${indent}${node.name} ${if (node.size != null) " (file, size=${node.size})" else " (dir)"}")
        node.children.forEach { printTree(it, "$indent  ") }
    }

    // Get sum of sizes of child files and child dirs (recursively)
    fun getSize(node: Node): Int {
        return node.children.sumOf {
            it.size ?: getSize(it) // Return size if it's a file, makes recursive call if not
        }
//        node.children.filter { it.children.isNotEmpty() }.forEach { calcSize(it) }
//        node.size = node.children.sumOf { it.size }
    }

    fun part1(input: List<String>): Int {
        // Parse input: navigate and build
        val root = Node("/", null, null)
        var current = root
        input.forEach {
            if (it.startsWith("$ ")) { // Commands: only handle cd; ls provides no info
                if (it.startsWith("$ cd ")) {
                    val (targetDir) = Regex("\\$ cd (\\S+)").find(it)!!.destructured.toList()
                    if (targetDir == "/") {
                        current = root
                    } else if (targetDir == "..") {
                        current = current.parent!!
                    } else {
                        val dir = current.children.find { it.name == targetDir }
                        if (dir != null) {
                            current = dir
                        } else {
                            val dir = Node(targetDir, current, null)
                            current.children.add(dir)
                            current = dir
                        }
                    }
                }
                // / or .. or alphanum
            } else if (it.startsWith("dir ")) { // Directory: create node and navigate
                val (name) = Regex("dir (\\S+)").find(it)!!.destructured.toList()
                val dir = Node(name, current, null)
                current.children.add(dir)
//                current = dir
            } else { // File: create node
                val (size, name) = Regex("(\\d+) (\\S+)").find(it)!!.destructured.toList()
                current.children.add(Node(name, current, size.toInt()))
            }
        }

        // debug
        printTree(root)

        // Calc size and report
        return getSize(root)
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
//    check(part2(testInput) == 26)

    val input = readInput("Day07")
    println(part1(input))
//    println(part2(input))
}
