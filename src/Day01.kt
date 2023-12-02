import java.lang.Exception

fun main() {
    Day01()
}

class Day01 : AdventOfCode(testPart1 = null, testPart2 = 281) {
    override fun part1(input: List<String>): Any {
        return input.splitAfter("").map {
            it.map {
                it.filter { it.isDigit() }.let {
                    "${it.first()}${it.last()}".toInt()
                }
            }.sum()
        }.first()
    }

    override fun part2(input: List<String>): Any {
        return input.splitAfter("").map {
            it.map { line ->
                val result = parseWordsFromList(line)
                val filtered = result.filter { char -> char.isDigit() }.let { number ->
                    "${number.first()}${number.last()}".toInt()
                }
                filtered
            }.sum()
        }.first()
    }

    private fun parseWordsFromList(line: String): String {
        var newLine = line
        val numberWords = mapOf(
                "one" to "1",
                "two" to "2",
                "three" to "3",
                "four" to "4",
                "five" to "5",
                "six" to "6",
                "seven" to "7",
                "eight" to "8",
                "nine" to "9"
        )

        newLine.findAnyOf(numberWords.keys, 0, true)?.let {
            newLine = newLine.replaceRange(it.first, it.first, numberWords[it.second]!!)
        }
        newLine.findLastAnyOf(numberWords.keys, newLine.lastIndex, true)?.let {
            newLine = newLine.replace(it.second, numberWords[it.second]!!)
        }

        return newLine
    }
}
