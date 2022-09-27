package io.github.controleagenda.model.dto

data class SegmentDTO(

    val segmentId: Long,
    val segmentName: String,
    val subSegments: MutableList<SubSegmentDTO?> = mutableListOf()

)

data class SegmentResponse(

    val segmentId: Long,
    val segmentName: String

)