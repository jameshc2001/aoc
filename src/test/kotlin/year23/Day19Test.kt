package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day19Test {

    @Test
    fun `can parse the sample input`() {
        val (workflows, parts) = Day19.parseInput(sampleInput)
        assertThat(workflows).hasSize(11)
        assertThat(workflows).contains(Day19.Workflow("crn", listOf(
            Day19.Rule('x', '>', 2662, "A"),
            Day19.Rule(null, null, null, "R")
        )))
        assertThat(parts).hasSize(5)
        assertThat(parts).contains(Day19.Part(mapOf('x' to 2036, 'm' to 264, 'a' to 79, 's' to 2244)))
    }

    @Test
    fun `can get answer for part 1 using sample input`() {
        assertThat(Day19.acceptedSum(sampleInput)).isEqualTo(19114)
    }

    @Test
    fun `can get answer for part 1 using question input`() {
        val input = Day19::class.java.getResourceAsStream("/year23/day19.txt")!!.bufferedReader().readText()
        assertThat(Day19.acceptedSum(input)).isEqualTo(389114)
    }

    @Test
    fun `can get answer for part 2 using sample input`() {
        println(4000L * 4000L * 4000L * 4000L)
        assertThat(Day19.validCombinations(sampleInput)).isEqualTo(167409079868000)
    }

    private val sampleInput = """
        px{a<2006:qkq,m>2090:A,rfg}
        pv{a>1716:R,A}
        lnx{m>1548:A,A}
        rfg{s<537:gd,x>2440:R,A}
        qs{s>3448:A,lnx}
        qkq{x<1416:A,crn}
        crn{x>2662:A,R}
        in{s<1351:px,qqz}
        qqz{s>2770:qs,m<1801:hdj,R}
        gd{a>3333:R,R}
        hdj{m>838:A,pv}

        {x=787,m=2655,a=1222,s=2876}
        {x=1679,m=44,a=2067,s=496}
        {x=2036,m=264,a=79,s=2244}
        {x=2461,m=1339,a=466,s=291}
        {x=2127,m=1623,a=2188,s=1013}
    """.trimIndent()

}