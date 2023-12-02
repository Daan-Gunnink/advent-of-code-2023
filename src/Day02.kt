import kotlin.system.measureTimeMillis

fun main() {
    measureTimeMillis { Day02() }.println()
}
class Day02: AdventOfCode(8, 2286) {
    override fun part1(input: List<String>): Any {
        var score = 0
        input.splitAfter("").forEach { games ->
            val maximum = mapOf(
                    "red" to 12,
                    "green" to 13,
                    "blue" to 14,
            )
            val regex = Regex("Game (\\d*):|(\\d*) (red)|(\\d*) (green)|(\\d*) (blue)")
            games.forEach {
                val regexData = regex.findAll(it).toList()
                val gameIdData = regexData.slice(IntRange(0,1)).first().groupValues[1]
                val gameRoundData = regexData.slice(IntRange(1, regexData.indices.last))

                var isValidRound = true

                gameRoundData.forEach {
                    val cleaned = it.groupValues.filter { it.isNotEmpty() }
                    val countValue = cleaned[1]
                    val colorValue = cleaned[2]

                    if(countValue.toInt() > maximum[colorValue]!!){
                        isValidRound = false
                    }

                }

                if(isValidRound){
                    score+= gameIdData.toInt()
                }
            }
        }
        return score
    }

    override fun part2(input: List<String>): Any {
        var score = 0
        input.splitAfter("").forEach { games ->
            val regex = Regex("Game (\\d*):|(\\d*) (red)|(\\d*) (green)|(\\d*) (blue)")
            games.forEach {
                val roundScore = mutableMapOf<String, Int>()
                val regexData = regex.findAll(it).toList()
                val gameRoundData = regexData.slice(IntRange(1, regexData.indices.last))

                gameRoundData.forEach {
                    val cleaned = it.groupValues.filter { it.isNotEmpty() }
                    val countValue = cleaned[1]
                    val colorValue = cleaned[2]

                    if(countValue.toInt() > (roundScore[colorValue] ?: 0)){
                        roundScore[colorValue] = countValue.toInt()
                    }
                }

                score += roundScore.values.reduce { acc, i ->
                    acc * i
                }

            }
        }
        return score
    }
}