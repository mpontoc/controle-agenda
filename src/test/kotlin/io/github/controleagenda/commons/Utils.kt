package io.github.controleagenda.commons

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.services.SegmentService

open class Utils() {

    fun listSegmentsDefault(): MutableList<SegmentToReturn> {
        return mutableListOf(
            SegmentToReturn(
                Segment(1, "Academia"),
                mutableListOf(SubSegment())
            ),
            SegmentToReturn(
                Segment(2, "Alimentação"),
                mutableListOf(SubSegment())
            ),
            SegmentToReturn(
                Segment(3, "Educação"),
                mutableListOf(SubSegment())
            ),
            SegmentToReturn(
                Segment(4, "Esporte"),
                mutableListOf(SubSegment())
            ),
            SegmentToReturn(
                Segment(5, "Familiar"),
                mutableListOf(SubSegment())
            ),
            SegmentToReturn(
                Segment(6, "Saúde"),
                mutableListOf(SubSegment())
            )
        )
    }

    fun listSegmentOnlyOne(): List<Segment> {
        return listOf(
            Segment(1, "Academia")
        )
    }

    fun createSegment(segmentService: SegmentService, id: Long, segment: String) {
        segmentService.createSegment(Segment(id, segment))
    }
}


