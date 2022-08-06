package io.github.controleagenda.model

data class SegmentToReturn(

    val segment: Segment = Segment(1, ""),
    val subSegment: MutableList<SubSegment?> = mutableListOf()

)