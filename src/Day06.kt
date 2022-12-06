import java.util.Deque

fun main() {
    fun findIndexOfFirstStringOfUniqueChars(text: String, numChars: Int): Int {
        // Split input into windows, find first window where all elements are unique (i.e. all can be added to a set)
        return text.windowed(size = numChars).indexOfFirst { it.all(hashSetOf<Char>()::add) }
    }

    fun part1(input: List<String>): Int {
        return findIndexOfFirstStringOfUniqueChars(input.first(), 4) + 4
    }

    fun part2(input: List<String>): Int {
        return findIndexOfFirstStringOfUniqueChars(input.first(), 14) + 14
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 26)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
