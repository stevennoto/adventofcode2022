import kotlin.math.abs

enum class CoordinateStatus {
    SENSOR, BEACON, NOT_BEACON, UNKNOWN
}
fun main() {
    fun manhattanDistance(x:Pair<Int, Int>, y:Pair<Int, Int>): Int {
        return abs(x.first - y.first) + abs(x.second - y.second)
    }

    data class Sensor(val sensor:Pair<Int, Int>, val beacon:Pair<Int, Int>) {
        val distanceBetween = manhattanDistance(sensor, beacon)
    }

    fun getRow(yCoordinate:Int, xMin:Int, xMax:Int, sensors:List<Sensor>):CharArray {
        val row = CharArray(xMax - xMin) { ' ' }
        var inRangeCoordinates = mutableSetOf<Int>()
        sensors.forEach {
            if (it.beacon.second == yCoordinate && it.beacon.first in xMin until xMax) row[it.beacon.first - xMin] = 'B'
            if (it.sensor.second == yCoordinate && it.sensor.first in xMin until xMax) row[it.sensor.first - xMin] = 'S'
            val horizDistance = it.distanceBetween - abs(yCoordinate - it.sensor.second)
//println("new things " + ((it.sensor.first - horizDistance)..(it.sensor.first + horizDistance)).toSet())
            inRangeCoordinates = inRangeCoordinates.union(((it.sensor.first - horizDistance)..(it.sensor.first + horizDistance)).toList()).toMutableSet()
//            (maxOf((it.sensor.first - horizDistance), xMin)..minOf((it.sensor.first + horizDistance), xMax - 1)).forEach {
//                if (row[it - xMin] == ' ')
//                    row[it - xMin] = '#'
//            }
        }
        inRangeCoordinates.forEach { if (it in xMin until xMax && row[it - xMin] == ' ') row[it - xMin] = '#' }
//println(inRangeCoordinates)
//println(row)
        return row
    }

    // Check coordinate against all sensors. If coordinate is at a beacon, ignore.
    // Else, if coordinate is covered by any sensor (i.e. can't be a beacon), return true.
    fun determineCoordinateStatus(xCoordinate:Int, yCoordinate:Int, sensors:List<Sensor>):CoordinateStatus {
        sensors.forEach {
            if (Pair(xCoordinate, yCoordinate) == it.beacon) return CoordinateStatus.BEACON
            if (Pair(xCoordinate, yCoordinate) == it.sensor) return CoordinateStatus.SENSOR
        }
        sensors.forEach {
            val horizDistance = it.distanceBetween - abs(yCoordinate - it.sensor.second)
            if (xCoordinate in (it.sensor.first - horizDistance)..(it.sensor.first + horizDistance)) {
                return CoordinateStatus.NOT_BEACON
            }
        }
        return CoordinateStatus.UNKNOWN
    }

    fun parseInput(input: List<String>):List<Sensor> {
        val regex = Regex("Sensor at x=(-?[0-9]+), y=(-?[0-9]+): closest beacon is at x=(-?[0-9]+), y=(-?[0-9]+)")
        return input.map {
            val (sensorX, sensorY, beaconX, beaconY) = regex.find(it)!!.destructured.toList().map { it.toInt() }
            Sensor(Pair(sensorX, sensorY), Pair(beaconX, beaconY))
        }
    }

    fun part1(input: List<String>, yCoordinate:Int): Int {
        val sensors = parseInput(input)
        val minX = sensors.minOf { it.sensor.first - it.distanceBetween }
        val maxX = sensors.maxOf { it.sensor.first + it.distanceBetween }

        return getRow(yCoordinate, minX, maxX, sensors).count { it =='#' }
    }

    fun part2(input: List<String>, minCoordinate:Int, maxCoordinate:Int): Int {
        val sensors = parseInput(input)

        (minCoordinate..maxCoordinate).forEach { yCoordinate ->
            if (yCoordinate % 100 == 0)
                println("Y = $yCoordinate")
            val target = getRow(yCoordinate, minCoordinate, maxCoordinate, sensors).indexOf(' ')
            if (target != -1) return (minCoordinate + target) * 4000000 + yCoordinate
        }
        return 0
    }

    // read input
    val testInput = readInput("Day15_test")
    val input = readInput("Day15")

//    (1..5).union(3..7).forEach { println(it) }

    // part 1 test and solution
    check(part1(testInput, 10) == 26)
    println(part1(input, 2000000))

    // part 2 test and solution
//    check(part2(testInput, 0, 20) == 56000011)
//    println(part2(input, 0, 4000000))
}
