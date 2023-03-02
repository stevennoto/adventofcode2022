import java.util.Deque

fun main() {
    data class Tree(val height: Int, var visible: Boolean = false) {
    }

    fun flagVisibleTrees(trees: List<Tree>) {
        var height = -1
        trees.forEach { if (it.height > height) {it.visible = true; height = it.height } }
    }

    fun part1(input: List<String>): Int {
        // Parse grid of trees. Then go through rows and cols (from both directions), flagging visibility
        val trees = input.map { it.toCharArray().map { Tree(it.toString().toInt()) } }

        trees.forEach { treeRow ->
            flagVisibleTrees(treeRow)
            flagVisibleTrees(treeRow.reversed())
        }
        for (index in 0 until trees[0].size) {
            val treeCol = trees.map { it[index] }
            flagVisibleTrees(treeCol)
            flagVisibleTrees(treeCol.reversed())
        }

        return trees.sumOf { it.count { it.visible } }
    }

    fun part2(input: List<String>): Int {
        // Parse tree grid. Consider each tree, calculating its score. Find scores by
        val trees = input.map { it.toCharArray().map { Tree(it.toString().toInt()) } }

//        trees.forEach {
//            it.forEach {
//                if (it.visible) print("X") else print("O")
//            }
//            println("")
//        }

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
//    check(part2(testInput) == 26)

    val input = readInput("Day08")
    println(part1(input))
//    println(part2(input))
}
