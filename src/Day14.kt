import kotlin.math.*

fun main() {
    val GRID_SIZE_X = 700
    val GRID_SIZE_Y = 170
    val SAND_ORIGIN = Pair(500, 0)

    // Return list of all points along line between 2 coordinate points
    fun getAllPointsBetween(x:Pair<Int, Int>, y:Pair<Int, Int>): List<Pair<Int, Int>> {
        if (x.first == y.first) return (min(x.second, y.second)..max(x.second, y.second)).toList().map { Pair(x.first, it) }
        else if (x.second == y.second) return (min(x.first, y.first)..max(x.first, y.first)).toList().map { Pair(it, x.second) }
        else return listOf()
    }

    // Create grid from input lines like "498,4 -> 498,6 -> 496,6"
    fun createGrid(input: List<String>): Array<BooleanArray> {
        val grid = Array(GRID_SIZE_X) { BooleanArray(GRID_SIZE_Y) }
        input.forEach {
            val points = it.split(" -> ").map { Pair(it.substringBefore(',').toInt(), it.substringAfter(',').toInt()) }
            points.windowed(2) {
                getAllPointsBetween(it.first(), it.last()).forEach {
                    grid[it.first][it.second] = true
                }
            }
        }
        return grid
    }

    // Determine where sand will settle, attempting to fall straight down, then down-left, then down-right, or null if won't settle
    fun getPointWhereSandWillSettle(grid: Array<BooleanArray>): Pair<Int, Int>? {
        var curX = SAND_ORIGIN.first
        var curY = SAND_ORIGIN.second
        while(true) {
            // Try to settle down, then down-left, then down-right, then repeat. But if none of those then it's settled.
            if(!grid[curX][curY + 1]) { curY++ }
            else if(!grid[curX - 1][curY + 1]) { curX--; curY++ }
            else if(!grid[curX + 1][curY + 1]) { curX++; curY++ }
            else return Pair(curX, curY)
            // If fallen below bottom threshold, it won't settle
            if(curY + 1 >= grid[0].size) return null
        }
    }

    fun part1(input: List<String>): Int {
        // Create grid, see where sand settles until it doesn't anymore, return amount of sand that settled
        val grid = createGrid(input)
        var numSettled = 0
        while(true) {
            val settle = getPointWhereSandWillSettle(grid)
            if (settle == null) return numSettled
            numSettled++
            grid[settle.first][settle.second] = true
        }
    }

    fun part2(input: List<String>): Int {
        // Create grid, see where sand settles until it doesn't anymore, return amount of sand that settled
        val grid = createGrid(input)

        // Additionally create ground level at 2 spaces beyond highest Y cordinate
        val floorLevel = input.maxOf { it.split(" -> ").maxOf { it.substringAfter(',').toInt() } } + 2
        getAllPointsBetween(Pair(0, floorLevel), Pair(GRID_SIZE_X - 1, floorLevel)).forEach {
            grid[it.first][it.second] = true
        }

        var numSettled = 0
        while(true) {
            val settle = getPointWhereSandWillSettle(grid)
            if (settle == null) return numSettled
            numSettled++
            if (settle == SAND_ORIGIN) return numSettled
            grid[settle.first][settle.second] = true
        }
    }

    // read input
    val testInput = readInput("Day14_test")
    val input = readInput("Day14")

    // part 1 test and solution
    check(part1(testInput) == 24)
    println(part1(input))

    // part 2 test and solution
    check(part2(testInput) == 93)
    println(part2(input))
}
