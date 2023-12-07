fun typeHand(hand: String): Int {
    val counts = hand.toList().groupingBy { it }.eachCount()
    val biggestGroup = counts.values.max()
    when (biggestGroup) {
        5 -> return 6 // 5 of a kind
        4 -> return 5 // 4 of a kind
        3 -> {
            if (2 in counts.values) {
                return 4 // Full house
            }
            return 3 // 3 of a kind
        }
        2 -> {
            if (counts.values.count { it == 2 } == 2) {
                return 2 // Two pair
            }
            return 1 // One pair
        }
        else -> return 0 // High card
    }
}

fun typeHandWithJoker(hand: String): Int {
    val counts = hand.toList().groupingBy { it }.eachCount()
    val biggestGroup = counts.filter { it.key != 'J' }.values.maxOrNull() ?: 0
    val jokerCount = counts['J'] ?: 0

    when (biggestGroup) {
        5 -> return 6 // 5 of a kind
        4 -> return if (jokerCount == 1) 6 else 5 // 5 of a kind when joker present, else 4 of a kind
        3 -> {
            if (jokerCount == 2) {
                return 6 // 5 of a kind
            }
            if (jokerCount == 1) {
                return 5 // 4 of a kind
            }
            if (2 in counts.values) {
                return 4 // Full house
            }
            return 3 // 3 of a kind
        }
        2 -> {
            if (jokerCount == 3) {
                return 6
            }
            if (jokerCount == 2) {
                return 5
            }
            if (counts.values.count { it == 2 } == 2) {
                if (jokerCount >= 1)
                    return 4 // full house
                return 2 // Two pair
            }
            if (jokerCount == 1) {
                return 3
            }
            if (jokerCount >= 2) {
                return 2
            }
            return 1 // One pair
        }
        else -> return when(jokerCount) {
            5 -> 6
            4 -> 6
            3 -> 5
            2 -> 3
            1 -> 1
            else -> 0
        }
    }
}

const val cardPrecedence = "23456789TJQKA"
const val cardPrecedenceWithJoker = "J23456789TQKA"
fun cardComparator(a: Char, b: Char, withJoker: Boolean = false): Int {
    val precedence = if (withJoker) cardPrecedenceWithJoker else cardPrecedence

    val idxA = precedence.indexOf(a)
    val idxB = precedence.indexOf(b)

    return idxA - idxB
}

fun handComparator(a: String, b: String, withJoker: Boolean = false): Int {
    val typeA = if (withJoker) typeHandWithJoker(a) else typeHand(a)
    val typeB = if (withJoker) typeHandWithJoker(b) else typeHand(b)

    if (typeA < typeB)
        return -1
    if (typeA > typeB)
        return 1

    for (i in (0..<5)) {
        val comp = cardComparator(a[i], b[i], withJoker)
        if (comp != 0)
            return comp
    }

    return 0
}

val handComparator = Comparator<Hand> { o1, o2 -> handComparator(o1.first, o2.first) }
val handComparatorWithJoker = Comparator<Hand> { o1, o2 -> handComparator(o1.first, o2.first, true) }

typealias Hand = Pair<String, Int>

class Day07: BasePuzzle<List<Hand>, Int, Int>() {
    override fun getPuzzleInput(): List<Hand> {
        return readLines("day07.txt").filter { it.isNotBlank() }.map {
            val (handString, betString) = it.split(' ')
            val bet = betString.toInt()
            Hand(handString, bet)
        }
    }

    override fun part1(input: List<Hand>): Int {
        return input.sortedWith(handComparator).mapIndexed { idx, it ->
            it.second * (idx+1)
        }.sum()
    }

    override fun part2(input: List<Hand>): Int {
        return input.sortedWith(handComparatorWithJoker).mapIndexed { idx, it ->
            it.second * (idx+1)
        }.sum()
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val dayClass = this::class.java.declaringClass.getDeclaredConstructor()
            val day = dayClass.newInstance() as BasePuzzle<*, *, *>
            day.main()
        }
    }
}