import java.util.Deque

fun main() {
    fun part1(input: List<String>, numStacks: Int, moveBoxesOneAtATime: Boolean = true): String {
        // Parse input diagram into 9 dequeues, 1 for each stack of crates, top of stack at ennd of dequeue
        val stacks = List(numStacks) {ArrayDeque<Char>()}
        val data = input.filter { it.trim().startsWith("[") }
        data.reversed().forEach {
            repeat(numStacks) { index ->
                if (it[4 * index + 1] != ' ') stacks[index].addLast(it[4 * index + 1])
            }
        }

        // Parse instructions, pop/push from source to target stack specified number of times
        val instructions = input.filter { it.trim().startsWith("move") }
        val regex = Regex("move (\\d+) from (\\d+) to (\\d+)")
        instructions.forEach {
            val (num, source, target) = regex.find(it)!!.destructured.toList().map { it.toInt() }
            if (moveBoxesOneAtATime)
                repeat(num) { stacks[target - 1].addLast(stacks[source - 1].removeLast()) }
            else {
                stacks[target - 1].addAll(stacks[source - 1].takeLast(num))
                repeat(num) { stacks[source - 1].removeLast() }
            }
        }

        // Join letters at top of each stack
        return stacks.joinToString("") { if(it.isNotEmpty()) it.last().toString() else "" }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput, 3) == "CMZ")
    check(part1(testInput, 3, false) == "MCD")

    val input = readInput("Day05")
    println(part1(input, 9))
    println(part1(input, 9, false))
}
