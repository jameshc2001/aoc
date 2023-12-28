package year23

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import year23.Day25.Node

class Day25Test {

    @Test
    fun `can parse the sample input`() {
        val graph = Day25.parseInput(sampleInput)
        assertThat(graph.nodes).hasSize(13)
        assertThat(graph.neighbours).containsEntry(Node("xhk"), setOf(
            Node("jqt"), Node("hfx"), Node("rhn"), Node("bvb"), Node("ntq")
        ))
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