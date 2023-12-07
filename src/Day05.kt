import java.time.Instant
import kotlin.system.measureTimeMillis

fun main() {
    Day05()
}

class Day05 : AdventOfCode(35L, 46L) {
    override fun part1(input: List<String>): Any {
        val parsedInput = input.joinToString("\n")
                .replace("\n", "-")
                .split("--")

        val actionMap = mutableMapOf<String, List<List<Long>>>()
        parsedInput.forEach {
            it.split(":").let {
                val actionKey = it.first()
                val actionValue = it.last().split("-").filter { it.isNotEmpty() }.map {
                    it.split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
                }
                actionMap[actionKey] = actionValue
            }
        }

        val keyMap = mapOf(
                "seed-to-soil map" to "seed",
                "soil-to-fertilizer map" to "fertilizer",
                "fertilizer-to-water map" to "water",
                "water-to-light map" to "light",
                "light-to-temperature map" to "temperature",
                "temperature-to-humidity map" to "humidity",
                "humidity-to-location map" to "location"
        )

        val seeds = actionMap["seeds"]!!.flatMap { it }
        val actions = actionMap.filter { it.key != "seeds" }.map {
            Pair(keyMap.get(it.key), convertToRangePair(it.value))
        }

        val locations = seeds.mapIndexed { index, seed ->
            var currentValue = seed
            actions.forEach { action ->
                val newContainer = action.second.firstOrNull {
                    currentValue >= it.first.first && currentValue <= it.first.last
                }
                val newValue = newContainer?.let { convertRangeToNumber(it.first, it.second, currentValue) }
                        ?: currentValue
                currentValue = newValue
            }
            currentValue
        }

        return locations.min()
    }

    override fun part2(input: List<String>): Any {
        var originalStartTime = Instant.now()
        val parsedInput = input.joinToString("\n")
                .replace("\n", "-")
                .split("--")

        val actionMap = mutableMapOf<String, List<List<Long>>>()
        parsedInput.forEach {
            it.split(":").let {
                val actionKey = it.first()
                val actionValue = it.last().split("-").filter { it.isNotEmpty() }.map {
                    it.split(" ").filter { it.isNotEmpty() }.map { it.toLong() }
                }
                actionMap[actionKey] = actionValue
            }
        }

        val keyMap = mapOf(
                "seed-to-soil map" to "seed",
                "soil-to-fertilizer map" to "fertilizer",
                "fertilizer-to-water map" to "water",
                "water-to-light map" to "light",
                "light-to-temperature map" to "temperature",
                "temperature-to-humidity map" to "humidity",
                "humidity-to-location map" to "location"
        )

        val seedMaps = actionMap["seeds"]!!.flatMap { it }.chunked(2)
        seedMaps.println()
        val actions = actionMap.filter { it.key != "seeds" }.map {
            Pair(keyMap.get(it.key), convertToRangePair(it.value))
        }

        var lowestSeenLocation : Long = Long.MAX_VALUE

        seedMaps.forEachIndexed { index,seedData ->
            "At $index".println()
            val seedRange = LongRange(seedData[0], seedData[0] + seedData[1])
            seedRange.println()

            for (seed in seedRange) {
                var currentValue = seed
                actions.forEach { action ->
                    val newContainer = action.second.firstOrNull {
                        currentValue >= it.first.first && currentValue <= it.first.last
                    }
                    val newValue = newContainer?.let { convertRangeToNumber(it.first, it.second, currentValue) }
                            ?: currentValue
                    currentValue = newValue
                }
                if (currentValue < lowestSeenLocation){
                    lowestSeenLocation = currentValue
                }
            }


        }

        lowestSeenLocation.println()
        "Time to find answer: ${Instant.now().minusMillis(originalStartTime.toEpochMilli()).toEpochMilli()}".println()
        return lowestSeenLocation
    }

    private fun convertRangeToNumber(sourceRange: LongRange, destinationRange: LongRange, inputNumber: Long): Long {
        val index = inputNumber - sourceRange.first
        return destinationRange.first + index
    }


    private fun convertToRangePair(input: List<List<Long>>): List<Pair<LongRange, LongRange>> {
        return input.map {
            val destination = it[0]
            val source = it[1]
            val range = it[2]
            Pair(LongRange(source, source + range), LongRange(destination, destination + range))
        }
    }
}