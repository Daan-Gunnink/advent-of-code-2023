abstract class AdventOfCode(testPart1: Any? = null, testPart2: Any? = null) {
    abstract fun part1(input: List<String>): Any

    abstract fun part2(input: List<String>): Any


    init {
        val currentDay = this::class.simpleName.toString()
        val testInput = readInput("${currentDay}_test")
        testPart1?.let {
            check(part1(testInput) == it)
        }
        testPart2?.let {
            check(part2(testInput) == it)
        }


        val input = readInput(currentDay)
        println("${currentDay}-1 - ${part1(input)}")
        println("${currentDay}-2 - ${part2(input)}")
    }
}
