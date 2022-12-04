fun main() {
    fun part1(input: List<String>): Int {
        // Split each string into 2 halves, find intersection = char that's in both halves, map to numeric value
        return input.map {
            val firstHalf = it.substring(0, it.length/2)
            val secondHalf = it.substring(it.length/2)
            firstHalf.toCharArray().intersect(secondHalf.toList()).first()
        }.map {
            if (it in 'A'..'Z') (it - 'A' + 27) else (it - 'a' + 1)
        }.sum()
    }

    fun part2(input: List<String>): Int {
        // Group input into sets of 3, find intersection of all 3, map to numeric value
        return input.chunked(3).map {
            val intersectOfFirstTwo = it[0].toCharArray().intersect(it[1].toList())
            intersectOfFirstTwo.intersect(it[2].toList()).first()
        }.map {
            if (it in 'A'..'Z') (it - 'A' + 27) else (it - 'a' + 1)
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
