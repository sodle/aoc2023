class Almanac(seedBlock: String, seedToSoilBlock: String, soilToFertilizerBlock: String,
              fertilizerToWaterBlock: String, waterToLightBlock: String,
              lightToTemperatureBlock: String, temperatureToHumidityBlock: String,
              humidityToLocationBlock: String) {
    val seeds = seedBlock.split(" ").filterNot { it.endsWith(':') }.map { it.toLong() }
    val seedsByRange = seeds.chunked(2).map { (it.first()..<it.first()+it.last()) }
    val seedToSoilMap = parseBlock(seedToSoilBlock)
    val soilToFertilizerMap = parseBlock(soilToFertilizerBlock)
    val fertilizerToWaterMap = parseBlock(fertilizerToWaterBlock)
    val waterToLightMap = parseBlock(waterToLightBlock)
    val lightToTemperatureMap = parseBlock(lightToTemperatureBlock)
    val temperatureToHumidityMap = parseBlock(temperatureToHumidityBlock)
    val humidityToLocationMap = parseBlock(humidityToLocationBlock)

    private fun parseBlock(block: String) =
        block.lines().filter { it.isNotBlank() }.filterNot { it.endsWith(':') }.associate { line ->
            val (destStart, sourceStart, length) = line.split(' ').map { it.toLong() }
            Pair((sourceStart..<sourceStart + length), sourceStart - destStart)
        }
}

fun blockLookup(block: Map<LongRange, Long>, index: Long): Long {
    val keyRange = block.keys.find { it.contains(index) }

    return if (keyRange == null) {
        index
    } else {
        val offset = block[keyRange]!!
        index - offset
    }
}

class Day05: BasePuzzle<Almanac, Long, Long>() {
    override fun getPuzzleInput(): Almanac {
        val file = this::class.java.getResource("day05.txt")!!.readText()
        val blocks = file.split("\n\n")
        
        val seedBlock = blocks.find { it.startsWith("seeds:") }!!
        val seedToSoilBlock = blocks.find { it.startsWith("seed-to-soil map:") }!!
        val soilToFertilizerBlock = blocks.find { it.startsWith("soil-to-fertilizer map:") }!!
        val fertilizerToWaterBlock = blocks.find { it.startsWith("fertilizer-to-water map:") }!!
        val waterToLightBlock = blocks.find { it.startsWith("water-to-light map:") }!!
        val lightToTemperatureBlock = blocks.find { it.startsWith("light-to-temperature map:") }!!
        val temperatureToHumidityBlock = blocks.find { it.startsWith("temperature-to-humidity map:") }!!
        val humidityToLocationBlock = blocks.find { it.startsWith("humidity-to-location map:") }!!

        return Almanac(seedBlock, seedToSoilBlock, soilToFertilizerBlock, fertilizerToWaterBlock, waterToLightBlock,
                       lightToTemperatureBlock, temperatureToHumidityBlock, humidityToLocationBlock)
    }

    override fun part1(input: Almanac): Long {
        return input.seeds.minOf { seed ->
            locateSeed(input, seed)
        }
    }

    override fun part2(input: Almanac): Long {
        return input.seedsByRange.minOf { range ->
            range.minOf { locateSeed(input, it) }
        }
    }

    private fun locateSeed(input: Almanac, seed: Long): Long {
        val soil = blockLookup(input.seedToSoilMap, seed)
        val fertilizer = blockLookup(input.soilToFertilizerMap, soil)
        val water = blockLookup(input.fertilizerToWaterMap, fertilizer)
        val light = blockLookup(input.waterToLightMap, water)
        val temperature = blockLookup(input.lightToTemperatureMap, light)
        val humidity = blockLookup(input.temperatureToHumidityMap, temperature)
        return blockLookup(input.humidityToLocationMap, humidity)
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val dayClass = this::class.java.declaringClass.getDeclaredConstructor()
            val day = dayClass.newInstance() as BasePuzzle<*, *, *>
            day.main()
        }
    }
}