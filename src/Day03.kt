import kotlin.math.max
import kotlin.math.min

fun main() {
    Day03()
}

class Day03 : AdventOfCode(4361, 467835) {
    override fun part1(input: List<String>): Any {
        val regex = Regex("(\\d*)")
        var score = 0
        input.forEachIndexed { index, line ->
            regex.findAll(line).filter { it.value.isNotEmpty() }.forEach {
                if (hasAdjacentCharacter(input, it.range, index)) {
                    score += it.value.toInt()
                }
            }
        }
        return score
    }

    override fun part2(input: List<String>): Any {
        val numberRegex = Regex("(\\d*)")
        val gearRegex = Regex("(\\*)")
        var score = 0

        val numbersWithYPos = input.flatMapIndexed { index, line ->
            numberRegex.findAll(line).map { it to index }
        }

        input.forEachIndexed { index, line ->
            gearRegex.findAll(line).forEach {
                val intersections = mutableListOf<Int>()
                val adjacentNumbers = getAdjacentDigitPositionForGear(input, it.range, index)
                val seenPositions = mutableListOf<Pair<IntRange, Int>>()

                adjacentNumbers.forEach { position ->
                    numbersWithYPos.forEach { (numMatch, yRange) ->
                        if (!seenPositions.contains(numMatch.range to yRange) && numMatch.range.contains(position.first) && position.second == yRange) {
                            intersections.add(numMatch.value.toInt())
                            seenPositions.add(numMatch.range to yRange)
                        }
                    }
                }

                if (intersections.size == 2) {
                    score += intersections.reduce { acc, i -> acc * i }
                }
            }
        }

        return score
    }

    private fun getAdjacentDigitPositionForGear(map: List<String>, positionX: IntRange, positionY: Int): List<Pair<Int, Int>> {
        val mapMaximumXPosition = map[0].length - 1
        val mapMaximumYPosition = map.size - 1
        val xRangeToCheck = IntRange(max(0, positionX.first - 1), min(mapMaximumXPosition, positionX.last + 1))
        val yRangeToCheck = IntRange(max(0, positionY - 1), min(mapMaximumYPosition, positionY + 1))

        val matchingPositions = mutableListOf<Pair<Int, Int>>()


        for (x: Int in xRangeToCheck) {
            for (y: Int in yRangeToCheck) {
                val char = map[y][x]
                if (char.isDigit()) {
                    matchingPositions.add(Pair(x, y))
                }
            }
        }
        return matchingPositions
    }

    private fun hasAdjacentCharacter(map: List<String>, positionX: IntRange, positionY: Int): Boolean {
        val mapMaximumXPosition = map[0].length - 1
        val mapMaximumYPosition = map.size - 1
        val xRangeToCheck = IntRange(max(0, positionX.first - 1), min(mapMaximumXPosition, positionX.last + 1))
        val yRangeToCheck = IntRange(max(0, positionY - 1), min(mapMaximumYPosition, positionY + 1))
        var hasAdjacent = false

        for (x: Int in xRangeToCheck) {
            for (y: Int in yRangeToCheck) {
                val char = map[y][x]
                if (!char.isDigit() && char != '.') {
                    hasAdjacent = true
                }
            }
        }
        return hasAdjacent
    }
}