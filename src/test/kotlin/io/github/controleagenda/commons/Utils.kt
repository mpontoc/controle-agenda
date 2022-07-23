package io.github.controleagenda.commons

import io.github.controleagenda.model.Segment
import io.github.controleagenda.services.SegmentService

open class Utils() {

    fun listSegmentsDefault(): List<Segment> {
        return listOf(
            Segment(1, "Academia"),
            Segment(2, "Alimentação"),
            Segment(3, "Educação"),
            Segment(4, "Esporte"),
            Segment(5, "Familiar"),
            Segment(6, "Saúde")
        )
    }

    fun listSegmentOnlyOne(): List<Segment> {
        return listOf(
            Segment(1, "Academia")
        )
    }

    fun createSegment(segmentService: SegmentService, id: Long, segment: String) {
        segmentService.addSegment(id, Segment(id, segment))
    }
}


