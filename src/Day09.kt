import java.util.Deque
import kotlin.math.absoluteValue

fun main() {
    // Adjust rope head position based on direction
    fun moveHead(head: Pair<Int, Int>, direction: String): Pair<Int, Int> {
        return when (direction) {
            "U" -> Pair(head.first, head.second + 1)
            "D" -> Pair(head.first, head.second - 1)
            "R" -> Pair(head.first + 1, head.second)
            "L" -> Pair(head.first - 1, head.second)
            else -> head
        }
    }

    // Adjust tail position based on head position
    fun moveTail(tail: Pair<Int, Int>, head: Pair<Int, Int>): Pair<Int, Int> {
        // If in same X or Y coord, and other coord is >= 2 off, adjust other coord (move orthogonally)
        return if (head.first == tail.first && (head.second - tail.second).absoluteValue >= 2) {
            Pair(tail.first, tail.second + if (head.second < tail.second) -1 else 1)
        } else if (head.second == tail.second && (head.first - tail.first).absoluteValue >= 2) {
            Pair(tail.first + if (head.first < tail.first) -1 else 1, tail.second)
        }
        // Else, if either coord is >= 2 off, adjust both coords (move diagonally)
        else if ((head.first - tail.first).absoluteValue >= 2 || (head.second - tail.second).absoluteValue >= 2) {
            Pair(tail.first + if (head.first < tail.first) -1 else 1, tail.second + if (head.second < tail.second) -1 else 1)
        } else {
            tail
        }
    }

    fun part1(input: List<String>): Int {
        // Parse instructions, moving head per instructions and tail per head movement. Track unique tail locations in Set.
        var head = Pair(0, 0)
        var tail = Pair(0, 0)
        val tailLocations = mutableSetOf<Pair<Int, Int>>()
        input.forEach {
            val (direction, numTimes) = Regex("(\\w) (\\d+)").find(it)!!.destructured.toList()
            repeat(numTimes.toInt()) {
                head = moveHead(head, direction)
                tail = moveTail(tail, head)
                tailLocations.add(tail)
            }
        }
        return tailLocations.size
    }

    fun part2(input: List<String>): Int {
        // Same as above, but instead of head and tail, rope has 10 nodes
        val ropeNodes = MutableList(10) { Pair(0, 0) }
        val tailLocations = mutableSetOf<Pair<Int, Int>>()
        input.forEach {
            val (direction, numTimes) = Regex("(\\w) (\\d+)").find(it)!!.destructured.toList()
            repeat(numTimes.toInt()) {
                ropeNodes[0] = moveHead(ropeNodes[0], direction)
                repeat(9) { index ->
                    ropeNodes[index + 1] = moveTail(ropeNodes[index + 1], ropeNodes[index])
                }
                tailLocations.add(ropeNodes[9])
            }
        }
        return tailLocations.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 1)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
