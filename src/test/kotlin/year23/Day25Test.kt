package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

class Day25Test {

    @Test
    fun `can parse the sample input`() {
        val graph = Day25.parseInput(sampleInput)
        assertThat(graph).hasSize(15)
        assertThat(graph).containsEntry("xhk", setOf("jqt", "hfx", "rhn", "bvb", "ntq"))
    }

    @Test
    fun `can get answer using sample input`() {
        assertThat(Day25.componentProduct(sampleInput)).isEqualTo(54)
    }

    @Test
    fun `can get answer using question input`() {
        val input = Day25::class.java.getResourceAsStream("/year23/day25.txt")!!.bufferedReader().readText()
        assertThat(Day25.componentProduct(input)).isEqualTo(619225)
    }

    private val sampleInput = """
        jqt: rhn xhk nvd
        rsh: frs pzl lsr
        xhk: hfx
        cmg: qnr nvd lhk bvb
        rhn: xhk bvb hfx
        bvb: xhk hfx
        pzl: lsr hfx nvd
        qnr: nvd
        ntq: jqt hfx bvb xhk
        nvd: lhk
        lsr: lhk
        rzs: qnr cmg lsr rsh
        frs: qnr lhk lsr
    """.trimIndent()

}