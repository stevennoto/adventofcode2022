fun main() {
    fun part1(input: List<String>): Int {
        // Clean up input (map A/B/C and X/Y/Z to 0/1/2)
        // Then, calculate rock/paper/scissors winner using modulus, and sum chosen moves and victory values
        return input.map {
            val chars = it.toCharArray()
            Pair(chars[0] - 'A', chars[2] - 'X')
        }.sumOf {
            val moveValue = it.second + 1
            val winValue = if ((it.first + 1) % 3 == it.second) 6 else if (it.first == it.second) 3 else 0
            moveValue + winValue
        }
    }

    fun part2(input: List<String>): Int {
        // Clean up input (map A/B/C and X/Y/Z to 0/1/2)
        // Then, calculate rock/paper/scissors move needed, and sum chosen moves and victory values
        return input.map {
            val chars = it.toCharArray()
            Pair(chars[0] - 'A', chars[2] - 'X')
        }.sumOf {
            val moveValue = when (it.second) {
                0 -> Math.floorMod(it.first - 1, 3) // To lose, move = 1 worse than opponent, mod 3. Use floorMod since negative.
                1 -> it.first // To tie, copy move
                2 -> (it.first + 1) % 3 // To win, move = 1 better than opponent, mod 3
                else -> 0
            } + 1
            val winValue = it.second * 3 // Value of loss/tie/win = 0/1/2 * 3
            moveValue + winValue
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
