fun main() {
    fun part1(input: List<String>): Int {
        // Parse input ranges, check whether one contains another, count number that do
        val regex = Regex("(\\d+)-(\\d+),(\\d+)-(\\d+)")
        return input.count {
            val (i, j, m, n) = regex.find(it)!!.destructured.toList().map { it.toInt() }
            ((i >= m && j <= n) || (m >= i && n <= j))
        }
    }

    fun part2(input: List<String>): Int {
        // Parse input ranges, check whether they overlap at all, count number that do
        val regex = Regex("(\\d+)-(\\d+),(\\d+)-(\\d+)")
        return input.count {
            val (i, j, m, n) = regex.find(it)!!.destructured.toList().map { it.toInt() }
            (i in m..n || j in m..n || m in i..j || n in i..j)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
