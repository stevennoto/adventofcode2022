fun main() {
    fun part1(input: List<String>): Int {
        // Split into groups separated by 2 newlines. Sum each group and keep max.
        return input.joinToString("\n")
            .split("\n\n")
            .maxOf { it.split("\n").sumOf { it.toInt() } }
    }

    fun part2(input: List<String>): Int {
        // Split into groups separated by 2 newlines. Sum each group. Sort, take top 3 and sum.
        return input.joinToString("\n")
            .split("\n\n")
            .map { it.split("\n").sumOf { it.toInt() } }
            .sortedDescending().take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
