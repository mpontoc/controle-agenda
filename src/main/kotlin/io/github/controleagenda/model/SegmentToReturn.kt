package io.github.controleagenda.model

import com.fasterxml.jackson.annotation.JsonProperty

data class SegmentToReturn(

    @JsonProperty("segment")
    val segment: Segment = Segment(1, ""),
    @JsonProperty("sub_segment")
    val subSegment: MutableList<SubSegment?> = mutableListOf()

)