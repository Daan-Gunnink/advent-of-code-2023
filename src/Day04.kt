import kotlin.math.pow

fun main() {
    Day04()
}

class Day04 : AdventOfCode(13, 30) {

    override fun part1(input: List<String>): Any {
        val regex = Regex("Card.* (\\d*):(.*)\\|(.*)")
        return input.map {
            regex.findAll(it).map { match ->
                val cardValues = convertGroupValueToIntList(match.groupValues[2])
                val drawValues = convertGroupValueToIntList(match.groupValues[3])
                val matchingNumbers = cardValues.filter { drawValues.contains(it) }
                2.0.pow(matchingNumbers.size - 1).toInt()
            }
        }.flatMap { it }.sum()
    }

    override fun part2(input: List<String>): Any {
        val regex = Regex("Card.* (\\d*):(.*)\\|(.*)")

        val cardsCount = IntArray(input.size) { 1 }
        input.map {
            regex.findAll(it).map { match ->
                val cardValues = convertGroupValueToIntList(match.groupValues[2])
                val drawValues = convertGroupValueToIntList(match.groupValues[3])
                cardValues.count { drawValues.contains(it) }
            }
        }.flatMap { it }.forEachIndexed { cardIndex, numberOfMatches ->
            for (nextCardIndex in cardIndex + 1..cardIndex + numberOfMatches) {
                cardsCount[nextCardIndex] += cardsCount[cardIndex]
            }
        }
        return cardsCount.sum()
    }

    private fun convertGroupValueToIntList(stringValue: String): List<Int> {
        return stringValue.trim().split(" ").filter { it.isNotEmpty() }.map { it.trim().toInt() }
    }
}