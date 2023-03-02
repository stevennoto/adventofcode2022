import kotlin.time.Duration.Companion.seconds

fun main() {
//    fun isSignificantIndex(index: Int): Boolean {
//        return (index + 20) % 40 == 0
//    }

    fun part1(input: List<String>): Int {
        // Parse commands into addends (numbers to add). "noop" becomes 0. "addx N" becomes a 0 and an N (since it takes 2 cycles).
        val addends = input.flatMap { if (it == "noop") listOf(0) else listOf(0, it.split(" ").last().toInt()) }

        // Track state in Pair: first val is running sum of signal strength, second val is register value
        return addends.foldIndexed(Pair(0, 1)) { index, pair, addend ->
            // If at position 20, 60, 100, 140, etc then add signal strength. Then, add addend.
//            Pair(pair.first + if (isSignificantIndex(index + 1)) pair.second * (index + 1) else 0, pair.second + addend)
            Pair(pair.first + if ((index + 20) % 40 == 0) pair.second * (index + 1) else 0, pair.second + addend)
        }.first
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
//    check(part2(testInput) == 1)

    val input = readInput("Day10")
    println(part1(input))
//    println(part2(input))
}
