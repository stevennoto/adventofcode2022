fun main() {
    // Each square/node has a label (S, E, or a level), level, id (for uniqueness), and links to reachable nodes
    data class Node(val label:Char, val id:Int) {
        val level = when (label) { 'S' -> 1; 'E' -> 26; else -> label - 'a' + 1 }
        val sources = mutableSetOf<Node>() // nodes that can reach this one
        val destinations = mutableSetOf<Node>() // nodes that this one can reach
    }

    // Check a path between 2 nodes, and if passable, connect them
    fun validatePath(source:Node?, destination:Node?) {
        if (source == null || destination == null) return
        if (destination.level <= source.level + 1) {
            source.destinations.add(destination)
            destination.sources.add(source)
        }
    }

    // Read input into a 2D node array, convert to a graph of nodes showing possible paths, return start and end nodes
    fun buildGraph(input: List<String>): Pair<Node, Node> {
        var id = 0
        val inputArray = input.map { it.toCharArray().map { Node(it, id++) } }.toTypedArray()
        var start:Node = Node('S', -1)
        var end:Node = Node('E', -1)
        inputArray.forEachIndexed { i, row ->
            row.forEachIndexed { j, node ->
                if (node.label == 'S') start = node
                if (node.label == 'E') end = node
                validatePath(node, inputArray.getOrNull(i - 1)?.getOrNull(j))
                validatePath(node, inputArray.getOrNull(i + 1)?.getOrNull(j))
                validatePath(node, inputArray.getOrNull(i)?.getOrNull(j - 1))
                validatePath(node, inputArray.getOrNull(i)?.getOrNull(j + 1))
            }
        }
        return Pair(start, end)
    }

    fun part1(input: List<String>): Int {
        // Build graph and get start & end nodes
        val (start, end) = buildGraph(input)

        // Do a breadth-first search through nodes, counting steps from start to end
        val visited = mutableListOf<Node>()
        var neighbors = setOf(start)
        var steps = 0
        while(neighbors.isNotEmpty()) {
            visited.addAll(neighbors)
            neighbors = neighbors.flatMap { it.destinations }.filter { !visited.contains(it) }.toSet()
            steps++
            if (neighbors.contains(end)) return steps
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        // Build graph and get start & end nodes
        val (start, end) = buildGraph(input)

        // Do a breadth-first search through nodes, counting steps from end to first level 'a'
        val visited = mutableListOf<Node>()
        var neighbors = setOf(end)
        var steps = 0
        while(neighbors.isNotEmpty()) {
            visited.addAll(neighbors)
            neighbors = neighbors.flatMap { it.sources }.filter { !visited.contains(it) }.toSet()
            steps++
            if (neighbors.count { it.level == 1 } >= 1) return steps
        }
        return 0
    }

    // read input
    val testInput = readInput("Day12_test")
    val input = readInput("Day12")

    // part 1 test and solution
    check(part1(testInput) == 31)
    println(part1(input))

    // part 2 test and solution
    check(part2(testInput) == 29)
    println(part2(input))
}
